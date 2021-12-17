package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface BookService {

    void add(Book book);

    void changeNameById(long id, String name);

    void changeAuthorIdById(long id, Author author);

    void changeGenreIdById(long id, Genre genre);

    Book findById(long id);

    List<Book> findAll();

    void removeById(long id);

}
