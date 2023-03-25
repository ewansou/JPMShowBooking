package com.ewansou.jpmshowbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UICancelTicket {
    private String ticket;
    private long mobileNumber;
}
