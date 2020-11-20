package com.data.generator.boot;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication(scanBasePackages = "com.data.generator")
public class DataGeneratorBoot {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataGeneratorBoot.class, args);	
	}
	
	@Bean
	public Validator createValidatorBean() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:CommonMessages", "classpath:FolderMessages");
		return messageSource;
	}

}
