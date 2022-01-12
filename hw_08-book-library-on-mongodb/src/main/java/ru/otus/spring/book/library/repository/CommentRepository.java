package ru.otus.spring.book.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.book.library.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {



}
