package ru.otus.spring.book.library.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;
import ru.otus.spring.book.library.domain.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String convertAuthorsToStringWithAscendingOrderById(List<Author> authors) {
        String idColumnName = "AUTHOR_ID";
        String nameColumnName = "NAME";
        String columnSeparator = "    ";

        if (authors == null || authors.isEmpty()) {
            return String.format("%s%s%s", idColumnName, columnSeparator, nameColumnName) + System.lineSeparator();
        }

        int idColumnWidth = authors.stream()
                .map(author -> ((Long) author.getId()).toString().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int nameColumnWidth = authors.stream()
                .map(author -> author.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalNameColumnWidth = Math.max(nameColumnWidth, nameColumnName.length());

        StringBuilder resultStringContainer =
                new StringBuilder(String.format("%" + finalIdColumnWidth + "s" + columnSeparator + "%-" + finalNameColumnWidth + "s",
                        idColumnName, nameColumnName));
        authors.stream()
                .sorted(Comparator.comparing(Author::getId))
                .forEach(author ->
                        resultStringContainer
                                .append(System.lineSeparator())
                                .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator + "%-" + finalNameColumnWidth + "s",
                                        author.getId(), author.getName()))
                );

        return resultStringContainer.toString();
    }

    @Override
    public String convertGenresToStringWithAscendingOrderById(List<Genre> genres) {
        String idColumnName = "GENRE_ID";
        String nameColumnName = "NAME";
        String columnSeparator = "    ";

        if (genres == null || genres.isEmpty()) {
            return String.format("%s%s%s", idColumnName, columnSeparator, nameColumnName) + System.lineSeparator();
        }

        int idColumnWidth = genres.stream()
                .map(genre -> ((Long) genre.getId()).toString().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int nameColumnWidth = genres.stream()
                .map(genre -> genre.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalNameColumnWidth = Math.max(nameColumnWidth, nameColumnName.length());

        StringBuilder resultStringContainer =
                new StringBuilder(String.format("%" + finalIdColumnWidth + "s" + columnSeparator + "%-" + finalNameColumnWidth + "s",
                        idColumnName, nameColumnName));
        genres.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .forEach(genre ->
                        resultStringContainer
                                .append(System.lineSeparator())
                                .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator + "%-" + finalNameColumnWidth + "s",
                                        genre.getId(), genre.getName()))
                );

        return resultStringContainer.toString();
    }

    @Override
    public String convertCommentsToStringWithAscendingOrderById(List<Comment> comments) {
        String idColumnName = "COMMENT_ID";
        String bookNameColumnName = "BOOK_NAME";
        String authorNameColumnName = "AUTHOR_NAME";
        String textColumnName = "COMMENT_TEXT";
        String columnSeparator = "    ";

        if (comments == null || comments.isEmpty()) {
            return String.format("%s%s%s%s%s%s%s",
                    idColumnName, columnSeparator, bookNameColumnName, columnSeparator, authorNameColumnName, columnSeparator, textColumnName) +
                    System.lineSeparator();
        }

        int idColumnWidth = comments.stream()
                .map(comment -> ((Long) comment.getId()).toString().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int bookNameColumnWidth = comments.stream()
                .map(comment -> comment.getBook().getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalBookNameColumnWidth = Math.max(bookNameColumnWidth, bookNameColumnName.length());

        int authorNameColumnWidth = comments.stream()
                .map(comment -> comment.getBook().getAuthor().getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalAuthorNameColumnWidth = Math.max(authorNameColumnWidth, authorNameColumnName.length());

        int textColumnWidth = comments.stream()
                .map(comment -> comment.getText().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalTextColumnWidth = Math.max(textColumnWidth, textColumnName.length());

        StringBuilder resultStringContainer =
                new StringBuilder(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                "%-" + finalBookNameColumnWidth + "s" + columnSeparator +
                                "%-" + finalAuthorNameColumnWidth + "s" + columnSeparator +
                                "%-" + finalTextColumnWidth + "s",
                        idColumnName, bookNameColumnName, authorNameColumnName, textColumnName));
        comments.stream()
                .sorted(Comparator.comparing(Comment::getId))
                .forEach(comment ->
                        resultStringContainer
                                .append(System.lineSeparator())
                                .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                                "%-" + finalBookNameColumnWidth + "s" + columnSeparator +
                                                "%-" + finalAuthorNameColumnWidth + "s" + columnSeparator +
                                                "%-" + finalTextColumnWidth + "s",
                                        comment.getId(), comment.getBook().getName(),
                                        comment.getBook().getAuthor().getName(), comment.getText()))
                );

        return resultStringContainer.toString();
    }

    @Override
    public String convertBooksToStringWithAscendingOrderById(List<Book> books) {
        String idColumnName = "BOOK_ID";
        String authorNameColumnName = "AUTHOR_NAME";
        String bookNameColumnName = "BOOK_NAME";
        String genresColumnName = "GENRES";
        String columnSeparator = "    ";
        String genresSeparator = ", ";

        if (books == null || books.isEmpty()) {
            return String.format("%s%s%s%s%s%s%s",
                    idColumnName, columnSeparator, authorNameColumnName, columnSeparator, bookNameColumnName, columnSeparator, genresColumnName) +
                    System.lineSeparator();
        }

        int idColumnWidth = books.stream()
                .map(book -> ((Long) book.getId()).toString().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int authorNameColumnWidth = books.stream()
                .map(book -> book.getAuthor().getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalAuthorNameColumnWidth = Math.max(authorNameColumnWidth, authorNameColumnName.length());

        int bookNameColumnWidth = books.stream()
                .map(book -> book.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalBookNameColumnWidth = Math.max(bookNameColumnWidth, bookNameColumnName.length());

        int genresColumnWidth = books.stream()
                .map(book -> book.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList()))
                .map(genres -> StringUtils.join(genres, genresSeparator).length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalGenresColumnWidth = Math.max(genresColumnWidth, genresColumnName.length());

        StringBuilder resultStringContainer =
                new StringBuilder(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                "%-" + finalAuthorNameColumnWidth + "s" + columnSeparator +
                                "%-" + finalBookNameColumnWidth + "s" + columnSeparator +
                                "%-" + finalGenresColumnWidth + "s",
                        idColumnName, authorNameColumnName, bookNameColumnName, genresColumnName));
        books.stream()
                .sorted(Comparator.comparing(Book::getId))
                .forEach(book -> {
                    String genres = book.getGenres().stream()
                            .map(Genre::getName)
                            .sorted(Comparator.naturalOrder())
                            .collect(Collectors.joining(genresSeparator));

                    resultStringContainer
                            .append(System.lineSeparator())
                            .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                            "%-" + finalAuthorNameColumnWidth + "s" + columnSeparator +
                                            "%-" + finalBookNameColumnWidth + "s" + columnSeparator +
                                            "%-" + finalGenresColumnWidth + "s",
                                    book.getId(), book.getAuthor().getName(),
                                    book.getName(), genres));
                });

        return resultStringContainer.toString();
    }

    @Override
    public String convertCommentTextsToStringWithAscendingOrderById(List<Comment> comments) {
        String commentHeader = "COMMENTS:";
        String idColumnName = "COMMENT_ID";
        String textColumnName = "COMMENT_TEXT";
        String columnSeparator = "    ";

        if (comments == null || comments.isEmpty()) {
            return String.format("%s" + System.lineSeparator() + "%s%s%s",
                    commentHeader, idColumnName, columnSeparator, textColumnName) +
                    System.lineSeparator();
        }

        int idColumnWidth = comments.stream()
                .map(comment -> ((Long) comment.getId()).toString().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int textColumnWidth = comments.stream()
                .map(comment -> comment.getText().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalTextColumnWidth = Math.max(textColumnWidth, textColumnName.length());

        StringBuilder resultStringContainer =
                new StringBuilder(String.format("%s" + System.lineSeparator() +
                                "%" + finalIdColumnWidth + "s" + columnSeparator +
                                "%-" + finalTextColumnWidth + "s",
                        commentHeader, idColumnName, textColumnName));
        comments.stream()
                .sorted(Comparator.comparing(Comment::getId))
                .forEach(comment ->
                        resultStringContainer
                                .append(System.lineSeparator())
                                .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                                "%-" + finalTextColumnWidth + "s",
                                        comment.getId(), comment.getText()))
                );

        return resultStringContainer.toString();
    }

}
