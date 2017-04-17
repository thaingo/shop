package ua.org.javatraining.andrii_tkachenko.data.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;

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

    public CustomOrder get(String id) {
        String sql = "select * from " + "CustomOrder" +
                " where " + "orderID" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, customOrderRowMapper);
    }

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
}
