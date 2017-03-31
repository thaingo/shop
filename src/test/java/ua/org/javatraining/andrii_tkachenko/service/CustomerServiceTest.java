package ua.org.javatraining.andrii_tkachenko.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;

import static org.junit.Assert.assertEquals;

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
@TestPropertySource(locations = "classpath:application.properties")
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/data/customerService.xml")
    public void save() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("home@mail.com");
        customer.setFirstName("Andrii");
        customer.setSubscribed(true);
        String email = customerService.save(customer).getEmail();
        assertEquals(customer.getEmail(), email);
    }

}