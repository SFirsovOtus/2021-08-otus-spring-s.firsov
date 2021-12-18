package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Author;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(value = {AuthorDaoImpl.class, BookDaoImpl.class})
class AuthorDaoImplTest {

	@Autowired
	private AuthorDao authorDao;

	@Autowired
	private BookDao bookDao;

	private final List<Author> authors = List.of(
			new Author().setId(1L).setName("Братья Стругацкие"),
			new Author().setId(2L).setName("Эндрю Таненбаум"),
			new Author().setId(3L).setName("В. И. Ленин")
	);

	@Test
	void getAllShouldReturnExpectedAuthorList() {
		List<Author> actualAuthors = authorDao.getAll().stream()
				.sorted(Comparator.comparing(Author::getId))
				.collect(Collectors.toList());

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
		Author insertedAuthor = new Author()
				.setName("New author");

		authorDao.insert(insertedAuthor);
		String actualAuthorName = authorDao.getAll().stream()
				.map(Author::getName)
				.filter(authorName -> authorName.equals(insertedAuthor.getName()))
				.findFirst()
				.orElse(null);

		assertThat(actualAuthorName).isEqualTo(insertedAuthor.getName());
	}

	@Test
	void deleteByIdShouldDeleteSpecifiedAuthor() {
		long authorId = 1L;

		// удалим все книги, чтобы не мешали внешние ключи
		bookDao.getAll()
				.forEach(book -> bookDao.deleteById(book.getId()));


		assertThatCode(() -> authorDao.getById(authorId))
				.doesNotThrowAnyException();

		authorDao.deleteById(authorId);

		assertThatThrownBy(() -> authorDao.getById(authorId))
				.isInstanceOf(EmptyResultDataAccessException.class);
	}

}
