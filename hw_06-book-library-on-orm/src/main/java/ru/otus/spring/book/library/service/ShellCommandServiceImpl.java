package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.book.library.config.CommandLineInterfaceConfig;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup(CommandLineInterfaceConfig.SHELL_COMMAND_GROUP_BOOK_LIBRARY_COMMANDS)
@RequiredArgsConstructor
public class ShellCommandServiceImpl implements ShellCommandService {

    private final IOService ioService;
    private final ToStringService toStringService;

    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final BookService bookService;


    @Override
    @ShellMethod(value = "Show all authors", key = {"show-all-authors", "saa"})
    public void showAllAuthors() {
        List<Author> authors = authorService.findAll();
        ioService.print(toStringService.convertAuthorsToStringWithAscendingOrderById(authors));
    }

    @Override
    @ShellMethod(value = "Show one author by ID", key = {"show-one-author", "soa"})
    public void showOneAuthor(long id) {
        Author author = authorService.findById(id).orElse(null);
        ioService.print(toStringService.convertAuthorsToStringWithAscendingOrderById(
                author == null ? Collections.emptyList() : List.of(author)));
    }

    @Override
    @ShellMethod(value = "Add one author with name", key = {"add-one-author", "aoa"})
    public void addOneAuthor(String authorName) {
        authorService.add(new Author(0, authorName));
        ioService.print("Author added.");
    }

    @Override
    @ShellMethod(value = "Remove one author by ID", key = {"remove-one-author", "roa"})
    public void removeOneAuthor(long id) {
        authorService.removeById(id);
        ioService.print("Author removed.");
    }


    @Override
    @ShellMethod(value = "Show all genres", key = {"show-all-genres", "sag"})
    public void showAllGenres() {
        List<Genre> genres = genreService.findAll();
        ioService.print(toStringService.convertGenresToStringWithAscendingOrderById(genres));
    }

    @Override
    @ShellMethod(value = "Show one genre by ID", key = {"show-one-genre", "sog"})
    public void showOneGenre(long id) {
        Genre genre = genreService.findById(id).orElse(null);
        ioService.print(toStringService.convertGenresToStringWithAscendingOrderById(
                genre == null ? Collections.emptyList() : List.of(genre)));
    }

    @Override
    @ShellMethod(value = "Add one genre with name", key = {"add-one-genre", "aog"})
    public void addOneGenre(String genreName) {
        genreService.add(new Genre(0, genreName));
        ioService.print("Genre added.");
    }

    @Override
    @ShellMethod(value = "Remove one genre by ID", key = {"remove-one-genre", "rog"})
    public void removeOneGenre(long id) {
        genreService.removeById(id);
        ioService.print("Genre removed.");
    }


    @Override
    @ShellMethod(value = "Show all comments", key = {"show-all-comments", "sac"})
    public void showAllComments() {
        List<Comment> comments = commentService.findAll();
        ioService.print(toStringService.convertCommentsToStringWithAscendingOrderById(comments));
    }

    @Override
    @ShellMethod(value = "Show one comment", key = {"show-one-comment", "soc"})
    public void showOneComment(long id) {
        Comment comment = commentService.findById(id).orElse(null);
        ioService.print(toStringService.convertCommentsToStringWithAscendingOrderById(
                comment == null ? Collections.emptyList() : List.of(comment)));
    }

    @Override
    @ShellMethod(value = "Add one comment for book ID with text", key = {"add-one-comment", "aoc"})
    public void addOneComment(long bookId, String text) {
        commentService.add(new Comment(0, text,
                new Book(bookId, null, null, null)));
        ioService.print("Comment added.");
    }

    @Override
    @ShellMethod(value = "Remove one comment", key = {"remove-one-comment", "roc"})
    public void removeOneComment(long id) {
        commentService.removeById(id);
        ioService.print("Comment removed.");
    }


    @Override
    @ShellMethod(value = "Show all books", key = {"show-all-books", "sab"})
    public void showAllBooks() {
        List<Book> books = bookService.findAll();
        ioService.print(toStringService.convertBooksToStringWithAscendingOrderById(books));
    }

    @Override
    @ShellMethod(value = "Show one book by ID", key = {"show-one-book", "sob"})
    public void showOneBook(long id) {
        Book book = bookService.findById(id).orElse(null);
        ioService.print(toStringService.convertBooksToStringWithAscendingOrderById(
                book == null ? Collections.emptyList() : List.of(book)));

        if (book != null) {
            List<Comment> comments = commentService.findByBook(new Book(id, null, null, null));
            ioService.print(StringUtils.EMPTY);
            ioService.print(toStringService.convertCommentTextsToStringWithAscendingOrderById(comments));
        }
    }

    @Override
    @ShellMethod(value = "Add one book with book name, author ID and genre ID list (example of genre ID list: \"2 34 6\")", key = {"add-one-book", "aob"})
    public void addOneBook(String bookName, long authorId, String genreIds) {
        List<Genre> genres = Arrays.stream(genreIds.trim().split("\\s+"))
                .map(Long::valueOf)
                .map(genreId -> new Genre(genreId, null))
                .collect(Collectors.toList());
        bookService.add(new Book(0, bookName, new Author(authorId, null), genres));
        ioService.print("Book added.");
    }

    @Override
    @ShellMethod(value = "Remove one book by ID", key = {"remove-one-book", "rob"})
    public void removeOneBook(long id) {
        bookService.removeById(id);
        ioService.print("Book removed.");
    }

}
