package io.github.tkaczenko.data.session;

import io.github.tkaczenko.data.model.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tkaczenko on 13.03.17.
 */
public class CartTest {
    private Cart cart;
    private List<Product> testData;

    @Before
    public void setUp() throws Exception {
        Product prod1 = new Product();
        prod1.setSku("s1");
        prod1.setName("Product_1");
        prod1.setDescription("Lorem ipsum");
        prod1.setPrice(BigDecimal.valueOf(25));
        prod1.setAmount(10);

        Product prod2 = new Product();
        prod2.setSku("s2");
        prod2.setName("Product_2");
        prod2.setDescription("Lorem ipsum");
        prod2.setAmount(20);
        prod2.setPrice(BigDecimal.valueOf(30));

        testData = new ArrayList<>();
        testData.add(prod1);
        testData.add(prod2);

        cart = new Cart();
        for (int i = 0; i < 2; i++) {
            cart.addItem(testData.get(0));
        }
        cart.addItem(testData.get(1));
    }

    @After
    public void tearDown() throws Exception {
        cart = null;
        testData.clear();
    }

    @Test
    public void testGetItems() {
        assertThat(cart.getItems().size(), is(2));
    }

    @Test
    public void testAddItems() {
        assertThat(cart.getItems().keySet(), containsInAnyOrder(testData.get(0), testData.get(1)));
        assertThat(cart.getItems().get(testData.get(0)), is(2));
        assertThat(cart.getItems().get(testData.get(1)), is(1));
    }

    @Test
    public void testClear() {
        cart.clear();
        assertThat(cart.getItems().size(), is(0));
    }

    @Test
    public void testUpdateItem() {
        cart.updateItem("s2", 2);
        assertThat(cart.getItems().keySet(), containsInAnyOrder(testData.get(0), testData.get(1)));
        assertThat(cart.getItems().get(testData.get(0)), is(2));
        assertThat(cart.getItems().get(testData.get(1)), is(2));
    }

    @Test
    public void testCalculateSubTotal() {
        assertThat(cart.calculateSubTotal(), is(80.0));
    }

    @Test
    public void testCalculateTotal() {
        assertThat(cart.calculateTotal(80.00), is(83.0));
    }

    @Test
    public void testSumQuantity() {
        assertThat(cart.sumQuantity(), is(3));
    }
}