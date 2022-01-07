package ru.otus.spring.book.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

	@MockBean
	private BookRepository bookRepository;
	@MockBean
	private CommentRepository commentRepository;

	@Autowired
	private BookService bookService;


	@Test
	void findAllShouldCallGetAllOfBookRepository() {
		bookService.findAll();

		verify(bookRepository, times(1)).getAll();
	}

	@Test
	void findByIdShouldCallGetByIdOfBookRepository() {
		long bookId = 123;

		bookService.findById(bookId);

		verify(bookRepository, times(1)).getById(bookId);
	}

	@Test
	void addShouldCallInsertOfBookRepository() {
		Book book = new Book(123, "Any book", new Author(456, "Any author"), List.of(
				new Genre(77, "Genre_1"),
				new Genre(88, "Genre_2")
		));

		bookService.add(book);

		verify(bookRepository, times(1)).insert(book);
	}

	@Test
	void removeByIdShouldCallDeleteByIdOfBookDao() {
		long bookId = 123;

		bookService.removeById(bookId);

		verify(commentRepository, times(1)).deleteByBook(any(Book.class));
		verify(bookRepository, times(1)).deleteById(bookId);
	}

}
