package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void add(Genre genre);

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    void removeById(long id);

}
