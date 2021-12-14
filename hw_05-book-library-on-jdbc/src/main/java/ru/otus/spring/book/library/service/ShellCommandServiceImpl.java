package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.book.library.config.CommandLineInterfaceConfig;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;


@ShellComponent
@ShellCommandGroup(CommandLineInterfaceConfig.SHELL_COMMAND_GROUP_BOOK_LIBRARY_COMMANDS)
@RequiredArgsConstructor
public class ShellCommandServiceImpl implements ShellCommandService {

    private final IOService ioService;

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;


    @Override
    @ShellMethod(value = "Show all books", key = {"show-all-books", "sab"})
    public void showAllBooks() {
        List<Book> books = bookService.findAll();
        ioService.print(StringUtils.join(books, System.lineSeparator()));
    }

    @Override
    @ShellMethod(value = "Show one book by ID", key = {"show-one-book", "sob"})
    public void showOneBook(long id) {
        Book book = bookService.findById(id);
        ioService.print(book);
    }

    @Override
    @ShellMethod(value = "Add one book with ID, name, author ID and genre ID", key = {"add-one-book", "aob"})
    public void addOneBook(long id, String name, long authorId, long genreId) {
        bookService.add(new Book(id, name, authorId, genreId));
        ioService.print("Book added.");
    }

    @Override
    @ShellMethod(value = "Remove one book by ID", key = {"remove-one-book", "rob"})
    public void removeOneBook(long id) {
        bookService.removeById(id);
        ioService.print("Book removed.");
    }

    @Override
    @ShellMethod(value = "Change book name by book ID", key = {"change-book-name", "cbn"})
    public void changeBookName(long id, String name) {
        bookService.changeNameById(id, name);
        ioService.print("Book changed.");
    }

    @Override
    @ShellMethod(value = "Change book author ID by book ID", key = {"change-book-author", "cba"})
    public void changeBookAuthorId(long id, long authorId) {
        bookService.changeAuthorIdById(id, authorId);
        ioService.print("Book changed.");
    }

    @Override
    @ShellMethod(value = "Change book genre ID by book ID", key = {"change-book-genre", "cbg"})
    public void changeBookGenreId(long id, long genreId) {
        bookService.changeGenreIdById(id, genreId);
        ioService.print("Book changed.");
    }


    @Override
    @ShellMethod(value = "Show all authors", key = {"show-all-authors", "saa"})
    public void showAllAuthors() {
        List<Author> authors = authorService.findAll();
        ioService.print(StringUtils.join(authors, System.lineSeparator()));
    }

    @Override
    @ShellMethod(value = "Show one author by ID", key = {"show-one-author", "soa"})
    public void showOneAuthor(long id) {
        Author author = authorService.findById(id);
        ioService.print(author);
    }

    @Override
    @ShellMethod(value = "Add one author with ID and name", key = {"add-one-author", "aoa"})
    public void addOneAuthor(long id, String name) {
        authorService.add(new Author(id, name));
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
        ioService.print(StringUtils.join(genres, System.lineSeparator()));
    }

    @Override
    @ShellMethod(value = "Show one genre by ID", key = {"show-one-genre", "sog"})
    public void showOneGenre(long id) {
        Genre genre = genreService.findById(id);
        ioService.print(genre);
    }

    @Override
    @ShellMethod(value = "Add one genre with ID and name", key = {"add-one-genre", "aog"})
    public void addOneGenre(long id, String name) {
        genreService.add(new Genre(id, name));
        ioService.print("Genre added.");
    }

    @Override
    @ShellMethod(value = "Remove one genre by ID", key = {"remove-one-genre", "rog"})
    public void removeOneGenre(long id) {
        genreService.removeById(id);
        ioService.print("Genre removed.");
    }

}
