package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.library.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {



}
