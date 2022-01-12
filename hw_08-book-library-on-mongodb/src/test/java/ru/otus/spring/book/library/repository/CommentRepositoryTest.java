package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;


    @Test
    void findByIdShouldReturnExpectedComment() {
        String expectedCommentText = "Очень понравилось, сюжет необычен";
        String expectedCommentId = commentRepository.findAll().stream()
                .filter(comment -> expectedCommentText.equals(comment.getText()))
                .map(Comment::getId)
                .findFirst()
                .orElse(null);

        String actualCommentText = commentRepository.findById(expectedCommentId)
                .get()
                .getText();

        assertThat(actualCommentText).isEqualTo(expectedCommentText);
    }

    @Test
    void saveShouldInsertSpecifiedComment() {
        Book book = bookRepository.findAll().get(4);
        Comment insertedComment = new Comment(null, "New comment",
                new Book(book.getId(), null, null, null));

        Comment actualComment = commentRepository.findAll().stream()
                .filter(comment -> insertedComment.getText().equals(comment.getText()))
                .findFirst()
                .orElse(null);
        assertThat(actualComment).isNull();

        commentRepository.save(insertedComment);

        actualComment = commentRepository.findAll().stream()
                .filter(comment -> insertedComment.getText().equals(comment.getText()))
                .findFirst()
                .orElse(null);
        assertThat(actualComment.getText()).isEqualTo(insertedComment.getText());
        assertThat(actualComment.getBook().getName()).isEqualTo(book.getName());
        assertThat(actualComment.getBook().getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedComment() {
        String commentText = "Лаконичная, доступная, идеальная для новичков";
        String commentId = commentRepository.findAll().stream()
                .filter(comment -> commentText.equals(comment.getText()))
                .map(Comment::getId)
                .findFirst()
                .orElse(null);
        assertThat(commentId).isNotNull();

        commentRepository.deleteById(commentId);

        commentId = commentRepository.findAll().stream()
                .filter(comment -> commentText.equals(comment.getText()))
                .map(Comment::getId)
                .findFirst()
                .orElse(null);
        assertThat(commentId).isNull();
    }

    @Test
    void findAllForBookShouldReturnExpectedCommentList() {
        Book book = bookRepository.findAll().stream()
                .filter(b -> "Пикник на обочине".equals(b.getName()))
                .findFirst()
                .orElse(null);

        List<String> expectedCommentTexts = List.of(
                "Почитав рецензии понял, что немногие читатели распробовали это произведение",
                "Читайте, не пожалеете"
        );

        ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id");
        Example<Comment> commentExample = Example.of(new Comment(null, null,
                new Book(book.getId(), null, null, null)), ignoringExampleMatcher);

        List<Comment> actualComments = commentRepository.findAll(commentExample).stream()
                .sorted(Comparator.comparing(Comment::getText))
                .collect(Collectors.toList());

        assertThat(actualComments.size()).isEqualTo(expectedCommentTexts.size());
        assertThat(actualComments.get(0).getText()).isEqualTo(expectedCommentTexts.get(0));
        assertThat(actualComments.get(1).getText()).isEqualTo(expectedCommentTexts.get(1));
    }

    @Test
    void deleteByBookShouldDeleteExpectedComments() {
        Book book = bookRepository.findAll().stream()
                .filter(b -> "Критика практического разума".equals(b.getName()))
                .findFirst()
                .orElse(null);

        long commentsCount = commentRepository.findAll().stream()
                .filter(comment -> comment.getBook().getName().equals(book.getName()))
                .count();
        assertThat(commentsCount).isEqualTo(2);

        commentRepository.deleteByBook(book);

        commentsCount = commentRepository.findAll().stream()
                .filter(comment -> comment.getBook().getName().equals(book.getName()))
                .count();
        assertThat(commentsCount).isEqualTo(0);
    }

}
