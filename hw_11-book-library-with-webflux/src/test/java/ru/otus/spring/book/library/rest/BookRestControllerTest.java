package ru.otus.spring.book.library.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;
import ru.otus.spring.book.library.rest.dto.AuthorDto;
import ru.otus.spring.book.library.rest.dto.BookDto;
import ru.otus.spring.book.library.rest.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.otus.spring.book.library.rest.BookRestController.*;

@WebFluxTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;


    @Test
    void getAllBooksShouldGetExpectedBooks() {
        List<Book> books = List.of(
                new Book("1", "Book 1", new Author("Author 1"), List.of(new Genre("Genre 1"), new Genre("Genre 2"))),
                new Book("2", "Book 2", new Author("Author 2"), List.of(new Genre("Genre 3"), new Genre("Genre 4")))
        );
        Flux<Book> bookFlux = Flux.fromIterable(books);

        List<BookDto> expectedBookDtos = new ArrayList<>();
        books.forEach(book -> expectedBookDtos.add(BookDto.toDto(book)));

        given(bookRepository.findAll()).willReturn(bookFlux);

        List<BookDto> actualBookDtos = webTestClient.get()
                .uri(URI_BOOKS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(books.size())
                .returnResult()
                .getResponseBody();

        verify(bookRepository, times(1)).findAll();
        assertThat(actualBookDtos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedBookDtos);
    }

    @Test
    void getBookShouldGetExpectedBook() {
        Book book = new Book("123", "Book 1", new Author("Author 1"), List.of(new Genre("Genre 1"), new Genre("Genre 2")));
        Mono<Book> bookMono = Mono.just(book);
        BookDto expectedBookDto = BookDto.toDto(book);

        given(bookRepository.findById(book.getId())).willReturn(bookMono);

        BookDto actualBookDto = webTestClient.get()
                .uri(URI_BOOK  + "/{id}", book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        verify(bookRepository, times(1)).findById(book.getId());
        assertThat(List.of(actualBookDto))
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(expectedBookDto));
    }

    @Test
    void addBookShouldAddSpecifiedBook() {
        BookDto bookDtoToInsert = new BookDto(null, "Book 1", new AuthorDto("Author 1"), List.of(new GenreDto("Genre 1"), new GenreDto("Genre 2")));
        Book bookToInsert = BookDto.toDomain(bookDtoToInsert);
        Book insertedBook = new Book("123", bookToInsert.getName(), bookToInsert.getAuthor(), bookToInsert.getGenres());
        Mono<Book> insertedBookMono = Mono.just(insertedBook);
        BookDto expectedBookDto = BookDto.toDto(insertedBook);

        given(bookRepository.save(any(Book.class))).willReturn(insertedBookMono);

        BookDto actualBookDto = webTestClient.post()
                .uri(URI_ADD_BOOK)
                .body(BodyInserters.fromObject(bookDtoToInsert))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();

        verify(bookRepository, times(1)).save(any(Book.class));
        assertThat(List.of(actualBookDto))
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(expectedBookDto));
    }

    @Test
    void deleteBookShouldDeleteSpecifiedBook() {
        String bookId = "123";

        given(commentRepository.deleteAllByBookId(bookId)).willReturn(Mono.empty());
        given(bookRepository.deleteById(bookId)).willReturn(Mono.empty());

        webTestClient.post()
                .uri(URI_DELETE_BOOK  + "/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("result_status").isEqualTo("OK");

        verify(commentRepository, times(1)).deleteAllByBookId(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

}
