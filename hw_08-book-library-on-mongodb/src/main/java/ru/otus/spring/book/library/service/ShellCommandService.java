package ru.otus.spring.book.library.service;

public interface ShellCommandService {

    void showAllBooks();

    void showOneBook(String id);

    void addOneBook(String bookName, String authorName, String genres);

    void removeOneBook(String id);

    void showOneComment(String id);

    void addOneComment(String bookId, String commentText);

    void removeOneComment(String id);

}
