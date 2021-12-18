package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Author;

import java.util.List;

public interface AuthorService {

    void add(Author author);

    Author findById(long id);

    List<Author> findAll();

    void removeById(long id);

}
