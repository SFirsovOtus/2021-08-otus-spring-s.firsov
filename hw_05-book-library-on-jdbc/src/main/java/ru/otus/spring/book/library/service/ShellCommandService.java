package ru.otus.spring.book.library.service;

public interface ShellCommandService {

    void showAllBooks();

    void showOneBook(long id);

    void addOneBook(long id, String name, long authorId, long genreId);

    void removeOneBook(long id);

    void changeBookName(long id, String name);

    void changeBookAuthorId(long id, long authorId);

    void changeBookGenreId(long id, long genreId);


    void showAllAuthors();

    void showOneAuthor(long id);

    void addOneAuthor(long id, String name);

    void removeOneAuthor(long id);


    void showAllGenres();

    void showOneGenre(long id);

    void addOneGenre(long id, String name);

    void removeOneGenre(long id);

}
