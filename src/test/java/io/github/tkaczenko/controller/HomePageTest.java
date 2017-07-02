package io.github.tkaczenko.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.service.CategoryService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
public class HomePageTest {
    private WebDriver driver;
    private String baseUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    @DatabaseSetup("/data/productService.xml")
    public void whenOpenIndexPageShouldBeShownCategoriesWithSubcategories() throws Exception {
        // Expected value
        Set<Category> set = categoryService.findAllByParent(null);
        List<Category> expected = new ArrayList<>();
        expected.addAll(set);
        expected.forEach(category -> category.setSubCategories(categoryService.findAllByParent(category)));

        driver.get(baseUrl);
        List<WebElement> categories = driver.findElements(By.xpath("/html/body/div/div/ul/li/a"));
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), categories.get(i).getText());
            List<WebElement> subCategories = driver
                    .findElements(By.xpath("/html/body/div/div/ul/li/div/a"));
            int j = 0;
            for (Category category : expected.get(i).getSubCategories()) {
                assertEquals(category.getName(), subCategories.get(i).getText());
                j++;
            }
        }
    }
}