package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/buyer/api")
@RequiredArgsConstructor
@Slf4j
public class BuyerController {

    private final SeatingDataAccessImpl seatingDataAccessImpl;

    @GetMapping(path = "/retrieveAvailableSeatingsByShowNumber", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<String> retrieveAvailableSeatingsByShowNumber
            (@RequestParam int showNumber){
        log.info("Retrieving available seatings for show {}", showNumber);
        List<SeatingEntity> lSeatingEntity = seatingDataAccessImpl
                .findByShowNumber(showNumber, SeatStatus.AVAILABLE.toString());

        List<String> availableSeats = new ArrayList<>();
       lSeatingEntity.forEach(s -> availableSeats.add(s.getSeatNumber()));
       return availableSeats;
    }
}
