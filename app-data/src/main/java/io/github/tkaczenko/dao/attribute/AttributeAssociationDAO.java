package io.github.tkaczenko.dao.attribute;

import io.github.tkaczenko.dao.AbstractJdbcDAO;
import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.attribute.Attribute;
import io.github.tkaczenko.model.attribute.AttributeAssociation;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by tkaczenko on 03.05.17.
 */
public class AttributeAssociationDAO extends AbstractJdbcDAO<AttributeAssociation, Integer> {
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

    @Override
    public int create(AttributeAssociation entity) {
        String sql = "select nextval('hibernate_sequence')";
        Integer res = template.queryForObject(sql, Integer.class);
        if (res == null) {
            return 0;
        }
        sql = "insert into " + "value" + "(" +
                "id" + ", " +
                "value" + ", " +
                "attribute_name" + ", " +
                "product_sku" +
                ") values(?, ?, ?, ?)";
        return template.update(
                sql, res, entity.getValue(), entity.getAttribute().getName(), entity.getProduct().getSku()
        );
    }

    @Override
    public int update(AttributeAssociation entity) {
        String sql = "update " + "value" +
                " set " +
                "category_id" + " = ?, " +
                "product_sku" + " = ?" +
                " where " + "category_id" + " = ?" +
                " and " + "product_id" + " = ?";
        return template.update(
                sql, entity.getAttribute().getName(), entity.getProduct().getSku()
        );
    }

    @Override
    public AttributeAssociation get(Integer id) {
        return null;
    }

    public void deleteByProduct(String sku) {
        String sql = "delete from " + "value" +
                " where " + "product_sku" + "= ?";
        template.update(sql, sku);
    }

    public List<AttributeAssociation> getAllByProduct(String sku) {
        String sql = "select * from " +
                "value" + " where " +
                "product_sku" + " = ?";
        return template.query(sql, new Object[]{sku}, associationRowMapper);
    }
}
