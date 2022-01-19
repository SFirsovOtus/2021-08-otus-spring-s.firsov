package ru.otus.spring.book.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BookDto {

    private long id;

    private String name;

    private AuthorDto authorDto;

    private List<GenreDto> genreDtos;


    public static BookDto toDto(Book book) {
        Author author = book.getAuthor();
        AuthorDto authorDto = new AuthorDto(author.getId(), author.getName());

        List<Genre> genres = book.getGenres();
        List<GenreDto> genreDtos = new ArrayList<>();
        genres.forEach(genre -> genreDtos.add(new GenreDto(genre.getId(), genre.getName())));

        return new BookDto(book.getId(), book.getName(), authorDto, genreDtos);
    }

}
