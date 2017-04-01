package ua.org.javatraining.andrii_tkachenko.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.service.CategoryService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
public class FrontStoreControllerTest {
    @Autowired
    private CategoryService categoryService;

    @LocalServerPort
    private int port;

    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/home/tkaczenko/Desktop/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    @Test
    @DatabaseSetup("/data/productService.xml")
    @Transactional
    public void home() throws Exception {
        List<Category> expected = categoryService.findAllParentCategory();

        expected.forEach(category -> System.out.println(category.getName()));

        webDriver.get("localhost:" + port);

        System.out.println("HTML:" + webDriver.getPageSource());

        //// TODO: 01.04.17 FIX test
        /*List<WebElement> categories = webDriver
                .findElement(By.id("categories-menu"))
                .findElements(By.xpath("//ul/li/a"));


        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), categories.get(i).getText());
            List<WebElement> subCategories = categories.get(i).findElements(By.xpath("//div/a"));
            int j = 0;
            for (Category category : expected.get(i).getSubCategories()) {
                assertThat(subCategories.get(j).getText(), is(category.getName()));
                j++;
            }
        }*/
    }
}