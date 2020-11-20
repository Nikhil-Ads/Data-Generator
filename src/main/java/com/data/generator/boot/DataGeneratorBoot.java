package com.data.generator.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.data.generator")
public class DataGeneratorBoot {

	public DataGeneratorBoot() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataGeneratorBoot.class, args);	
	}

}
