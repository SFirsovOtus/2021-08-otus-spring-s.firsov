package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.List;

public interface ToStringService {

    String convertBooksToStringWithAscendingOrderById(List<Book> books);

    String convertCommentsToStringWithAscendingOrderById(List<Comment> comments);

    String convertCommentTextsToStringWithAscendingOrderById(List<Comment> comments);

}
