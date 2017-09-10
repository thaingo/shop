package io.github.tkaczenko.provider_impl;

import io.github.tkaczenko.ScraperApplication;
import io.github.tkaczenko.loader.BaseLoader;
import io.github.tkaczenko.provider.BaseScraper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScraperApplication.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
public class ProdjScraperTest {
    private BaseScraper baseScraper;

    @Autowired
    private BaseLoader baseLoader;

    @Before
    public void setUp() throws Exception {
        baseScraper = new ProdjScraper(2, 2, 5, 15, new ProdjScraper.Extractor());
    }

    @Test
    public void testSaveSuccessfully() throws Exception {
        baseScraper.load();
        baseLoader.save(baseScraper);
        assertThat(baseScraper.getCategories().size(), equalTo(2));
        assertThat(baseScraper.getProducts().size(), equalTo(5));
    }
}