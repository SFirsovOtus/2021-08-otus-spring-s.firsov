package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.book.library.domain.Author;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Autowired
    private TestEntityManager entityManager;


    private final List<Author> authors = List.of(
            new Author(1, "Братья Стругацкие"),
            new Author(2, "Эндрю Таненбаум"),
            new Author(3, "В. И. Ленин"),
            new Author(4, "Герберт Уэллс"),
            new Author(5, "Новый автор")
    );


    @Test
    void getAllShouldReturnExpectedAuthorList() {
        List<Author> actualAuthors = authorRepository.getAll().stream()
                .sorted(Comparator.comparing(Author::getId))
                .collect(Collectors.toList());

        assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(authors);
    }

    @Test
    void getByIdShouldReturnExpectedAuthor() {
        Author actualAuthor = authorRepository.getById(3).orElse(null);

        assertThat(actualAuthor)
                .usingRecursiveComparison()
                .isEqualTo(authors.get(2));
    }

    @Test
    void insertShouldReturnInsertedAuthor() {
        Author insertedAuthor = new Author(0, "New author");

        authorRepository.insert(insertedAuthor);

        String actualAuthorName = entityManager.find(Author.class, insertedAuthor.getId())
                .getName();

        assertThat(actualAuthorName).isEqualTo(insertedAuthor.getName());
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedAuthor() {
        long authorId = 5;

        Author author = entityManager.find(Author.class, authorId);
        assertThat(author).isNotNull();

        authorRepository.deleteById(authorId);
        entityManager.clear();

        author = entityManager.find(Author.class, authorId);
        assertThat(author).isNull();
    }

}
