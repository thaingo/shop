package io.github.tkaczenko.data.dao;

import io.github.tkaczenko.data.model.Product;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by tkaczenko on 28.02.17.
 */
public class ProductDAO extends AbstractJdbcDAO<Product, String> {
    private RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setSku(rs.getString("sku"));
        product.setAmount(rs.getInt("amount"));
        product.setDescription(rs.getString("description"));
        product.setLikes(rs.getInt("likes"));
        product.setDislikes(rs.getInt("dislikes"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getInt("price"));
        return product;
    };

    @Override
    public int create(Product entity) {
        String sql = "INSERT INTO " + "Product" + "(" +
                "sku" + ", " +
                "amount" + ", " +
                "description" + ", " +
                "likes" + ", " +
                "dislikes" + ", " +
                "name" + ", " +
                "price" +
                ")" + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        int res = template.update(
                sql, entity.getSku(), entity.getAmount(), entity.getDescription(), entity.getLikes(),
                entity.getDislikes(), entity.getName(), entity.getPrice()
        );
        return res;
    }

    @Override
    public int update(Product entity) {
        String sql = "UPDATE " + "product" +
                " SET " +
                "price" + " = ?, " +
                "amount" + " = ?, " +
                "description" + " = ?, " +
                "likes" + " = ?, " +
                "dislikes" + " = ?, " +
                "name" + " = ?" +
                " WHERE " + "sku" + " = ?";
        return template.update(
                sql, entity.getPrice(), entity.getAmount(), entity.getDescription(), entity.getLikes(),
                entity.getDislikes(), entity.getName(), entity.getSku()
        );
    }

    @Override
    public Product get(String id) {
        String sql = "SELECT * FROM " +
                "Product" + " WHERE " +
                "sku" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, productRowMapper);
    }
}
