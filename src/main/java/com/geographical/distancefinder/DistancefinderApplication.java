package com.geographical.distancefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = { "com.geographical.distancefinder" })
@EnableSwagger2
public class DistancefinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistancefinderApplication.class, args);
	}
}