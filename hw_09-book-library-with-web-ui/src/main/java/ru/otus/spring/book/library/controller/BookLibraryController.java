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
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.service.AuthorService;
import ru.otus.spring.book.library.service.BookService;
import ru.otus.spring.book.library.service.CommentService;
import ru.otus.spring.book.library.service.GenreService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookLibraryController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;


    private static final String URL_BOOKS = "/books";
    private static final String URL_COMMENTS = "/comments";
    private static final String URL_DELETE_COMMENT = "/delete-comment";
    private static final String URL_DELETE_BOOK = "/delete-book";
    private static final String URL_ADD_COMMENT = "/add-comment";
    private static final String URL_SAVE_COMMENT = "/save-comment";
    private static final String URL_ADD_BOOK = "/add-book";
    private static final String URL_SAVE_BOOK = "/save-book";

    private static final String PARAM_COMMENT_ID = "comment_id";
    private static final String PARAM_BOOK_ID = "book_id";

    @GetMapping(URL_BOOKS)
    public String getBooks(Model model) {
        List<Book> books = bookService.findAll().stream()
                .sorted(Comparator.comparing(Book::getId))
                .collect(Collectors.toList());

        model.addAttribute("books", books);
        model.addAttribute("url_comments", URL_COMMENTS);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);
        model.addAttribute("url_delete_book", URL_DELETE_BOOK);
        model.addAttribute("url_add_book", URL_ADD_BOOK);

        return "books";
    }

    @GetMapping(URL_COMMENTS)
    public String getComments(@RequestParam(PARAM_BOOK_ID) long bookId, Model model) {
        Book book = bookService.findById(bookId).orElse(null);
        model.addAttribute("url_books", URL_BOOKS);

        if (book == null) {
            return "no-such-book";
        }

        List<Comment> comments = commentService.findByBook(new Book(bookId, null, null, null)).stream()
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());

        model.addAttribute("comments", comments);
        model.addAttribute("book", book);
        model.addAttribute("url_delete_comment", URL_DELETE_COMMENT);
        model.addAttribute("param_comment_id", PARAM_COMMENT_ID);
        model.addAttribute("url_add_comment", URL_ADD_COMMENT);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);

        return "comments";
    }

    @PostMapping(URL_DELETE_COMMENT)
    public String deleteComment(@RequestParam(PARAM_COMMENT_ID) long commentId, Model model) {
        Comment comment = commentService.findById(commentId).orElse(null);

        if (comment == null) {
            model.addAttribute("url_books", URL_BOOKS);
            return "no-such-comment";
        }

        commentService.removeById(commentId);

        return "redirect:" + URL_COMMENTS + "?" + PARAM_BOOK_ID + "=" + comment.getBook().getId();
    }

    @PostMapping(URL_DELETE_BOOK)
    public String deleteBook(@RequestParam(PARAM_BOOK_ID) long bookId) {
        bookService.removeById(bookId);

        return "redirect:" + URL_BOOKS;
    }

    @GetMapping(URL_ADD_COMMENT)
    public String addComment(@RequestParam(PARAM_BOOK_ID) long bookId, Model model) {
        Comment comment = new Comment(0, null, new Book(bookId, null, null, null));

        model.addAttribute("comment", comment);
        model.addAttribute("url_comments", URL_COMMENTS);
        model.addAttribute("param_book_id", PARAM_BOOK_ID);
        model.addAttribute("url_save_comment", URL_SAVE_COMMENT);

        return "add-comment";
    }

    @PostMapping(URL_SAVE_COMMENT)
    public String saveComment(@RequestParam(PARAM_BOOK_ID) long bookId, Comment comment, Model model) {
        if (comment.getText().trim().isEmpty()) {
            model.addAttribute("book_id", bookId);
            model.addAttribute("url_comments", URL_COMMENTS);
            model.addAttribute("param_book_id", PARAM_BOOK_ID);
            return "need-to-fill-comment";
        }

        Book book = new Book(bookId, null, null, null);

        commentService.add(comment.setBook(book));

        return "redirect:" + URL_COMMENTS + "?" + PARAM_BOOK_ID + "=" + bookId;
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
