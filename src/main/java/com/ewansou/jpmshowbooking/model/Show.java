package com.ewansou.jpmshowbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show {
    int showNumber;
    int numberOfRows;
    int numberOfSeatsPerRow;
    int cancellationWindowInMinutes;
}
