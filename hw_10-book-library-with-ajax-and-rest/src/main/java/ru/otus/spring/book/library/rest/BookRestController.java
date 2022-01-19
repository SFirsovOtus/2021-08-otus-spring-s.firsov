package ru.otus.spring.book.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.book.library.rest.dto.BookDto;
import ru.otus.spring.book.library.service.BookService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;


    public static final String URI_BOOKS="/api/books";


    @GetMapping(URI_BOOKS)
    public List<BookDto> getAllBooks() {
        return bookService.findAll().stream()
                .map(BookDto::toDto)
                .sorted(Comparator.comparing(BookDto::getId))
                .collect(Collectors.toList());
    }

}
