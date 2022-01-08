package ru.otus.spring.book.library.repository;

import ru.otus.spring.book.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    void insert(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
