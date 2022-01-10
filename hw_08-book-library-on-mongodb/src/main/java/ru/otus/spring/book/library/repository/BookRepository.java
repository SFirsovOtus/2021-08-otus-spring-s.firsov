package ru.otus.spring.book.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.library.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {



}
