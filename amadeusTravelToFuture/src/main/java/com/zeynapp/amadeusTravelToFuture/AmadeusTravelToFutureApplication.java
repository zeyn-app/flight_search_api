package com.zeynapp.amadeusTravelToFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmadeusTravelToFutureApplication{

	public static void main(String[] args) {
		SpringApplication.run(AmadeusTravelToFutureApplication.class, args);
	}

}
