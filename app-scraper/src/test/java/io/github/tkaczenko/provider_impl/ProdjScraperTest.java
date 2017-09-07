package io.github.tkaczenko.provider_impl;

import io.github.tkaczenko.ScraperApplication;
import io.github.tkaczenko.loader.BaseLoader;
import io.github.tkaczenko.provider.Scraper;
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

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScraperApplication.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
})
public class ProdjScraperTest {
    private Scraper scraper;

    @Autowired
    private BaseLoader baseLoader;

    @Before
    public void setUp() throws Exception {
        scraper = new ProdjScraper(2, 2, 15);
    }

    @Test
    public void testSaveSuccessfully() throws Exception {
        scraper.load();
        baseLoader.save(scraper);
    }
}