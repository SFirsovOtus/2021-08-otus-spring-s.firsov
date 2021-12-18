package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.dao.GenreDao;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;


    @Override
    public void add(Genre genre) {
        genreDao.insert(genre);
    }

    @Override
    public Genre findById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.getAll();
    }

    @Override
    public void removeById(long id) {
        genreDao.deleteById(id);
    }

}
