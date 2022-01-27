package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findGenreByName(String name);

}
