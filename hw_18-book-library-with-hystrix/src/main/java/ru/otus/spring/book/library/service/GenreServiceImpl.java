package ru.otus.spring.book.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.backup.BookLibraryBackup;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final SleepService sleepService;
    private final Logger logger;

    private BookLibraryBackup bookLibraryBackup = new BookLibraryBackup();

    @Override
    @Transactional
    public void add(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findGenreByName(name);
    }

    @HystrixCommand(fallbackMethod="findAllGenresFallback",
            commandProperties= {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000")
            })
    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        sleepService.sleepRandomly(6);
        List<Genre> genres = genreRepository.findAll();
        bookLibraryBackup.setGenres(genres);
        return genres;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        genreRepository.deleteById(id);
    }

    private List<Genre> findAllGenresFallback() {
        logger.info("Got genres from backup");
        return bookLibraryBackup.getGenres();
    }

}
