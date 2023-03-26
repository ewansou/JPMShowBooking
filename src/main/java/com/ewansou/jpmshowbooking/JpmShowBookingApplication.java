package com.ewansou.jpmshowbooking;

import com.ewansou.jpmshowbooking.config.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties({Settings.class})
public class JpmShowBookingApplication {

    public static void main(String[] args) {
        log.info("Starting JpmShowBookingApplication...");
        SpringApplication.run(JpmShowBookingApplication.class, args);
        log.info("JpmShowBookingApplication started!");
    }
}
