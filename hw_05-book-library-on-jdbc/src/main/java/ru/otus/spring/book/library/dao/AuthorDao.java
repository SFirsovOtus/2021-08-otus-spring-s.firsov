package ru.otus.spring.book.library.dao;

import ru.otus.spring.book.library.domain.Author;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
