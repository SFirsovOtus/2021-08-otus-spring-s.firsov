package ru.otus.spring.book.library.backup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BookLibraryBackup {

    private List<Book> books = Collections.emptyList();

    private List<Author> authors = Collections.emptyList();

    private List<Genre> genres = Collections.emptyList();

}
