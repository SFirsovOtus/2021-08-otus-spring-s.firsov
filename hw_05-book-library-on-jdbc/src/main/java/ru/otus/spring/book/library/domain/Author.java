package ru.otus.spring.book.library.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Author {

    private Long id;

    private String name;

}
