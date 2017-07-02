package io.github.tkaczenko.data.dao;

import io.github.tkaczenko.data.model.CustomOrder;
import io.github.tkaczenko.data.model.OrderItem;
import io.github.tkaczenko.data.model.Product;
import org.springframework.jdbc.core.RowMapper;

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
        item.setSubtotal(rs.getBigDecimal("subtotal"));
        item.setAmount(rs.getInt("amount"));
        return item;
    };

    @Override
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

    @Override
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

    @Override
    public OrderItem get(String id) {
        return null;
    }

    public OrderItem get(String orderID, String sku) {
        String sql = "select * from " +
                "OrderItem" + " where " +
                "order_orderID" + " = ?" + " and " +
                "product_sku" + " = ?";
        return template.queryForObject(sql, new Object[]{orderID, sku}, orderItemRowMapper);
    }
}
