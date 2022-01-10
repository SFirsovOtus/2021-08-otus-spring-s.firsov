package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Override
    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBook(Book book) {
        ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id");
        Example<Comment> commentExample = Example.of(new Comment(null, null,
                new Book(book.getId(), null, null, null)), ignoringExampleMatcher);
        return commentRepository.findAll(commentExample);
    }

    @Override
    public void removeById(String id) {
        commentRepository.deleteById(id);
    }

}
