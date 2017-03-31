package ua.org.javatraining.andrii_tkachenko.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.logging.Level;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tkaczenko on 31.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@TestPropertySource(locations = "classpath:application.properties")
public class FrontStoreControllerTest {
    @Autowired
    private WebApplicationContext context;

    @LocalServerPort
    private int port;

    private WebDriver webDriver;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/home/tkaczenko/Desktop/chromedriver");
        webDriver = new ChromeDriver();
        ((RemoteWebDriver) webDriver).setLogLevel(Level.SEVERE);
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    @Test
    @Transactional
    @DatabaseSetup(value = "/data/productService.xml")
    public void home() throws Exception {
        this.webDriver.get("localhost:" + port);
        List<WebElement> categories = webDriver.findElements(By.xpath("//div/div/ul/li"));
        List<Category> expected = categoryService.findAll();
        for (int i = 0; i < expected.size(); i++) {
            assertThat(categories.get(i).getText(), is(expected.get(i).getName()));
            List<WebElement> subCategories = categories.get(i).findElements(By.xpath("//div/a"));
            int j = 0;
            for (Category category : expected.get(i).getSubCategories()) {
                assertThat(subCategories.get(j).getText(), is(category.getName()));
                j++;
            }
        }
    }
}