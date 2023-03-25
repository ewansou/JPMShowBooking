package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.service.AdminService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/admin/api")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final Gson gsonObj;
    private final AdminService adminService;

    @GetMapping(path = "/retrieveShows", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ShowEntity> retrieveShows() {
        log.info("Retrieving shows... ");
        return adminService.retrieveAllShows();
    }

    @GetMapping(path = "/retrieveAllShowsSeatings", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveAllShowsSeatings() {
        log.info("Retrieving all shows seatings.. ");
        return adminService.retrieveAllShowsSeatings();
    }

    @GetMapping(path = "/retrieveShowSeatingsByShowNumber", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveShowSeatingsByShowNumber(@RequestParam int showNumber) {
        log.info("Retrieving seatings for show {}", showNumber);
        return adminService.retrieveShowSeatingsByShowNumber(showNumber);
    }

    @GetMapping(path = "/retrieveShowSeatingsByShowNumberAndSeatStatus", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveShowSeatingsByShowNumberAndSeatStatus
            (@RequestParam Map<Integer, String> requestParms) {
        int showNumber = Integer.parseInt(requestParms.get("showNumber"));
        String seatStatus = requestParms.get("seatStatus");
        log.info("Retrieving seatings with status {} for show {}", seatStatus, showNumber);
        return adminService.retrieveShowSeatingsByShowNumberAndSeatStatus(showNumber, seatStatus);
    }

    @GetMapping(path = "/viewShow", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ShowEntity viewShow(@RequestParam int showNumber) {
        log.info("Retrieving details of show {}", showNumber);
        return adminService.viewShow(showNumber);
    }

    @PostMapping(path = "/setupShow", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String setupShow(@RequestBody UIShow request) {
        log.info("Received request to addShow: {}", gsonObj.toJson(request, UIShow.class));
        String response = "Show " + request.getShowNumber() + " added ";
        return response + adminService.setupShow(request);
    }
}
