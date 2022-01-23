package ru.otus.spring.book.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "books")
public class BookForMongo {

    @Id
    private String id;

    private String name;

    private String author;

    private List<String> genres;


    public BookForMongo(String name, String author, List<String> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

}
