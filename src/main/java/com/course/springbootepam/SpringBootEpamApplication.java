package com.course.springbootepam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEpamApplication implements CommandLineRunner {
	
	Log log = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEpamApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Hello World !!");
	}

}
