package ru.otus.spring.book.library.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.service.AuthorService;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.GenreService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.book.library.controller.CommentController.*;
import static ru.otus.spring.book.library.rest.BookRestController.*;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;


    public static final String URL_BOOKS = "/books";
    public static final String URL_DELETE_BOOK = "/delete-book";
    public static final String URL_ADD_BOOK = "/add-book";
    public static final String URL_SAVE_BOOK = "/save-book";

    public static final String PARAM_BOOK_ID = "book_id";


    @GetMapping(URL_BOOKS)
    public String getBooks(Model model) {
        model.addAttribute("url_comments", URL_COMMENTS);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);
        model.addAttribute("url_delete_book", URL_DELETE_BOOK);
        model.addAttribute("url_add_book", URL_ADD_BOOK);
        model.addAttribute("uri_books", URI_BOOKS);

        return "books";
    }

    @PostMapping(URL_DELETE_BOOK)
    public String deleteBook(@RequestParam(PARAM_BOOK_ID) long bookId) {
        bookService.removeById(bookId);

        return "redirect:" + URL_BOOKS;
    }

    @GetMapping(URL_ADD_BOOK)
    public String addBook(Model model) {
        String book = StringUtils.EMPTY;
        String author = StringUtils.EMPTY;
        String genres = StringUtils.EMPTY;

        List<Author> allAuthors = authorService.findAll().stream()
                .sorted(Comparator.comparing(Author::getName))
                .collect(Collectors.toList());
        List<Genre> allGenres = genreService.findAll().stream()
                .sorted(Comparator.comparing(Genre::getName))
                .collect(Collectors.toList());

        model.addAttribute("url_books", URL_BOOKS);
        model.addAttribute("book", book);
        model.addAttribute("author", author);
        model.addAttribute("genres", genres);
        model.addAttribute("all_authors", allAuthors);
        model.addAttribute("all_genres", allGenres);
        model.addAttribute("url_save_book", URL_SAVE_BOOK);

        return "add-book";
    }

    @PostMapping(URL_SAVE_BOOK)
    public String saveBook(String book, String author, String genres, Model model) {
        if (book.trim().isEmpty() || author.isEmpty() || genres == null) {
            model.addAttribute("url_add_book", URL_ADD_BOOK);
            model.addAttribute("url_books", URL_BOOKS);
            return "need-to-fill-book";
        }

        Author authorInfo = authorService.findByName(author).orElse(null);
        List<Genre> genreInfos = Arrays.stream(genres.split(","))
                .map(genre -> genreService.findByName(genre).orElse(null))
                .collect(Collectors.toList());

        Book newBook = new Book(0, book, authorInfo, genreInfos);

        bookService.add(newBook);

        return "redirect:" + URL_BOOKS;
    }

}
