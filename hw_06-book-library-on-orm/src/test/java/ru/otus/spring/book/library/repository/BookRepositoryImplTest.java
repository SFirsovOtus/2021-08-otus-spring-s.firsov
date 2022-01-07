package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void getAllShouldReturnExpectedBookList() {
        List<Long> expectedBookIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);

        List<Long> actualBookIds = bookRepository.getAll().stream()
                .map(Book::getId)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        assertThat(actualBookIds)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedBookIds);
    }

    @Test
    void getByIdShouldReturnExpectedBook() {
        long expectedBookId = 6;
        String expectedBookName = "Материализм и эмпириокритицизм";
        String expectedAuthorName = "В. И. Ленин";

        Book book = bookRepository.getById(expectedBookId).orElse(null);

        assertThat(book.getId()).isEqualTo(expectedBookId);
        assertThat(book.getName()).isEqualTo(expectedBookName);
        assertThat(book.getAuthor().getName()).isEqualTo(expectedAuthorName);
    }

    @Test
    void insertShouldReturnInsertedBook() {
        Book insertedBook = new Book(0, "New book", new Author(2, null), List.of(new Genre(6, null)));
        String expectedAuthorName = "Эндрю Таненбаум";

        bookRepository.insert(insertedBook);
        entityManager.clear();

        Book actualBook = entityManager.find(Book.class, insertedBook.getId());

        assertThat(actualBook.getName()).isEqualTo(insertedBook.getName());
        assertThat(actualBook.getAuthor().getName()).isEqualTo(expectedAuthorName);
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedBook() {
        long bookId = 8;

        Book book = entityManager.find(Book.class, bookId);
        assertThat(book).isNotNull();

        bookRepository.deleteById(bookId);
        entityManager.clear();

        book = entityManager.find(Book.class, bookId);
        assertThat(book).isNull();
    }

}
