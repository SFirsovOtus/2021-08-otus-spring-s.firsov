package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

	@Autowired
	private GenreDao genreDao;

	private final List<Genre> genres = List.of(
			new Genre(1L, "Фантастика"),
			new Genre(2L, "Философия"),
			new Genre(3L, "Компьютерная литература")
	);

	@Test
	void getAllShouldReturnExpectedGenreList() {
		List<Genre> actualGenres = genreDao.getAll();

		assertThat(actualGenres)
				.usingRecursiveFieldByFieldElementComparator()
				.isEqualTo(genres);
	}

	@Test
	void getByIdShouldReturnExpectedGenre() {
		Genre actualGenre = genreDao.getById(3L);

		assertThat(actualGenre)
				.usingRecursiveComparison()
				.isEqualTo(genres.get(2));
	}

	@Test
	void insertShouldReturnInsertedGenre() {
		long genreId = 123L;
		Genre insertedGenre = new Genre(genreId, "New genre");

		genreDao.insert(insertedGenre);
		Genre actualGenre = genreDao.getById(genreId);

		assertThat(actualGenre)
				.usingRecursiveComparison()
				.isEqualTo(insertedGenre);
	}

	@Test
	void deleteByIdShouldDeleteSpecifiedGenre() {
		long genreId = 1L;

		assertThatCode(() -> genreDao.getById(genreId))
				.doesNotThrowAnyException();

		genreDao.deleteById(genreId);

		assertThatThrownBy(() -> genreDao.getById(genreId))
				.isInstanceOf(EmptyResultDataAccessException.class);
	}

}
