package ru.otus.spring.book.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.backup.BookLibraryBackup;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final SleepService sleepService;
    private final Logger logger;

    private BookLibraryBackup bookLibraryBackup = new BookLibraryBackup();

    @Override
    @Transactional
    public void add(Author author) {
        authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findByName(String name) {
        return authorRepository.findAuthorByName(name);
    }

    @HystrixCommand(fallbackMethod="findAllAuthorsFallback",
            commandProperties= {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
            })
    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        sleepService.sleepRandomly(6);
        List<Author> authors = authorRepository.findAll();
        bookLibraryBackup.setAuthors(authors);
        return authors;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        authorRepository.deleteById(id);
    }

    private List<Author> findAllAuthorsFallback() {
        logger.info("Got authors from backup");
        return bookLibraryBackup.getAuthors();
    }

}
