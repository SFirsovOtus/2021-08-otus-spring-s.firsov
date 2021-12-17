package ru.otus.spring.book.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.book.library.dao.BookDao;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

	@MockBean
	private BookDao bookDao;

	@Autowired
	private BookService bookService;

  private final Author anyAuthor = new Author()
			.setId(456L)
			.setName("Any author");
	private final Genre anyGenre = new Genre()
			.setId(789L)
			.setName("Any genre");;
	private final Book anyBook = new Book()
			.setId(123L)
			.setName("Any book")
			.setAuthor(anyAuthor)
			.setGenre(anyGenre);


	@Test
	void addShouldCallInsertOfBookDao() {

		bookService.add(anyBook);

		verify(bookDao, times(1)).insert(anyBook);
	}

	@Test
	void findByIdShouldCallGetByIdOfBookDao() {

		bookService.findById(anyBook.getId());

		verify(bookDao, times(1)).getById(anyBook.getId());
	}

	@Test
	void findAllShouldCallGetAllOfBookDao() {

		bookService.findAll();

		verify(bookDao, times(1)).getAll();
	}

	@Test
	void removeByIdShouldCallDeleteByIdOfBookDao() {

		bookService.removeById(anyBook.getId());

		verify(bookDao, times(1)).deleteById(anyBook.getId());
	}

	@Test
	void changeNameByIdShouldCallUpdateNameByIdOfBookDao() {

		bookService.changeNameById(anyBook.getId(), anyBook.getName());

		verify(bookDao, times(1)).updateNameById(anyBook.getId(), anyBook.getName());
	}

	@Test
	void changeAuthorIdByIdShouldCallUpdateAuthorIdByIdOfBookDao() {

		bookService.changeAuthorIdById(anyBook.getId(), anyBook.getAuthor());

		verify(bookDao, times(1)).updateAuthorIdById(anyBook.getId(), anyBook.getAuthor());
	}

	@Test
	void changeGenreIdByIdShouldCallUpdateGenreIdByIdOfBookDao() {

		bookService.changeGenreIdById(anyBook.getId(), anyBook.getGenre());

		verify(bookDao, times(1)).updateGenreIdById(anyBook.getId(), anyBook.getGenre());
	}

}
