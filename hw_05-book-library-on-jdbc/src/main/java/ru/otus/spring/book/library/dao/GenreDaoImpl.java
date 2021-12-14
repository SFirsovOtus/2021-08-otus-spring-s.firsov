package ru.otus.spring.book.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;


    @Override
    public void insert(Genre genre) {
        Map<String, Object> params = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
        namedParameterJdbcOperations.update(
                "insert into genres (id, name) values (:id, :name)",
                params);
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations
                .queryForObject("select * from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("select * from genres", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations
                .update("delete from genres where id = :id" , params);
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(
                    rs.getLong("id"),
                    rs.getString("name")
            );
        }

    }

}
