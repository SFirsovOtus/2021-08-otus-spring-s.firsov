package ru.otus.spring.book.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.repository.CommentRepository;
import ru.otus.spring.book.library.rest.dto.CommentDto;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentRepository commentRepository;


    public static final String URI_COMMENTS_OF_BOOK = "/api/comments/book";
    public static final String URI_ADD_COMMENT = "/api/add/comment";
    public static final String URI_DELETE_COMMENT = "/api/delete/comment";
    public static final String URI_DELETE_COMMENTS_OF_BOOK = "/api/delete/comments/book";

    @GetMapping(URI_COMMENTS_OF_BOOK + "/{id}")
    public Flux<CommentDto> getComments(@PathVariable String id) {
        return commentRepository.findAllByBookId(id)
                .map(CommentDto::toDto);
    }

    // Пример запроса: {"text": "Новый комментарий", "bookId": "1234567890abcdef"}
    @PostMapping(URI_ADD_COMMENT)
    public Mono<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        return commentRepository.save(new Comment(commentDto.getText(), commentDto.getBookId()))
                .map(CommentDto::toDto);
    }

    @PostMapping(URI_DELETE_COMMENT + "/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteComment(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = Map.of("result_status", HttpStatus.OK);

        return commentRepository.deleteById(id)
                .thenReturn(new ResponseEntity<>(responseBody, httpHeaders, HttpStatus.OK));
    }

    @PostMapping(URI_DELETE_COMMENTS_OF_BOOK + "/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteComments(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = Map.of("result_status", HttpStatus.OK);

        return commentRepository.deleteAllByBookId(id)
                .thenReturn(new ResponseEntity<>(responseBody, httpHeaders, HttpStatus.OK));
    }

}
