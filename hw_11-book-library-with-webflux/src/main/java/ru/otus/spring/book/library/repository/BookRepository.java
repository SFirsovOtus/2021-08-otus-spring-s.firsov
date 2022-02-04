package ru.otus.spring.book.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.book.library.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {



}
