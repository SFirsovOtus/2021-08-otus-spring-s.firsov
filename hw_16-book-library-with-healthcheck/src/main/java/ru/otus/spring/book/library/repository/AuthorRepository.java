package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.library.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByName(String name);

}
