package ru.otus.spring.book.library.service;

public interface ShellCommandService {

    void showAllAuthors();

    void showOneAuthor(long id);

    void addOneAuthor(String authorName);

    void removeOneAuthor(long id);


    void showAllGenres();

    void showOneGenre(long id);

    void addOneGenre(String genreName);

    void removeOneGenre(long id);


    void showAllComments();

    void showOneComment(long id);

    void addOneComment(long bookId, String text);

    void removeOneComment(long id);


    void showAllBooks();

    void showOneBook(long id);

    void addOneBook(String bookName, long authorId, String genreIds);

    void removeOneBook(long id);

}
