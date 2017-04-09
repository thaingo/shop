package ua.org.javatraining.andrii_tkachenko.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by tkaczenko on 09.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class HomePage {
    private static WebDriver webDriver;

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        webDriver = new ChromeDriver();
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    @Test
    @DatabaseSetup("/data/productService.xml")
    public void home() throws Exception {
        Set<Category> set = categoryService.findAllByParent(null);
        List<Category> expected = new ArrayList<>();
        expected.addAll(set);
        expected.forEach(category -> category.setSubCategories(categoryService.findAllByParent(category)));

        webDriver.get("localhost:" + port);

        List<WebElement> categories = webDriver.findElements(By.xpath("/html/body/div/div/ul/li/a"));
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), categories.get(i).getText());
            List<WebElement> subCategories = webDriver
                    .findElements(By.xpath("/html/body/div/div/ul/li/div/a"));
            int j = 0;
            for (Category category : expected.get(i).getSubCategories()) {
                assertEquals(category.getName(), subCategories.get(i).getText());
                j++;
            }
        }
    }
}