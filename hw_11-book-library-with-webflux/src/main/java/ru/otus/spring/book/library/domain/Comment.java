package ru.otus.spring.book.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static ru.otus.spring.book.library.config.BookLibraryInitialization.COLLECTION_COMMENTS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = COLLECTION_COMMENTS)
public class Comment {

    @Id
    private String id;

    private String text;

    private String bookId;


    public Comment(String text, String bookId) {
        this.text = text;
        this.bookId = bookId;
    }

}
