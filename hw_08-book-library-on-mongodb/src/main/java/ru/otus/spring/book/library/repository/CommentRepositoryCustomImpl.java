package ru.otus.spring.book.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;


    @Override
    public void deleteByBook(Book book) {
        Query query = new Query()
                .addCriteria(Criteria.where("book").is(book));
        mongoTemplate.remove(query, Comment.class);
    }

}
