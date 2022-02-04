package ru.otus.spring.book.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.spring.book.library.domain.Author;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AuthorDto {

    private String name;


    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getName());
    }

    public static Author toDomain(AuthorDto authorDto) {
        return new Author(authorDto.getName());
    }

}
