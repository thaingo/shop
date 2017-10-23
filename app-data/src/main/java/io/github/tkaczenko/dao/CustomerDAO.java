package io.github.tkaczenko.dao;

import io.github.tkaczenko.model.Customer;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by tkaczenko on 27.02.17.
 */
public class CustomerDAO extends AbstractJdbcDAO<Customer, String> {
    private RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        Customer customer = new Customer();
        customer.setEmail(rs.getString("email"));
        customer.setFirstName(rs.getString("firstName"));
        customer.setSubscribed(rs.getBoolean("subscribed"));
        return customer;
    };

    @Override
    public int create(Customer entity) {
        String sql = "insert into " + "Customer" + "(" +
                "email" + ", " +
                "firstName" + ", " +
                "subscribed" +
                ") values(?, ?, ?)";
        return template.update(
                sql, entity.getEmail(), entity.getFirstName(), entity.isSubscribed()
        );
    }

    @Override
    public int update(Customer entity) {
        String sql = "update " + "Customer" +
                " set " +
                "email" + "= ?, " +
                "firstName" + "= ?, " +
                "subscribed" + "= ?" +
                " WHERE " + "email" + " = ?";
        return template.update(
                sql, entity.getEmail(), entity.getFirstName(), entity.isSubscribed(), entity.getEmail()
        );
    }

    @Override
    public Customer get(String id) {
        String sql = "select * from " + "Customer" + " where " +
                "email" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, customerRowMapper);
    }
}
