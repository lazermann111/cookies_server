package com.lazermann.AddApplication;

import com.lazermann.AddApplication.configs.SchedulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class) // https://github.com/spring-projects/spring-boot/issues/6987
@Import({SchedulerConfig.class})
public class AddApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddApplication.class, args);
	}
}
