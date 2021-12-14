package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Book;

import java.util.List;

public interface BookService {

    void add(Book book);

    void changeNameById(long id, String name);

    void changeAuthorIdById(long id, long authorId);

    void changeGenreIdById(long id, long genreId);

    Book findById(long id);

    List<Book> findAll();

    void removeById(long id);

}
