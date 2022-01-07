package ru.otus.spring.book.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(CommentRepositoryImpl.class)
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void getAllShouldReturnExpectedCommentList() {
        Set<Long> expectedCommentIds = Set.of(1L, 2L, 3L, 8L, 9L, 10L);

        List<Comment> comments = commentRepository.getAll();
        assertThat(comments.size()).isEqualTo(expectedCommentIds.size());

        comments = comments.stream()
                .filter(comment -> expectedCommentIds.contains(comment.getId()))
                .collect(Collectors.toList());
        assertThat(comments.size()).isEqualTo(expectedCommentIds.size());
    }

    @Test
    void getByIdShouldReturnExpectedComment() {
        long expectedCommentId = 3;
        String expectedCommentText = "Обожаю эту книгу";
        long expectedBookId = 2;

        Comment comment = commentRepository.getById(expectedCommentId).orElse(null);

        assertThat(comment.getId()).isEqualTo(expectedCommentId);
        assertThat(comment.getText()).isEqualTo(expectedCommentText);
        assertThat(comment.getBook().getId()).isEqualTo(expectedBookId);
    }

    @Test
    void insertShouldReturnInsertedComment() {
        Comment insertedComment = new Comment(0, "New comment",
                new Book(6, null, null, null));

        commentRepository.insert(insertedComment);

        Comment actualComment = entityManager.find(Comment.class, insertedComment.getId());

        assertThat(actualComment.getId()).isEqualTo(insertedComment.getId());
        assertThat(actualComment.getText()).isEqualTo(insertedComment.getText());
        assertThat(actualComment.getBook().getId()).isEqualTo(insertedComment.getBook().getId());
    }

    @Test
    void deleteByIdShouldDeleteSpecifiedComment() {
        long commentId = 8;

        Comment comment = entityManager.find(Comment.class, commentId);
        assertThat(comment).isNotNull();

        commentRepository.deleteById(commentId);
        entityManager.clear();

        comment = entityManager.find(Comment.class, commentId);
        assertThat(comment).isNull();
    }

    @Test
    void getByBookShouldReturnExpectedCommentList() {
        Set<Long> expectedCommentIds = Set.of(8L, 9L, 10L);
        Book book = new Book(7, null, null, null);

        List<Comment> comments = commentRepository.getByBook(book);

        assertThat(comments.stream().map(Comment::getId).collect(Collectors.toSet()).size()).isEqualTo(expectedCommentIds.size());
        assertTrue(expectedCommentIds.contains(comments.get(0).getId()));
        assertTrue(expectedCommentIds.contains(comments.get(1).getId()));
        assertTrue(expectedCommentIds.contains(comments.get(2).getId()));
    }

    @Test
    void deleteByBookShouldDeleteExpectedComments() {
        List<Long> deletedCommentIds = List.of(8L, 9L, 10L);
        Book book = new Book(7, null, null, null);

        Comment comment = entityManager.find(Comment.class, deletedCommentIds.get(0));
        assertThat(comment).isNotNull();
        comment = entityManager.find(Comment.class, deletedCommentIds.get(1));
        assertThat(comment).isNotNull();
        comment = entityManager.find(Comment.class, deletedCommentIds.get(2));
        assertThat(comment).isNotNull();

        commentRepository.deleteByBook(book);
        entityManager.clear();

        comment = entityManager.find(Comment.class, deletedCommentIds.get(0));
        assertThat(comment).isNull();
        comment = entityManager.find(Comment.class, deletedCommentIds.get(1));
        assertThat(comment).isNull();
        comment = entityManager.find(Comment.class, deletedCommentIds.get(2));
        assertThat(comment).isNull();
    }

}
