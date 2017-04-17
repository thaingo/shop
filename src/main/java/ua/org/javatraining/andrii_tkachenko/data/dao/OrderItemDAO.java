package ua.org.javatraining.andrii_tkachenko.data.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.OrderItem;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;

/**
 * Created by tkaczenko on 03.03.17.
 */
public class OrderItemDAO extends AbstractJdbcDAO<OrderItem, String> {
    private RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> {
        OrderItem item = new OrderItem();
        Product product = new Product();
        product.setSku(rs.getString("product_sku"));
        CustomOrder order = new CustomOrder();
        order.setId(rs.getInt("order_orderID"));
        item.setProduct(product);
        item.setOrder(order);
        item.setSubtotal(rs.getInt("subtotal"));
        item.setAmount(rs.getInt("amount"));
        return item;
    };

    public OrderItem get(String orderID, String sku) {
        String sql = "select * from " +
                "OrderItem" + " where " +
                "order_orderID" + " = ?" + " and " +
                "product_sku" + " = ?";
        return template.queryForObject(sql, new Object[]{orderID, sku}, orderItemRowMapper);
    }

    public int create(OrderItem entity) {
        String sql = "insert into " + "OrderItem" + "(" +
                "order_orderID" + ", " +
                "product_sku" + ", " +
                "amount" + ", " +
                "subtotal" +
                ") values(?, ?, ?, ?)";
        return template.update(
                sql, entity.getOrder().getId(), entity.getProduct().getSku(),
                entity.getAmount(), entity.getSubtotal()
        );
    }

    public int update(OrderItem entity) {
        String sql = "UPDATE " + "OrderItem" +
                " SET " +
                "order_orderID" + " = ?, " +
                "product_sku" + " = ?, " +
                "amount" + " = ?, " +
                "subtotal" + " = ?" +
                " WHERE " +
                "order_orderID" + " = ?" + " and " +
                "product_sku" + " = ?";
        return template.update(
                sql, entity.getOrder().getId(), entity.getProduct().getSku(),
                entity.getAmount(), entity.getSubtotal(),
                entity.getOrder().getId(), entity.getProduct().getSku()
        );
    }
}
