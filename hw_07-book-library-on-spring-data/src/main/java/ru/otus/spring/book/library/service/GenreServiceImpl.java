package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.domain.Genre;
import ru.otus.spring.book.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;


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
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        genreRepository.deleteById(id);
    }

}
