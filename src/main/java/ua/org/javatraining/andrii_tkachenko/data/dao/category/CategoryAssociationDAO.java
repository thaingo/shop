package ua.org.javatraining.andrii_tkachenko.data.dao.category;

import org.springframework.jdbc.core.RowMapper;
import ua.org.javatraining.andrii_tkachenko.data.dao.AbstractJdbcDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;

import java.util.List;

/**
 * Created by tkaczenko on 03.03.17.
 */
//// TODO: 06.03.17 Use sequance
public class CategoryAssociationDAO extends AbstractJdbcDAO<CategoryAssociation, String> {
    private RowMapper<CategoryAssociation> associationRowMapper = (rs, rowNum) -> {
        CategoryAssociation association = new CategoryAssociation();
        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        association.setCategory(category);
        Product product = new Product();
        product.setSku(rs.getString("product_sku"));
        association.setProduct(product);
        return association;
    };

    public CategoryAssociation get(String categoryID, String sku) {
        String sql = "select * from " +
                "categoryassociation" + " where " +
                "category_id" + " = ?" + " and " +
                "product_sku" + " = ?";
        return template.queryForObject(sql, new Object[]{categoryID, sku}, associationRowMapper);
    }

    public List<CategoryAssociation> getAllByCategory(int category) {
        String sql = "select * from " +
                "Category" + " where " +
                "category_id" + " = ?";
        return template.query(sql, new Object[]{category}, associationRowMapper);
    }

    public int create(CategoryAssociation entity) {
        String sql = "insert into " + "Category" + "(" +
                "category_id" + ", " +
                "product_sku" +
                ") values(?, ?)";
        return template.update(
                sql, entity.getCategory().getId(), entity.getProduct().getSku()
        );
    }

    public int update(CategoryAssociation entity) {
        String sql = "update " + "Category" +
                " set " +
                "category_id" + " = ?, " +
                "product_sku" + " = ?" +
                " where " + "category_id" + " = ?" +
                " and " + "product_id" + " = ?";
        return template.update(
                sql, entity.getCategory().getId(), entity.getProduct().getSku()
        );
    }
}
