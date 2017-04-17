package ua.org.javatraining.andrii_tkachenko.data.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by tkaczenko on 17.04.17.
 */
public abstract class AbstractJdbcDAO<E, I> {
    protected DataSource dataSource;
    protected JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
    }
}
