package io.github.tkaczenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"io.github.tkaczenko.model"})
@EnableJpaRepositories(basePackages = {"io.github.tkaczenko.repository"})
public class ScraperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }
}
