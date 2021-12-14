package ru.otus.spring.book.library.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Author {

    private final long id;

    private final String name;

}
