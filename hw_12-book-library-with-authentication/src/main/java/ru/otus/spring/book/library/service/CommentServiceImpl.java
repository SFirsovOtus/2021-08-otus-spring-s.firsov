package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBook(Book book) {
        ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAll()
                .withIgnorePaths("id");
        Example<Comment> commentExample = Example.of(new Comment(0, null,
                new Book(book.getId(), null, null, null)), ignoringExampleMatcher);
        return commentRepository.findAll(commentExample);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        commentRepository.deleteById(id);
    }

}
