package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findAllShouldReturnExpectedBookList() {
        List<Book> expectedBooks = List.of(
                new Book(1, "Пикник на обочине", null, null),
                new Book(2, "Жук в муравейнике", null, null),
                new Book(3, "Современные операционные системы", null, null),
                new Book(4, "Компьютерные сети", null, null),
                new Book(5, "Философские тетради", null, null),
                new Book(6, "Материализм и эмпириокритицизм", null, null),
                new Book(7, "Человек-невидимка", null, null),
                new Book(8, "Война миров", null, null)
        );

        List<Book> actualBooks = bookRepository.findAll().stream()
                .sorted(Comparator.comparing(Book::getId))
                .collect(Collectors.toList());

        assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            assertThat(actualBooks.get(i).getId()).isEqualTo(expectedBooks.get(i).getId());
            assertThat(actualBooks.get(i).getName()).isEqualTo(expectedBooks.get(i).getName());
        }
    }

    @Test
    void findByIdShouldReturnExpectedBook() {
        long expectedBookId = 6;
        String expectedBookName = "Материализм и эмпириокритицизм";
        String expectedAuthorName = "В. И. Ленин";

        Book book = bookRepository.findById(expectedBookId).orElse(null);

        assertThat(book.getId()).isEqualTo(expectedBookId);
        assertThat(book.getName()).isEqualTo(expectedBookName);
        assertThat(book.getAuthor().getName()).isEqualTo(expectedAuthorName);
    }

    @Test
    void saveShouldInsertSpecifiedBook() {
        Book insertedBook = new Book(0, "New book", new Author(2, null), List.of(new Genre(6, null)));
        String expectedAuthorName = "Эндрю Таненбаум";

        bookRepository.save(insertedBook);
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

        book = entityManager.find(Book.class, bookId);
        assertThat(book).isNull();
    }

}
