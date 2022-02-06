package ru.otus.spring.book.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static ru.otus.spring.book.library.config.BookLibraryInitialization.COLLECTION_BOOKS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = COLLECTION_BOOKS)
public class Book {

    @Id
    private String id;

    private String name;

    private Author author;

    private List<Genre> genres;


    public Book(String name, Author author, List<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

}
