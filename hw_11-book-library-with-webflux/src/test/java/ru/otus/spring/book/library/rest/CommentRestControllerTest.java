package ru.otus.spring.book.library.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.repository.CommentRepository;
import ru.otus.spring.book.library.rest.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.otus.spring.book.library.rest.CommentRestController.*;

@WebFluxTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository commentRepository;


    @Test
    void getCommentsShouldGetExpectedComments() {
        String bookId = "987";
        List<Comment> comments = List.of(
                new Comment("1", "Comment 1", bookId),
                new Comment("2", "Comment 2", bookId)
        );
        Flux<Comment> commentFlux = Flux.fromIterable(comments);

        List<CommentDto> expectedCommentDtos = new ArrayList<>();
        comments.forEach(comment -> expectedCommentDtos.add(CommentDto.toDto(comment)));

        given(commentRepository.findAllByBookId(bookId)).willReturn(commentFlux);

        List<CommentDto> actualCommentDtos = webTestClient.get()
                .uri(URI_COMMENTS_OF_BOOK  + "/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(comments.size())
                .returnResult()
                .getResponseBody();

        verify(commentRepository, times(1)).findAllByBookId(bookId);
        assertThat(actualCommentDtos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedCommentDtos);
    }

    @Test
    void addCommentShouldAddSpecifiedComment() {
        CommentDto commentDtoToInsert = new CommentDto(null, "Comment 1", "987");
        Comment commentToInsert = CommentDto.toDomain(commentDtoToInsert);
        Comment insertedComment = new Comment("123", commentToInsert.getText(), commentToInsert.getBookId());
        Mono<Comment> insertedCommentMono = Mono.just(insertedComment);
        CommentDto expectedCommentDto = CommentDto.toDto(insertedComment);

        given(commentRepository.save(any(Comment.class))).willReturn(insertedCommentMono);

        CommentDto actualCommentDto = webTestClient.post()
                .uri(URI_ADD_COMMENT)
                .body(BodyInserters.fromObject(commentDtoToInsert))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class)
                .returnResult()
                .getResponseBody();

        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(List.of(actualCommentDto))
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(expectedCommentDto));
    }

    @Test
    void deleteCommentShouldDeleteSpecifiedComment() {
        String commentId = "123";

        given(commentRepository.deleteById(commentId)).willReturn(Mono.empty());

        webTestClient.post()
                .uri(URI_DELETE_COMMENT  + "/{id}", commentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("result_status").isEqualTo("OK");

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void deleteCommentsShouldDeleteSpecifiedComments() {
        String bookId = "123";

        given(commentRepository.deleteAllByBookId(bookId)).willReturn(Mono.empty());

        webTestClient.post()
                .uri(URI_DELETE_COMMENTS_OF_BOOK  + "/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("result_status").isEqualTo("OK");

        verify(commentRepository, times(1)).deleteAllByBookId(bookId);
    }

}
