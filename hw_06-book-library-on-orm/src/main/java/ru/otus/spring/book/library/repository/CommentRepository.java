package ru.otus.spring.book.library.repository;

import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void insert(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> getByBook(Book book);

    List<Comment> getAll();

    void deleteById(long id);

    void deleteByBook(Book book);

}
