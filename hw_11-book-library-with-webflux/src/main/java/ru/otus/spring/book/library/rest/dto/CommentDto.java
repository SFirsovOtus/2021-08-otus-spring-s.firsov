package ru.otus.spring.book.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.otus.spring.book.library.domain.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CommentDto {

    private String id;

    private String text;

    private String bookId;


    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getBookId());
    }

    public static Comment toDomain(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText(), commentDto.getBookId());
    }

}
