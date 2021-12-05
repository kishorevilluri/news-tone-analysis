package com.analytical.news.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.analytical.news")
@EnableJpaRepositories(basePackages = "com.analytical.news.repository")
@EntityScan("com.analytical.news.entities")
@EnableAutoConfiguration
@ConfigurationPropertiesScan("com.analytical.news.properties")
public class NewsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
		System.out.println("App Started!");
	}
}