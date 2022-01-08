package ru.otus.spring.book.library.repository;

import ru.otus.spring.book.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
