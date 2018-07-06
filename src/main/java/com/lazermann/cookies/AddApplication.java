package com.lazermann.cookies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude =  {JpaRepositoriesAutoConfiguration.class,org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class, HibernateJpaAutoConfiguration.class}) // https://github.com/spring-projects/spring-boot/issues/6987
@EnableWebMvc
@ComponentScan("com.lazermann.cookies.*")
public class AddApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddApplication.class, args);
	}
}
