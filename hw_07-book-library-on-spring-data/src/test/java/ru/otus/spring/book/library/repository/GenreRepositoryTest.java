package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;


    private final List<Genre> genres = List.of(
            new Genre(1, "Фантастика"),
            new Genre(2, "Философия"),
            new Genre(3, "Компьютерная литература"),
            new Genre(4, "Роман"),
            new Genre(5, "Литература ужасов"),
            new Genre(6, "Детектив"),
            new Genre(7, "Биографии и мемуары"),
            new Genre(8, "Марксизм"),
            new Genre(9, "Программирование"),
            new Genre(10, "Трактат"),
            new Genre(11, "Новый жанр")
    );


    @Test
    void findAllShouldReturnExpectedGenreList() {
        List<Genre> actualGenres = genreRepository.findAll().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());

        assertThat(actualGenres)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(genres);
    }

    @Test
    void findByIdShouldReturnExpectedGenre() {
        Genre actualGenre = genreRepository.findById(3L).orElse(null);

        assertThat(actualGenre)
                .usingRecursiveComparison()
                .isEqualTo(genres.get(2));
    }

    @Test
    void saveShouldInsertSpecifiedGenre() {
        Genre insertedGenre = new Genre(0, "New genre");

        genreRepository.save(insertedGenre);

        String actualAuthorName = entityManager.find(Genre.class, insertedGenre.getId())
                .getName();

        assertThat(actualAuthorName).isEqualTo(insertedGenre.getName());
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedGenre() {
        long genreId = 11;

        Genre genre = entityManager.find(Genre.class, genreId);
        assertThat(genre).isNotNull();

        genreRepository.deleteById(genreId);

        genre = entityManager.find(Genre.class, genreId);
        assertThat(genre).isNull();
    }

}
