package com.ewansou.jpmshowbooking.controller;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.service.AdminShowManagementService;
import com.ewansou.jpmshowbooking.util.ShowUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final Gson gsonObj;
    private final AdminShowManagementService adminShowManagementService;
    private final ShowUtil showValidator;

    @GetMapping(path = "/retrieveShows", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ShowEntity> retrieveShows (){
        log.info("Retrieving shows... ");
        return adminShowManagementService.loadShowsFromDb();
    }

    @GetMapping(path = "/viewShow", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ShowEntity viewShow (@RequestParam int showNumber){
        log.info("Retrieving show with number {}", showNumber);
        return adminShowManagementService.viewShowFromDB(showNumber);
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
            adminShowManagementService.addShowIntoDB(showEntity);
            return response + "success";
        } else {
            return response + "failed";
        }
    }
}
