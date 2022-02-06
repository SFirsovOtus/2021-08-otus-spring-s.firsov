package ru.otus.spring.book.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.spring.book.library.domain.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GenreDto {

    private String name;


    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getName());
    }

    public static Genre toDomain(GenreDto genreDto) {
        return new Genre(genreDto.getName());
    }

}
