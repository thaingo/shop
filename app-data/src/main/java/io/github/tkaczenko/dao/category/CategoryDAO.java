package io.github.tkaczenko.dao.category;

import io.github.tkaczenko.dao.AbstractJdbcDAO;
import io.github.tkaczenko.model.category.Category;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by tkaczenko on 02.03.17.
 */
public class CategoryDAO extends AbstractJdbcDAO<Category, Integer> {
    private RowMapper<Category> categoryRowMapper = (rs, rowNum) -> {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setDescription(rs.getString("description"));
        category.setName(rs.getString("name"));
        Category parentCategory = new Category();
        parentCategory.setId(rs.getInt("parent_category_id"));
        return category;
    };

    @Override
    public int create(Category entity) {
        String sql = "insert into " + "Category" + "(" +
                "id" + ", " +
                "description" + ", " +
                "name" + ", " +
                "parentCategory_id" +
                ") values(?, ?, ?, ?)";
        return template.update(
                sql, entity.getId(), entity.getDescription(), entity.getName(), entity.getParentCategory().getId()
        );
    }

    @Override
    public int update(Category entity) {
        String sql = "update " + "Category" +
                " set " +
                "id" + " = ?, " +
                "description" + " = ?, " +
                "name" + " = ?, " +
                "parentCategory_id" + " = ? " +
                " where " + "id" + " = ?";
        return template.update(
                sql, entity.getId(), entity.getDescription(), entity.getName(), entity.getParentCategory().getId()
        );
    }

    @Override
    public Category get(Integer id) {
        String sql = "select * from " + "Category" + " where " +
                "id" + " = ?";
        return template.queryForObject(sql, new Object[]{id}, categoryRowMapper);
    }

    public List<Category> findAll() {
        String sql = "select * from" + "Category";
        return template.query(sql, new Object[]{}, categoryRowMapper);
    }
}
