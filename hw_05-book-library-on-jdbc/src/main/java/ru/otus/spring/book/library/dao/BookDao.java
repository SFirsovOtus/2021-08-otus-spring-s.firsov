package ru.otus.spring.book.library.dao;

import ru.otus.spring.book.library.domain.Book;

import java.util.List;

public interface BookDao {

    void insert(Book book);

    void updateNameById(long id, String name);

    void updateAuthorIdById(long id, long authorId);

    void updateGenreIdById(long id, long genreId);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
