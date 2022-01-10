package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.book.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void findByIdShouldReturnExpectedBook() {
        Book expectedBook = bookRepository.findAll().stream()
                .filter(book -> "Пикник на обочине".equals(book.getName()))
                .findFirst()
                .orElse(null);

        Book actualBook = bookRepository.findById(expectedBook.getId()).orElse(null);

        assertThat(actualBook.getName()).isEqualTo(expectedBook.getName());
    }

    @Test
    void saveShouldInsertSpecifiedBook() {
        Book insertedBook = new Book(null, "Any book", "Any author", List.of("Genre_1", "Genre_2"));

        Book book = bookRepository.findAll().stream()
                .filter(b -> insertedBook.getName().equals(b.getName()))
                .findFirst()
                .orElse(null);
        assertThat(book).isNull();

        bookRepository.save(insertedBook);

        book = bookRepository.findAll().stream()
                .filter(b -> insertedBook.getName().equals(b.getName()))
                .findFirst()
                .orElse(null);
        assertThat(book).isNotNull();
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedBook() {
        String bookName = "UNIX. Программное окружение";

        Book book = bookRepository.findAll().stream()
                .filter(b -> bookName.equals(b.getName()))
                .findFirst()
                .orElse(null);
        assertThat(book).isNotNull();

        bookRepository.deleteById(book.getId());

        book = bookRepository.findAll().stream()
                .filter(b -> bookName.equals(b.getName()))
                .findFirst()
                .orElse(null);
        assertThat(book).isNull();
    }

}
