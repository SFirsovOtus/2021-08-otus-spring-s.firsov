package ru.otus.spring.book.library.service;

import ru.otus.spring.book.library.domain.Genre;

import java.util.List;

public interface ToStringService {

    String convertGenreNamesToStringWithAscendingOrderByNameSeparatedByCommas(List<Genre> genres);

}
