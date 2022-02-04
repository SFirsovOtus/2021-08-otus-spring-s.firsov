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
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;
import ru.otus.spring.book.library.rest.dto.BookDto;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    public static final String URI_BOOKS = "/api/books";
    public static final String URI_BOOK = "/api/book";
    public static final String URI_ADD_BOOK = "/api/add/book";
    public static final String URI_DELETE_BOOK = "/api/delete/book";

    @GetMapping(URI_BOOKS)
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .map(BookDto::toDto);
    }

    @GetMapping(URI_BOOK + "/{id}")
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(BookDto::toDto);
    }

    // Пример запроса: {"name": "Название книги", "authorDto": {"name": "Имя автора"}, "genreDtos":[{"name": "1-й жанр"}, {"name": "2-й жанр"}]}
    @PostMapping(URI_ADD_BOOK)
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {
        return bookRepository.save(BookDto.toDomain(bookDto))
                .map(BookDto::toDto);
    }

    @PostMapping(URI_DELETE_BOOK + "/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteBook(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> responseBody = Map.of("result_status", HttpStatus.OK);

        return Flux.merge(
                commentRepository.deleteAllByBookId(id),
                bookRepository.deleteById(id)
                ).then(Mono.just(new ResponseEntity<>(responseBody, httpHeaders, HttpStatus.OK)));

    }

}
