package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;

	private final List<Book> books = List.of(
			new Book(1L, "Пикник на обочине", 1L, 1L),
			new Book(2L, "Жук в муравейнике", 1L, 1L),
			new Book(3L, "Современные операционные системы", 2L, 3L)
	);


	@Test
	void getAllShouldReturnExpectedBookList() {
		List<Book> actualBooks = bookDao.getAll();

		assertThat(actualBooks)
				.usingRecursiveFieldByFieldElementComparator()
				.isEqualTo(books);
	}

	@Test
	void getByIdShouldReturnExpectedBook() {
		Book actualBook = bookDao.getById(3L);

		assertThat(actualBook)
				.usingRecursiveComparison()
				.isEqualTo(books.get(2));
	}

	@Test
	void insertShouldReturnInsertedBook() {
		long bookId = 123L;
		Book insertedBook = new Book(bookId, "New book", 456L, 789L);

		bookDao.insert(insertedBook);
		Book actualBook = bookDao.getById(bookId);

		assertThat(actualBook)
				.usingRecursiveComparison()
				.isEqualTo(insertedBook);
	}

	@Test
	void deleteByIdShouldDeleteSpecifiedBook() {
		long bookId = 1L;

		assertThatCode(() -> bookDao.getById(bookId))
				.doesNotThrowAnyException();

		bookDao.deleteById(bookId);

		assertThatThrownBy(() -> bookDao.getById(bookId))
				.isInstanceOf(EmptyResultDataAccessException.class);
	}

	@Test
	void updateNameByIdShouldUpdateNameOfSpecifiedBook() {
		long bookId = 2L;
		String newName = "New Book";

		String actualName = bookDao.getById(bookId).getName();
		assertThat(actualName).isNotEqualTo(newName);

		bookDao.updateNameById(bookId, newName);

		actualName = bookDao.getById(bookId).getName();
		assertThat(actualName).isEqualTo(newName);
	}

	@Test
	void updateAuthorIdByIdShouldUpdateAuthorIdOfSpecifiedBook() {
		long bookId = 2L;
		long newAuthorId = 123L;

		long actualAuthorId = bookDao.getById(bookId).getAuthorId();
		assertThat(actualAuthorId).isNotEqualTo(newAuthorId);

		bookDao.updateAuthorIdById(bookId, newAuthorId);

		actualAuthorId = bookDao.getById(bookId).getAuthorId();
		assertThat(actualAuthorId).isEqualTo(newAuthorId);
	}

	@Test
	void updateGenreIdByIdShouldUpdateGenreIdOfSpecifiedBook() {
		long bookId = 2L;
		long newGenreId = 123L;

		long actualGenreId = bookDao.getById(bookId).getGenreId();
		assertThat(actualGenreId).isNotEqualTo(newGenreId);

		bookDao.updateGenreIdById(bookId, newGenreId);

		actualGenreId = bookDao.getById(bookId).getGenreId();
		assertThat(actualGenreId).isEqualTo(newGenreId);
	}

}
