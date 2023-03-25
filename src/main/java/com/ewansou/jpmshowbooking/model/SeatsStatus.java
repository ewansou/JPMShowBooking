package com.ewansou.jpmshowbooking.model;

import com.ewansou.jpmshowbooking.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatsStatus {
    int showNumber;
    String seat;
    Status status;


}
