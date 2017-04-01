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
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.Visualization;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    @DatabaseSetup("/data/productService.xml")
    public void whenOneRowExistsShouldReturnOneProductByCategory() throws Exception {
        Set<Product> actual = productService.findAllByCategoryId(2);
        assertThat(actual.size(), is(1));
        assertThat(actual.iterator().next(), allOf(
                hasProperty("sku", is("s1")),
                hasProperty("name", is("Product 1")),
                hasProperty("price", is(100)),
                hasProperty("amount", is(10)),
                hasProperty("likes", is(5)),
                hasProperty("dislikes", is(2)),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
    }

    @Test
    @DatabaseSetup("/data/productService.xml")
    public void whenOneRowExistsShouldReturnOneProduct() throws Exception {
        Product actual = productService.findById("s1");
        assertThat(actual, allOf(
                hasProperty("sku", is("s1")),
                hasProperty("name", is("Product 1")),
                hasProperty("price", is(100)),
                hasProperty("amount", is(10)),
                hasProperty("likes", is(5)),
                hasProperty("dislikes", is(2)),
                hasProperty("description", is("Lorem ipsum dolor sit amet"))
        ));
        Set<AttributeAssociation> attributes = actual.getAttributes();
        assertThat(attributes.size(), is(3));
        assertThat(attributes, containsInAnyOrder(
                allOf(hasProperty("value", is("Cool stuff"))),
                allOf(hasProperty("value", is("Cool stuff"))),
                allOf(hasProperty("value", is("Cool stuff")))
                )
        );
        Set<CategoryAssociation> categories = actual.getCategories();
        assertThat(categories.size(), is(2));
        assertThat(categories, containsInAnyOrder(
                allOf(hasProperty("id", is(1))),
                allOf(hasProperty("id", is(2)))
                )
        );
        Set<Visualization> visualizations = actual.getVisualizations();
        assertThat(visualizations.size(), is(3));
        assertThat(visualizations, containsInAnyOrder(
                allOf(hasProperty("url", is("url1"))),
                allOf(hasProperty("url", is("url2"))),
                allOf(hasProperty("url", is("url3")))
        ));
    }

}