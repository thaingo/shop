package ua.org.javatraining.andrii_tkachenko.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by tkaczenko on 12.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@TestPropertySource(locations = "classpath:application.properties")
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    @DatabaseSetup("/data/categoryService.xml")
    public void whenRowsExistsShouldReturnThose() throws Exception {
        List<Category> actual = categoryService.findAllParentCategory();
        assertEquals(2, actual.size());
        assertThat(actual.size(), is(6));
        assertThat(actual.get(0), allOf(
                hasProperty("id", is(1)),
                hasProperty("name", is("Category 1")),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
    }

    @Test
    @DatabaseSetup("/data/categoryService.xml")
    public void whenRowWithNameExistShouldReturnThat() throws Exception {
        List<Category> actual = categoryService.findByName("Category 1");
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), allOf(
                hasProperty("id", is(1)),
                hasProperty("name", is("Category 1")),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
    }

    @Test
    @DatabaseSetup("/data/categoryService.xml")
    public void whenRowWithNameIsNotExistShouldReturnNull() throws Exception {
        List<Category> actual = categoryService.findByName("Category 3");
        assertThat(actual.size(), is(0));
    }

    @Test
    @DatabaseSetup("/data/categoryService.xml")
    public void whenRowsWithNameExistShouldReturnList() throws Exception {
        List<Category> actual = categoryService.findByName("Subcategory 1");
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0), allOf(
                hasProperty("id", is(2)),
                hasProperty("name", is("Subcategory 1")),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
        assertThat(actual.get(1), allOf(
                hasProperty("id", is(5)),
                hasProperty("name", is("Subcategory 1")),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
    }
}