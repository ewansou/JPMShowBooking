package com.ewansou.jpmshowbooking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JpmShowBookingApplication {

    public static void main(String[] args) {
        log.info("Starting JpmShowBookingApplication...");
        SpringApplication.run(JpmShowBookingApplication.class, args);
        log.info("JpmShowBookingApplication started!");
    }
}
