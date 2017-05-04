package ua.org.javatraining.andrii_tkachenko.data.dao.attribute;

import org.springframework.jdbc.core.RowMapper;
import ua.org.javatraining.andrii_tkachenko.data.dao.AbstractJdbcDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;

import java.util.List;

/**
 * Created by tkaczenko on 03.05.17.
 */
public class AttributeAssociationDAO extends AbstractJdbcDAO<AttributeAssociationDAO, Integer> {
    private RowMapper<AttributeAssociation> associationRowMapper = (rs, rowNum) -> {
        AttributeAssociation association = new AttributeAssociation();
        association.setId(rs.getInt("id"));
        Attribute attribute = new Attribute();
        attribute.setName(rs.getString("attribute_name"));
        association.setAttribute(attribute);
        Product product = new Product();
        product.setSku(rs.getString("product_sku"));
        association.setProduct(product);
        association.setValue(rs.getString("value"));
        return association;
    };

    public List<AttributeAssociation> getAllByProduct(String sku) {
        String sql = "select * from " +
                "value" + " where " +
                "product_sku" + " = ?";
        return template.query(sql, new Object[]{sku}, associationRowMapper);
    }

//    public int create(AttributeAssociation entity) {
//        String sql = "insert into " + "value" + "(" +
//                "category_id" + ", " +
//                "product_sku" +
//                ") values(?, ?)";
//        return template.update(
//                sql, entity.getCategory().getId(), entity.getProduct().getSku()
//        );
//    }
//
//    public int update(AttributeAssociation entity) {
//        String sql = "update " + "value" +
//                " set " +
//                "category_id" + " = ?, " +
//                "product_sku" + " = ?" +
//                " where " + "category_id" + " = ?" +
//                " and " + "product_id" + " = ?";
//        return template.update(
//                sql, entity.getCategory().getId(), entity.getProduct().getSku()
//        );
//    }
}
