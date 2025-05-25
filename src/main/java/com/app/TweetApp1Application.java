package com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class TweetApp1Application {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(TweetApp1Application.class);
		logger.debug("************* TWEET APP STARTED **********");
		SpringApplication.run(TweetApp1Application.class, args);
	}
}
