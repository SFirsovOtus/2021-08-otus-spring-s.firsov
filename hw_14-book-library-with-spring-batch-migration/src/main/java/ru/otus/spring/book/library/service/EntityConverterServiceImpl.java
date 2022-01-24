package ru.otus.spring.book.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.BookForMongo;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityConverterServiceImpl implements EntityConverterService {

    @Override
    public BookForMongo toBookForMongo(Book book) {
        List<String> genres = book.getGenres().stream()
                .map(Genre::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return new BookForMongo(null, book.getName(), book.getAuthor().getName(), genres);
    }

}
