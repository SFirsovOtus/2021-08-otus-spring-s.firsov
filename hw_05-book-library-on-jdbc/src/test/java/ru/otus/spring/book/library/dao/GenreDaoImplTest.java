package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(value = {GenreDaoImpl.class, BookDaoImpl.class})
class GenreDaoImplTest {

	@Autowired
	private GenreDao genreDao;

	@Autowired
	private BookDao bookDao;

	private final List<Genre> genres = List.of(
			new Genre().setId(1L).setName("Фантастика"),
			new Genre().setId(2L).setName("Философия"),
			new Genre().setId(3L).setName("Компьютерная литература")
	);


	@Test
	void getAllShouldReturnExpectedGenreList() {
		List<Genre> actualGenres = genreDao.getAll().stream()
				.sorted(Comparator.comparing(Genre::getId))
				.collect(Collectors.toList());

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
		Genre insertedGenre = new Genre()
				.setName("New genre");

		genreDao.insert(insertedGenre);
		String actualGenreName = genreDao.getAll().stream()
				.map(Genre::getName)
				.filter(genreName -> genreName.equals(insertedGenre.getName()))
				.findFirst()
				.orElse(null);

		assertThat(actualGenreName).isEqualTo(insertedGenre.getName());
	}

	@Test
	void deleteByIdShouldDeleteSpecifiedGenre() {
		long genreId = 1L;

		// удалим все книги, чтобы не мешали внешние ключи
		bookDao.getAll()
				.forEach(book -> bookDao.deleteById(book.getId()));


		assertThatCode(() -> genreDao.getById(genreId))
				.doesNotThrowAnyException();

		genreDao.deleteById(genreId);

		assertThatThrownBy(() -> genreDao.getById(genreId))
				.isInstanceOf(EmptyResultDataAccessException.class);
	}

}
