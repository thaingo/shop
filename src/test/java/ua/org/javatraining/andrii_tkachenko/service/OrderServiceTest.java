package ua.org.javatraining.andrii_tkachenko.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by tkaczenko on 13.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    @DatabaseSetup("/data/orderService.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/data/orderServiceExpected.xml")
    public void save() throws Exception {
        orderService.save("home@mail.com", "Mykolaiv");
    }
}