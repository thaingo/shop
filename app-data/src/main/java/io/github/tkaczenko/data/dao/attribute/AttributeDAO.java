package io.github.tkaczenko.data.dao.attribute;


import io.github.tkaczenko.data.dao.AbstractJdbcDAO;
import io.github.tkaczenko.data.model.attribute.Attribute;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by tkaczenko on 02.03.17.
 */
public class AttributeDAO extends AbstractJdbcDAO<Attribute, String> {
    private RowMapper<Attribute> attributeRowMapper = (rs, rowNum) -> {
        Attribute attribute = new Attribute();
        attribute.setName(rs.getString("name"));
        return attribute;
    };

    @Override
    public int create(Attribute entity) {
        String sql = "insert into " + "Attribute" + "(" +
                "name" +
                ") values(?)";
        return template.update(
                sql, entity.getName()
        );
    }

    @Override
    public int update(Attribute entity) {
        String sql = "update " + "Attribute" +
                " set " +
                "name" + " = ? " +
                " where " + "name" + " = ?";
        return template.update(
                sql, entity.getName()
        );
    }

    @Override
    public Attribute get(String id) {
        String sql = "select * from " + "Attribute" + " where " +
                "name" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, attributeRowMapper);
    }
}
