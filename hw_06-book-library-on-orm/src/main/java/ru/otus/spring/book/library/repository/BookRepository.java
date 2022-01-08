package ru.otus.spring.book.library.repository;

import ru.otus.spring.book.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
