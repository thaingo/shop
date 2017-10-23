package io.github.tkaczenko.dao;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.Visualization;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by tkaczenko on 03.03.17.
 */
public class VisualizationDAO extends AbstractJdbcDAO<Visualization, Integer> {
    private RowMapper<Visualization> visualizationRowMapper = (rs, rowNum) -> {
        Visualization visualization = new Visualization();
        visualization.setId(rs.getInt("id"));
        visualization.setType(rs.getInt("type"));
        visualization.setUrl(rs.getString("url"));
        Product product = new Product();
        product.setSku(rs.getString("product_sku"));
        return visualization;
    };

    @Override
    public int create(Visualization entity) {
        String sql = "insert into " + "visualization" + "(" +
                "id" + ", " +
                "type" + ", " +
                "url" + ", " +
                "product_sku" +
                ") values(?, ?, ?, ?)";
        return template.update(
                sql, entity.getId(), entity.getType(),
                entity.getUrl(), entity.getProduct().getSku()
        );
    }

    @Override
    public int update(Visualization entity) {
        String sql = "UPDATE " + "visualization" +
                " SET " +
                "id" + " = ?, " +
                "type" + " = ?, " +
                "url" + " = ?, " +
                "product_sku" + " = ?, " +
                "WHERE " + "id" + " = ?";
        return template.update(
                sql, entity.getId(), entity.getType(),
                entity.getUrl(), entity.getProduct().getSku(),
                entity.getProduct().getSku()
        );
    }

    @Override
    public Visualization get(Integer id) {
        String sql = "select * from " +
                "visualization" + " where " +
                "id" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, visualizationRowMapper);
    }

    public List<Visualization> getAllByProduct(String sku) {
        String sql = "select * from " +
                "visualization" + " where " +
                "product_sku" + " = ?";
        return template.query(sql, new Object[]{sku}, visualizationRowMapper);
    }
}
