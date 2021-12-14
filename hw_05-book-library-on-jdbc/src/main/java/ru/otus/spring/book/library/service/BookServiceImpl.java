package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.dao.BookDao;
import ru.otus.spring.book.library.domain.Book;

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
    public void changeAuthorIdById(long id, long authorId) {
        bookDao.updateAuthorIdById(id, authorId);
    }

    @Override
    public void changeGenreIdById(long id, long genreId) {
        bookDao.updateGenreIdById(id, genreId);
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
