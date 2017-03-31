package ua.org.javatraining.andrii_tkachenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ua.org.javatraining.andrii_tkachenko.data.model"})
@EnableJpaRepositories(basePackages = {"ua.org.javatraining.andrii_tkachenko.data.repository"})
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
