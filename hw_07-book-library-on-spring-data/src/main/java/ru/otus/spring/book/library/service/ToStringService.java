package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface ToStringService {

    String convertAuthorsToStringWithAscendingOrderById(List<Author> authors);

    String convertGenresToStringWithAscendingOrderById(List<Genre> genres);

    String convertCommentsToStringWithAscendingOrderById(List<Comment> comments);

    String convertBooksToStringWithAscendingOrderById(List<Book> books);

    String convertCommentTextsToStringWithAscendingOrderById(List<Comment> comments);

}
