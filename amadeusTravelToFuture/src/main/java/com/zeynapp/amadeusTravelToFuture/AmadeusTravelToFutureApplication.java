package com.zeynapp.amadeusTravelToFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
public class AmadeusTravelToFutureApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmadeusTravelToFutureApplication.class, args);
    }
}