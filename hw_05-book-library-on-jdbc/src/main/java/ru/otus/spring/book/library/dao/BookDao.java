package ru.otus.spring.book.library.dao;

import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface BookDao {

    void insert(Book book);

    void updateNameById(long id, String name);

    void updateAuthorIdById(long id, Author author);

    void updateGenreIdById(long id, Genre genre);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
