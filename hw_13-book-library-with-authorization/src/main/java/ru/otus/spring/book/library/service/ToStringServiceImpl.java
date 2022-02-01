package ru.otus.spring.book.library.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("toStringService")
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String convertGenreNamesToStringWithAscendingOrderByNameSeparatedByCommas(List<Genre> genres) {
        String genresSeparator = ", ";

        return genres.stream()
                .map(Genre::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(genresSeparator));
    }

}
