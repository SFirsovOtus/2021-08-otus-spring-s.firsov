package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void add(Book book);

    Optional<Book> findById(String id);

    List<Book> findAll();

    void removeById(String id);

}
