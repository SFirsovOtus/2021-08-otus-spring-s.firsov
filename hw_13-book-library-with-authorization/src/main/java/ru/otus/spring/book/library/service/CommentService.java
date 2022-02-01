package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    void add(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByBook(Book book);

    void removeById(long id);

}
