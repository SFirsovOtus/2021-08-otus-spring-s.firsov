package ru.otus.spring.book.library.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.book.library.domain.Book;
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
	void findAllShouldCallFindAllOfBookRepository() {
		bookService.findAll();

		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void findByIdShouldCallFindByIdOfBookRepository() {
		String bookId = "123";

		bookService.findById(bookId);

		verify(bookRepository, times(1)).findById(bookId);
	}

	@Test
	void addShouldCallSaveOfBookRepository() {
		Book book = new Book(null, "Any book", "Any author", List.of("Genre_1", "Genre_2"));

		bookService.add(book);

		verify(bookRepository, times(1)).save(book);
	}

	@Test
	void removeByIdShouldCallDeleteByBookOfCommentRepositoryAndDeleteByIdOfBookRepository() {
		String bookId = "123";

		bookService.removeById(bookId);

		verify(commentRepository, times(1)).deleteByBook(any(Book.class));
		verify(bookRepository, times(1)).deleteById(bookId);
	}

}
