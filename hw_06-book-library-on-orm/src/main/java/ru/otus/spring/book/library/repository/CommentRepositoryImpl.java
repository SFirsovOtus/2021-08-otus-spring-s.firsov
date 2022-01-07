package ru.otus.spring.book.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    public void insert(Comment comment) {
        if (comment.getId() <= 0) {
            entityManager.persist(comment);
        } else {
            entityManager.merge(comment);
        }
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> getByBook(Book book) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book = :book", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c " +
                                                                          "join fetch c.book as b " +
                                                                          "join fetch b.author", Comment.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByBook(Book book) {
        Query query = entityManager.createQuery("delete from Comment c where c.book = :book");
        query.setParameter("book", book);
        query.executeUpdate();
    }

}
