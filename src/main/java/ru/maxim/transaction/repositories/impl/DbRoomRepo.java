package ru.maxim.transaction.repositories.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.maxim.transaction.repositories.RoomRepo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Repository
public class DbRoomRepo implements RoomRepo {

    @Qualifier("jdbcTemplate1")
    private final JdbcTemplate jdbc;

    public DbRoomRepo(@Qualifier("jdbcTemplate1") JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer save(final String title) {
        KeyHolder key = new GeneratedKeyHolder();
        String query = "insert into room(name) values(?)";
        try {
            jdbc.update(connect -> {
                PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, title);
                return ps;
            }, key);
            Map<String, Object> result = key.getKeys();
            return (Integer) result.get("id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
