package io.github.tkaczenko.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import io.github.tkaczenko.model.CustomOrder;
import io.github.tkaczenko.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tkaczenko on 13.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class OrderItemServiceTest {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Test
    @DatabaseSetup("/data/orderItemService.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/data/ordrerItemServiceExpected.xml")
    public void save() throws Exception {
        CustomOrder order = orderService.findById(1);
        Map<Product, Integer> items = new LinkedHashMap<>();
        Product product1 = productService.findById("s1");
        Product product2 = productService.findById("s2");
        items.put(product1, 2);
        items.put(product2, 1);
        orderItemService.save(order, items);
    }
}