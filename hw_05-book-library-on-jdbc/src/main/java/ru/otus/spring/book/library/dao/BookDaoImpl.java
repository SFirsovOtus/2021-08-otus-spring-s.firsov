package ru.otus.spring.book.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.library.domain.Book;

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
        Map<String, Object> params = Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "author_id", book.getAuthorId(),
                "genre_id", book.getGenreId()
        );
        namedParameterJdbcOperations.update(
                "insert into books (id, name, author_id, genre_id) values (:id, :name, :author_id, :genre_id)",
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
    public void updateAuthorIdById(long id, long authorId) {
        Map<String, Object> params = Map.of(
                "id", id,
                "author_id", authorId
        );
        namedParameterJdbcOperations.update(
                "update books set author_id = :author_id where id = :id",
                params);
    }

    @Override
    public void updateGenreIdById(long id, long genreId) {
        Map<String, Object> params = Map.of(
                "id", id,
                "genre_id", genreId
        );
        namedParameterJdbcOperations.update(
                "update books set genre_id = :genre_id where id = :id",
                params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations
                .queryForObject("select * from books where id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("select * from books", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations
                .update("delete from books where id = :id" , params);
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("author_id"),
                    rs.getLong("genre_id")
            );
        }

    }

}
