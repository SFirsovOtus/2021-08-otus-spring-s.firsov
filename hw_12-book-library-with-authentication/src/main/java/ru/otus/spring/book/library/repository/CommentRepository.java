package ru.otus.spring.book.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("delete from Comment c where c.book = :book")
    void deleteByBook(@Param("book") Book book);

}
