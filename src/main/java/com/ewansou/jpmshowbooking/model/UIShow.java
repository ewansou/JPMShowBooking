package com.ewansou.jpmshowbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UIShow {
    private int showNumber;
    private int numberOfRows;
    private int numberOfSeatsPerRow;
    private int cancellationWindowInMinutes;
}
