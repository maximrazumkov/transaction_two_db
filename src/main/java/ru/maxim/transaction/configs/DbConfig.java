package ru.maxim.transaction.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Primary
    @Bean(name = "datasource1")
    @ConfigurationProperties(prefix="spring.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix="spring.second")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="jdbcTemplate2")
    public JdbcTemplate initJdbcTemplate2(@Qualifier("datasource2") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name="jdbcTemplate1")
    public JdbcTemplate initJdbcTemplate1(@Qualifier("datasource1") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "transactionManager1")
    public PlatformTransactionManager getTransactionManager1(@Qualifier("datasource1") DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Bean(name = "transactionManager2")
    public PlatformTransactionManager getTransactionManager2(@Qualifier("datasource2") DataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(@Qualifier("transactionManager1") PlatformTransactionManager ds1,
                                                        @Qualifier("transactionManager2") PlatformTransactionManager ds2) {
        return new ChainedTransactionManager(ds1, ds2);
    }
}
