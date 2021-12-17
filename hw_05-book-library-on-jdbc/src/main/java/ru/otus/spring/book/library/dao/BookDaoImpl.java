package ru.otus.spring.book.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.library.domain.Author;
import ru.otus.spring.book.library.domain.Book;
import ru.otus.spring.book.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public void insert(Book book) {
        Map<String, Object> params = Map.of("name", book.getName());
        namedParameterJdbcOperations.update(
                "insert into books (name) values (:name)",
                params);
    }

    @Override
    public void updateNameById(long id, String name) {
        Map<String, Object> params = Map.of(
                "id", id,
                "name", name
        );
        namedParameterJdbcOperations.update(
                "update books set name = :name where id = :id",
                params);
    }

    @Override
    public void updateAuthorIdById(long id, Author author) {
        Map<String, Object> params = Map.of(
                "id", id,
                "author_id", author.getId()
        );
        namedParameterJdbcOperations.update(
                "update books set author_id = :author_id where id = :id",
                params);
    }

    @Override
    public void updateGenreIdById(long id, Genre genre) {
        Map<String, Object> params = Map.of(
                "id", id,
                "genre_id", genre.getId()
        );
        namedParameterJdbcOperations.update(
                "update books set genre_id = :genre_id where id = :id",
                params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations
                .queryForObject("select b.id, b.name, a.id as author_id, a.name as author_name, g.id as genre_id, g.name as genre_name" +
                        " from books b" +
                        " left join authors a on a.id = b.author_id" +
                        " left join genres g on g.id = b.genre_id" +
                        " where b.id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("select b.id, b.name, a.id as author_id, a.name as author_name, g.id as genre_id, g.name as genre_name" +
                        " from books b" +
                        " left join authors a on a.id = b.author_id" +
                        " left join genres g on g.id = b.genre_id", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations
                .update("delete from books where id = :id", params);
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author()
                    .setId(rs.getLong("author_id"))
                    .setName(rs.getString("author_name"));

            Genre genre = new Genre()
                    .setId(rs.getLong("genre_id"))
                    .setName(rs.getString("genre_name"));

            return new Book()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setAuthor(author)
                    .setGenre(genre);
        }

    }

}
