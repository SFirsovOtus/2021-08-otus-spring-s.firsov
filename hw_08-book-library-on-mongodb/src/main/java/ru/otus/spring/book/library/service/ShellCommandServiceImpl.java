package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.book.library.config.CommandLineInterfaceConfig;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ShellComponent
@ShellCommandGroup(CommandLineInterfaceConfig.SHELL_COMMAND_GROUP_BOOK_LIBRARY_COMMANDS)
@RequiredArgsConstructor
public class ShellCommandServiceImpl implements ShellCommandService {

    private final IOService ioService;
    private final ToStringService toStringService;

    private final BookService bookService;
    private final CommentService commentService;


    @Override
    @ShellMethod(value = "Show all books", key = {"show-all-books", "sab"})
    public void showAllBooks() {
        List<Book> books = bookService.findAll();
        ioService.print(toStringService.convertBooksToStringWithAscendingOrderById(books));
    }

    @Override
    @ShellMethod(value = "Show one book by ID", key = {"show-one-book", "sob"})
    public void showOneBook(String id) {
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
    @ShellMethod(value = "Add one book with book name, author name and genre names (example of genre list: \"Повесть, Художественная литература, Приключения\")",
            key = {"add-one-book", "aob"})
    public void addOneBook(String bookName, String authorName, String genreNames) {
        List<String> genres = Arrays.asList(genreNames.trim().split("\\s*,\\s*"));
        bookService.add(new Book(null, bookName, authorName, genres));
        ioService.print("Book added.");
    }

    @Override
    @ShellMethod(value = "Remove one book by ID", key = {"remove-one-book", "rob"})
    public void removeOneBook(String id) {
        bookService.removeById(id);
        ioService.print("Book removed.");
    }

    @Override
    @ShellMethod(value = "Show one comment by ID", key = {"show-one-comment", "soc"})
    public void showOneComment(String id) {
        Comment comment = commentService.findById(id).orElse(null);
        ioService.print(toStringService.convertCommentsToStringWithAscendingOrderById(
                comment == null ? Collections.emptyList() : List.of(comment)));
    }

    @Override
    @ShellMethod(value = "Add one comment for book ID with comment text", key = {"add-one-comment", "aoc"})
    public void addOneComment(String bookId, String commentText) {
        commentService.add(new Comment(null, commentText,
                new Book(bookId, null, null, null)));
        ioService.print("Comment added.");
    }

    @Override
    @ShellMethod(value = "Remove one comment by ID", key = {"remove-one-comment", "roc"})
    public void removeOneComment(String id) {
        commentService.removeById(id);
        ioService.print("Comment removed.");
    }

}
