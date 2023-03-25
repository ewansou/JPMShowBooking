package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.model.UIBookShow;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.SeatingRepository;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
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
    private final SeatingRepository seatingRepository;
    private final SeatingDataAccessImpl seatingDataAccessImpl;
    private final ShowRepositoryDataAccessImpl showRepositoryDataAccessImpl;
    private final SeatUtil seatUtil;
    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @GetMapping(path = "/retrieveAvailableSeatingsByShowNumber", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<String> retrieveAvailableSeatingsByShowNumber
            (@RequestParam int showNumber) {
        log.info("Retrieving available seatings for show {}", showNumber);
        List<SeatingEntity> lSeatingEntity = seatingDataAccessImpl
                .findByShowNumber(showNumber, SeatStatus.AVAILABLE.toString());

        List<String> availableSeats = new ArrayList<>();
        lSeatingEntity.forEach(s -> availableSeats.add(s.getSeatNumber()));
        return availableSeats;
    }

    @PostMapping(path = "/bookSeats", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String bookSeats(@RequestBody UIBookShow request) {
        log.info("Received request to book seats: {}", gsonObj.toJson(request, UIBookShow.class));

        if (seatUtil.isValidBookShowRequest(request)) {
            ShowEntity show = showRepositoryDataAccessImpl.findByShowNumber(request.getShowNumber());

            for (String seatNumber : request.getSeats()) {
                try {
                    SeatingEntity seatingEntity = seatingDataAccessImpl
                            .findBySeatNumber(request.getShowNumber(), seatNumber);

                    if (seatingEntity.getSeatStatus().equals(SeatStatus.AVAILABLE.toString())) {
                        seatingEntity.setSeatStatus(SeatStatus.SOFTBOOKED.toString());
                        seatingEntity.setBuyerMobile(request.getMobileNumber());
                        seatingEntity.setTicketNumber(UUID.randomUUID().toString());
                        seatingEntity.setValidTill(new Date(System.currentTimeMillis() +
                                (show.getCancellationWindowInMinutes() * 60000)));
                        seatingRepository.save(seatingEntity);

                        log.info("Seat number {} for show number {} softbooked success", seatNumber,
                                request.getShowNumber());
                    } else {
                        log.warn("Seat number {}  for show number {} is not available for booking", seatNumber,
                                request.getShowNumber());
                    }
                } catch (NullPointerException e) {
                    log.warn("Invalid seat number {} for show number {}", seatNumber, request.getShowNumber());
                }
            }
            return "Booking done";
        } else {
            return "Request to book seats failed. Invalid booking request";
        }
    }
}
