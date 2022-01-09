package ru.otus.spring.book.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


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
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        authorRepository.deleteById(id);
    }

}
