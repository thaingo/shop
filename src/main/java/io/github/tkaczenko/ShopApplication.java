package io.github.tkaczenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class ShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Manages the lifecycle of the root application context
        servletContext.addListener(new RequestContextListener());
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}