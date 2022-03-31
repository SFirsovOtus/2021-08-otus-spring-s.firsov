package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.repository.BookRepository;
import ru.otus.spring.book.library.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public void add(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        commentRepository.deleteByBook(new Book(id, null, null, null));
        bookRepository.deleteById(id);
    }

    @Override
    public int countAll() {
        return bookRepository.countAll();
    }

}
