package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.repository"})
@EntityScan(basePackages = {"com.model"})
@EnableWebMvc
@EnableSpringDataWebSupport
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
