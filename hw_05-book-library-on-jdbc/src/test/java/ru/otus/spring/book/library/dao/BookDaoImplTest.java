package ru.otus.spring.book.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;

	private final Author author1 = new Author().setId(1L).setName("Братья Стругацкие");
	private final Author author2 = new Author().setId(2L).setName("Эндрю Таненбаум");

	private final Genre genre1 = new Genre().setId(1L).setName("Фантастика");
	private final Genre genre3 = new Genre().setId(3L).setName("Компьютерная литература");

	private final List<Book> books = List.of(
			new Book().setId(1L).setName("Пикник на обочине").setAuthor(author1).setGenre(genre1),
			new Book().setId(2L).setName("Жук в муравейнике").setAuthor(author1).setGenre(genre1),
			new Book().setId(3L).setName("Современные операционные системы").setAuthor(author2).setGenre(genre3)
	);


	@Test
	void getAllShouldReturnExpectedBookList() {
		List<Book> actualBooks = bookDao.getAll().stream()
				.sorted(Comparator.comparing(Book::getId))
				.collect(Collectors.toList());

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
		Book insertedBook = new Book()
				.setName("New book");

		bookDao.insert(insertedBook);
		String actualBookName = bookDao.getAll().stream()
				.map(Book::getName)
				.filter(bookName -> bookName.equals(insertedBook.getName()))
				.findFirst()
				.orElse(null);

		assertThat(actualBookName).isEqualTo(insertedBook.getName());
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
		long bookId = 3L;

		long actualAuthorId = bookDao.getById(bookId).getAuthor().getId();
		assertThat(actualAuthorId).isNotEqualTo(author1.getId());

		bookDao.updateAuthorIdById(bookId, author1);

		actualAuthorId = bookDao.getById(bookId).getAuthor().getId();
		assertThat(actualAuthorId).isEqualTo(author1.getId());
	}

	@Test
	void updateGenreIdByIdShouldUpdateGenreIdOfSpecifiedBook() {
		long bookId = 3L;

		long actualGenreId = bookDao.getById(bookId).getGenre().getId();
		assertThat(actualGenreId).isNotEqualTo(genre1.getId());

		bookDao.updateGenreIdById(bookId, genre1);

		actualGenreId = bookDao.getById(bookId).getGenre().getId();
		assertThat(actualGenreId).isEqualTo(genre1.getId());
	}

}
