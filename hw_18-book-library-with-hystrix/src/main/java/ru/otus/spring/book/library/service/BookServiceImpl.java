package ru.otus.spring.book.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.backup.BookLibraryBackup;
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
    private final SleepService sleepService;
    private final Logger logger;

    private BookLibraryBackup bookLibraryBackup = new BookLibraryBackup();

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

    @HystrixCommand(fallbackMethod="findAllBooksFallback",
            commandProperties= {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
            })
    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        sleepService.sleepRandomly(6);
        List<Book> books = bookRepository.findAll();
        bookLibraryBackup.setBooks(books);
        return books;
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

    private List<Book> findAllBooksFallback() {
        logger.info("Got books from backup");
        return bookLibraryBackup.getBooks();
    }

}
