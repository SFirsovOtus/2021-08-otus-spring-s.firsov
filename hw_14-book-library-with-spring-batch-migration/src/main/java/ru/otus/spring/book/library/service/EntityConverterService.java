package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.BookForMongo;

public interface EntityConverterService {

    BookForMongo toBookForMongo(Book book);

}
