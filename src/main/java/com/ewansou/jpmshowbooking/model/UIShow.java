package com.ewansou.jpmshowbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UIShow {
    private int showNumber;
    private int numberOfRows;
    private int numberOfSeatsPerRow;
    private int cancellationWindowInMinutes;
}
