package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.dao.AuthorDao;
import ru.otus.spring.book.library.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;


    @Override
    public void add(Author author) {
        authorDao.insert(author);
    }

    @Override
    public Author findById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorDao.getAll();
    }

    @Override
    public void removeById(long id) {
        authorDao.deleteById(id);
    }

}
