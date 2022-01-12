package ru.otus.spring.book.library.repository;

import ru.otus.spring.book.library.domain.Book;

public interface CommentRepositoryCustom {

    void deleteByBook(Book book);

}
