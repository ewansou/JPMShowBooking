package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.model.UIBookShow;
import com.ewansou.jpmshowbooking.model.UICancelTicket;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.SeatingRepository;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import com.ewansou.jpmshowbooking.service.BuyerService;
import com.ewansou.jpmshowbooking.util.SeatUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/buyer/api")
@RequiredArgsConstructor
@Slf4j
public class BuyerController {

    private final Gson gsonObj;
    private final BuyerService buyerService;

    @GetMapping(path = "/retrieveAvailableSeatingsByShowNumber", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<String> retrieveAvailableSeatingsByShowNumber
            (@RequestParam int showNumber) {
        log.info("Retrieving available seatings for show {}", showNumber);
        return buyerService.retrieveAvailableSeatingsByShowNumber(showNumber);
    }

    @PostMapping(path = "/bookSeats", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<String> bookSeats(@RequestBody UIBookShow request) {
        log.info("Received request to book seats: {}", gsonObj.toJson(request, UIBookShow.class));
        return buyerService.bookSeats(request);
    }

    @PostMapping(path = "/cancelTicket", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String cancelTicket(@RequestBody UICancelTicket request) {
        log.info("Received request to cancel ticket: {}", gsonObj.toJson(request, UICancelTicket.class));
        return buyerService.cancelTicket(request);
    }
}
