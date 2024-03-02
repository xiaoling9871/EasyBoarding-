package com.example.s3service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class S3ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3ServiceApplication.class, args);
	}

}
