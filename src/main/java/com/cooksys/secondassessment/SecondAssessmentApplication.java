package com.cooksys.secondassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.cooksys.secondassessment.service.HashtagService;
import com.cooksys.secondassessment.service.UserService;

import cooksys.secondassessment.controller.HashtagController;
import cooksys.secondassessment.controller.UserController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan(basePackageClasses = UserService.class)
@ComponentScan(basePackageClasses = HashtagController.class)
@ComponentScan(basePackageClasses = HashtagService.class)
public class SecondAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondAssessmentApplication.class, args);
	}
}