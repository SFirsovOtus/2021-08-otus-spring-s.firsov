package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    void add(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    void removeById(long id);

}
