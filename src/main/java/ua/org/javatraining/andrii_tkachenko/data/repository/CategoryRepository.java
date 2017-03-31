package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.List;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    /*
    "select root.id as root_id" +
                ", root.name as root_name" +
                ", root.description as root_description" +
                ", root.parentcategory_id as root_parentcategory_id" +
                ", down1.id as down1_id" +
                ", down1.name as down1_name" +
                ", down1.description as down1_description" +
                ", down1.parentcategory_id as down1_parentcategory_id" +
                " from category as root " +
                " left outer " +
                "join category as down1 " +
                "on down1.parentcategory_id = root.id " +
                "order " +
                "by root.id" +
", down1.id";
     */
    List<Category> findByName(String name);
}
