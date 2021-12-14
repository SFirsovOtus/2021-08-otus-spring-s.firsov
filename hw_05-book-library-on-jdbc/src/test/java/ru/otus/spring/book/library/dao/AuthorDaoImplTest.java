package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

	@Autowired
	private AuthorDao authorDao;

	private final List<Author> authors = List.of(
			new Author(1L, "Братья Стругацкие"),
			new Author(2L, "Эндрю Таненбаум"),
			new Author(3L, "В. И. Ленин")
	);

	@Test
	void getAllShouldReturnExpectedAuthorList() {
		List<Author> actualAuthors = authorDao.getAll();

		assertThat(actualAuthors)
				.usingRecursiveFieldByFieldElementComparator()
				.isEqualTo(authors);
	}

	@Test
	void getByIdShouldReturnExpectedAuthor() {
		Author actualAuthor = authorDao.getById(3L);

		assertThat(actualAuthor)
				.usingRecursiveComparison()
				.isEqualTo(authors.get(2));
	}

	@Test
	void insertShouldReturnInsertedAuthor() {
		long authorId = 123L;
		Author insertedAuthor = new Author(authorId, "New author");

		authorDao.insert(insertedAuthor);
		Author actualAuthor = authorDao.getById(authorId);

		assertThat(actualAuthor)
				.usingRecursiveComparison()
				.isEqualTo(insertedAuthor);
	}

	@Test
	void deleteByIdShouldDeleteSpecifiedAuthor() {
		long authorId = 1L;

		assertThatCode(() -> authorDao.getById(authorId))
				.doesNotThrowAnyException();

		authorDao.deleteById(authorId);

		assertThatThrownBy(() -> authorDao.getById(authorId))
				.isInstanceOf(EmptyResultDataAccessException.class);
	}

}
