package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.dao.BookDao;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;


    @Override
    public void add(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void changeNameById(long id, String name) {
        bookDao.updateNameById(id, name);
    }

    @Override
    public void changeAuthorIdById(long id, Author author) {
        bookDao.updateAuthorIdById(id, author);
    }

    @Override
    public void changeGenreIdById(long id, Genre genre) {
        bookDao.updateGenreIdById(id, genre);
    }

    @Override
    public Book findById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.getAll();
    }

    @Override
    public void removeById(long id) {
        bookDao.deleteById(id);
    }

}
