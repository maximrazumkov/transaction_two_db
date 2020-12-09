package ru.maxim.transaction.repositories.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.maxim.transaction.repositories.ClsChangesRepo;

@Repository
public class DbClsChangesRepo implements ClsChangesRepo {

    @Qualifier("jdbcTemplate2")
    private final JdbcTemplate jdbc;

    public DbClsChangesRepo(@Qualifier("jdbcTemplate2")JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(String code, String title) {
        String query = "INSERT INTO public.cls_changes(code, title) VALUES (?, ?);";
        try {
            jdbc.update(query, code, title);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
