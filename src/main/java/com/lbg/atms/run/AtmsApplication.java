package com.lbg.atms.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lbg.atms")
public class AtmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmsApplication.class, args);
	}

}
