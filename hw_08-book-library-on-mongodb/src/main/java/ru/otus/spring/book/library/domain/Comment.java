package ru.otus.spring.book.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String text;

    @DBRef
    private Book book;


    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

}
