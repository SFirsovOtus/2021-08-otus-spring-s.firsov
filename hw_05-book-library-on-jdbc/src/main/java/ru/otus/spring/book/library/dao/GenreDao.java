package ru.otus.spring.book.library.dao;

import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
