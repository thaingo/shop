package ua.org.javatraining.andrii_tkachenko.webpages;

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
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by tkaczenko on 13.04.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class CartPageTest {
    private WebDriver driver;
    private String baseUrl;

    @LocalServerPort
    private int port;

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
    public void whenProductAddedToCartCartShouldContainThisProduct() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Добавить в корзину")).click();
        driver.get(baseUrl + "/");
        driver.findElement(By.linkText("Корзина")).click();
        assertEquals("", driver.findElement(By.cssSelector("img[alt=\"Image\"]")).getText());
        assertEquals("Product 1", driver.findElement(By.xpath("//tr[2]/td[2]")).getText());
        assertEquals("100", driver.findElement(By.xpath("//td[3]")).getText());
        assertEquals("Кол-во: 1", driver.findElement(By.xpath("//tr[3]/td[2]")).getText());
        assertEquals("|Сумма: 100.0 грн|", driver.findElement(By.cssSelector("h4")).getText());
        assertEquals("|Полная стоимость: 103.0 грн|", driver.findElement(By.xpath("//tr[4]/td/h4")).getText());
    }
}