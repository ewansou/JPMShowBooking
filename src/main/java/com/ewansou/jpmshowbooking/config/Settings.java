package com.ewansou.jpmshowbooking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("settings")
public class Settings {
    private int maxSeatsPerRow;
    private int maxRows;
}
