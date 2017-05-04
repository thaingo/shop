package ua.org.javatraining.andrii_tkachenko.data.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;

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

    public Product get(String id) {
        String sql = "select * from " +
                "Product" + " where " +
                "sku" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, productRowMapper);
    }

    public int create(Product entity) {
        String sql = "insert into " + "Product" + "(" +
                "sku" + ", " +
                "amount" + ", " +
                "description" + ", " +
                "likes" + ", " +
                "dislikes" + ", " +
                "name" + ", " +
                "price" +
                ")" + "values(?, ?, ?, ?, ?, ?, ?)";
        int res = template.update(
                sql, entity.getSku(), entity.getAmount(), entity.getDescription(), entity.getLikes(),
                entity.getDislikes(), entity.getName(), entity.getPrice()
        );
        return res;
    }

    public int update(Product entity) {
        String sql = "update " + "product" +
                " set " +
                "amount" + " = ?, " +
                "description" + " = ?, " +
                "likes" + " = ?, " +
                "dislikes" + " = ?, " +
                "name" + " = ?" +
                " where " + "sku" + " = ?";
        return template.update(
                sql, entity.getAmount(), entity.getDescription(), entity.getLikes(),
                entity.getDislikes(), entity.getName(), entity.getSku()
        );
    }
}
