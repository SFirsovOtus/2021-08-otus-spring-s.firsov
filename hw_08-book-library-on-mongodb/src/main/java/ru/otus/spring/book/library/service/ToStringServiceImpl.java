package ru.otus.spring.book.library.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Comment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToStringServiceImpl implements ToStringService {

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
                .map(book -> book.getId().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int authorNameColumnWidth = books.stream()
                .map(book -> book.getAuthor().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalAuthorNameColumnWidth = Math.max(authorNameColumnWidth, authorNameColumnName.length());

        int bookNameColumnWidth = books.stream()
                .map(book -> book.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalBookNameColumnWidth = Math.max(bookNameColumnWidth, bookNameColumnName.length());

        int genresColumnWidth = books.stream()
                .map(Book::getGenres)
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
                            .sorted(Comparator.naturalOrder())
                            .collect(Collectors.joining(genresSeparator));

                    resultStringContainer
                            .append(System.lineSeparator())
                            .append(String.format("%" + finalIdColumnWidth + "s" + columnSeparator +
                                            "%-" + finalAuthorNameColumnWidth + "s" + columnSeparator +
                                            "%-" + finalBookNameColumnWidth + "s" + columnSeparator +
                                            "%-" + finalGenresColumnWidth + "s",
                                    book.getId(), book.getAuthor(),
                                    book.getName(), genres));
                });

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
                .map(comment -> (comment.getId()).length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalIdColumnWidth = Math.max(idColumnWidth, idColumnName.length());

        int bookNameColumnWidth = comments.stream()
                .map(comment -> comment.getBook().getName().length())
                .max(Comparator.naturalOrder())
                .orElse(0);
        int finalBookNameColumnWidth = Math.max(bookNameColumnWidth, bookNameColumnName.length());

        int authorNameColumnWidth = comments.stream()
                .map(comment -> comment.getBook().getAuthor().length())
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
                                        comment.getBook().getAuthor(), comment.getText()))
                );

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
                .map(comment -> (comment.getId()).length())
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
