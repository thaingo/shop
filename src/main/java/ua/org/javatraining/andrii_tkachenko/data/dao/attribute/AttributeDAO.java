package ua.org.javatraining.andrii_tkachenko.data.dao.attribute;


import org.springframework.jdbc.core.RowMapper;
import ua.org.javatraining.andrii_tkachenko.data.dao.AbstractJdbcDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;

/**
 * Created by tkaczenko on 02.03.17.
 */
public class AttributeDAO extends AbstractJdbcDAO<Attribute, String> {
    private RowMapper<Attribute> attributeRowMapper = (rs, rowNum) -> {
        Attribute attribute = new Attribute();
        attribute.setName(rs.getString("name"));
        return attribute;
    };

    public Attribute get(String id) {
        String sql = "select * from " + "Attribute" + " where " +
                "name" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, attributeRowMapper);
    }

    public int create(Attribute entity) {
        String sql = "insert into " + "Attribute" + "(" +
                "name" +
                ") values(?)";
        return template.update(
                sql, entity.getName()
        );
    }

    public int update(Attribute entity) {
        String sql = "update " + "Attribute" +
                " set " +
                "name" + " = ? " +
                " where " + "name" + " = ?";
        return template.update(
                sql, entity.getName()
        );
    }
}
