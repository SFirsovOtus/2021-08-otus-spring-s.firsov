package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface GenreService {

    void add(Genre genre);

    Genre findById(long id);

    List<Genre> findAll();

    void removeById(long id);

}
