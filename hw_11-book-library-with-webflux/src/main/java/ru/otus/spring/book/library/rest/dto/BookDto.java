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

    private String id;

    private String name;

    private AuthorDto authorDto;

    private List<GenreDto> genreDtos;


    public static BookDto toDto(Book book) {
        AuthorDto authorDto = AuthorDto.toDto(book.getAuthor());

        List<GenreDto> genreDtos = new ArrayList<>();
        book.getGenres().forEach(genre -> genreDtos.add(GenreDto.toDto(genre)));

        return new BookDto(book.getId(), book.getName(), authorDto, genreDtos);
    }

    public static Book toDomain(BookDto bookDto) {
        Author author = AuthorDto.toDomain(bookDto.authorDto);

        List<Genre> genres = new ArrayList<>();
        bookDto.getGenreDtos().forEach(genreDto -> genres.add(GenreDto.toDomain(genreDto)));

        return new Book(bookDto.getId(), bookDto.getName(), author, genres);
    }

}
