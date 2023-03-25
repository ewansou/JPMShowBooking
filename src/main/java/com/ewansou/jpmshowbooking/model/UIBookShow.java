package com.ewansou.jpmshowbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UIBookShow {
    private int showNumber;
    private long mobileNumber;
    private String[] seats;
}
