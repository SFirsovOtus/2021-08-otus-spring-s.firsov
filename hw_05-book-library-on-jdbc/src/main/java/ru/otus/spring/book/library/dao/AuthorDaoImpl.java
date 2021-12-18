package ru.otus.spring.book.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public void insert(Author author) {
        Map<String, Object> params = Map.of("name", author.getName());
        namedParameterJdbcOperations.update(
                "insert into authors (name) values (:name)",
                params);
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations
                .queryForObject("select id, name from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations
                .update("delete from authors where id = :id", params);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"));
        }

    }

}
