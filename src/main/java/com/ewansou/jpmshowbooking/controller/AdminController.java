package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.util.ShowUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
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
    private final SeatingDataAccessImpl seatingDataAccessImpl;
    private final ShowRepositoryDataAccessImpl showRepositoryDataAccessImpl;
    private final ShowUtil showValidator;

    @GetMapping(path = "/retrieveShows", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ShowEntity> retrieveShows (){
        log.info("Retrieving shows... ");
        return showRepositoryDataAccessImpl.findAll();
    }

    @GetMapping(path = "/retrieveAllShowsSeatings", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveAllShowsSeatings (){
        log.info("Retrieving all shows seatings.. ");
        return seatingDataAccessImpl.findAll();
    }

    @GetMapping(path = "/retrieveShowSeatingsByShowNumber", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveShowSeatingsByShowNumber (@RequestParam int showNumber){
        log.info("Retrieving seatings for show {}", showNumber);
        return seatingDataAccessImpl.findByShowNumber(showNumber);
    }

    @GetMapping(path = "/retrieveShowSeatingsByShowNumberAndSeatStatus", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<SeatingEntity> retrieveShowSeatingsByShowNumberAndSeatStatus
            (@RequestParam Map<Integer, String> requestParms){
        int showNumber = Integer.parseInt(requestParms.get("showNumber"));
        String seatStatus = requestParms.get("seatStatus");
        log.info("Retrieving seatings with status {} for show {}", seatStatus, showNumber);
        return seatingDataAccessImpl.findByShowNumber(showNumber, seatStatus);
    }

    @GetMapping(path = "/viewShow", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ShowEntity viewShow (@RequestParam int showNumber){
        log.info("Retrieving details of show {}", showNumber);
        return showRepositoryDataAccessImpl.findByShowNumber(showNumber);
    }

    @PostMapping(path = "/setupShow", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String setupShow(@RequestBody UIShow request){
        log.info("Received request to addShow: {}", gsonObj.toJson(request, UIShow.class));

        String response = "Show " + request.getShowNumber() + " added ";

        if (showValidator.isValidShowRequest(request)) {
            ShowEntity showEntity = ShowEntity.builder()
                    .showNumber(request.getShowNumber())
                    .numberOfRows(request.getNumberOfRows())
                    .numberOfSeatsPerRow(request.getNumberOfSeatsPerRow())
                    .totalNumberOfSeats(request.getNumberOfRows() * request.getNumberOfSeatsPerRow())
                    .cancellationWindowInMinutes(request.getCancellationWindowInMinutes())
                    .build();
            showRepositoryDataAccessImpl.addShow(showEntity);
            return response + "success";
        } else {
            return response + "failed";
        }
    }
}
