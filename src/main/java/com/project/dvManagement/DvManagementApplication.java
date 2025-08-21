package com.project.dvManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DvManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DvManagementApplication.class, args);
	}

}
