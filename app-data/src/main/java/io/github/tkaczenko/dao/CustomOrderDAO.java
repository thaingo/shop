package io.github.tkaczenko.dao;

import io.github.tkaczenko.model.CustomOrder;
import io.github.tkaczenko.model.Customer;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by tkaczenko on 28.02.17.
 */
public class CustomOrderDAO extends AbstractJdbcDAO<CustomOrder, String> {
    private RowMapper<CustomOrder> customOrderRowMapper = (rs, rowNum) -> {
        CustomOrder order = new CustomOrder();
        order.setId(rs.getInt("orderID"));
        order.setStatus(rs.getInt("status"));
        order.setAddress(rs.getString("address"));
        Customer customer = new Customer();
        customer.setEmail(rs.getString("customer_email"));
        return order;
    };

    @Override
    public int create(CustomOrder entity) {
        String sql = "insert into " + "CustomOrder" + "(" +
                "orderID" + "," +
                "status" + "," +
                "address" + "," +
                "customer_email" +
                ")" + " values(?, ?, ?, ?)";
        return template.update(
                sql, entity.getId(), entity.getStatus(), entity.getAddress(), entity.getCustomer().getEmail()
        );
    }

    @Override
    public int update(CustomOrder entity) {
        String sql = "UPDATE " + "customorder" +
                " set " +
                "orderid" + " = ?, " +
                "status" + " = ?, " +
                "address" + " = ?, " +
                "customer_email" + " = ?" +
                " WHERE " + "orderid" + " = ?";
        return template.update(
                sql, entity.getId(), entity.getStatus(), entity.getAddress(), entity.getCustomer().getEmail(),
                entity.getCustomer().getEmail()
        );
    }

    @Override
    public CustomOrder get(String id) {
        String sql = "select * from " + "CustomOrder" +
                " where " + "orderID" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, customOrderRowMapper);
    }
}
