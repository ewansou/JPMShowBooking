package com.ewansou.jpmshowbooking.service;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import com.ewansou.jpmshowbooking.util.ShowUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {

    private final SeatingDataAccessImpl seatingDataAccessImpl;
    private final ShowRepositoryDataAccessImpl showRepositoryDataAccessImpl;
    private final ShowUtil showUtil;

    public List<ShowEntity> retrieveAllShows() {
        return showRepositoryDataAccessImpl.findAll();
    }

    public List<SeatingEntity> retrieveAllShowsSeatings() {
        return seatingDataAccessImpl.findAll();
    }

    public List<SeatingEntity> retrieveShowSeatingsByShowNumber(int showNumber) {
        return seatingDataAccessImpl.findByShowNumber(showNumber);
    }

    public List<SeatingEntity> retrieveShowSeatingsByShowNumberAndSeatStatus(int showNumber, String seatStatus) {
        return seatingDataAccessImpl.findByShowNumber(showNumber, seatStatus);
    }

    public ShowEntity viewShow(int showNumber) {
        return showRepositoryDataAccessImpl.findByShowNumber(showNumber);
    }

    public String setupShow(UIShow request) {
        if (showUtil.isValidShowRequest(request)) {
            if (showRepositoryDataAccessImpl.findByShowNumber(request.getShowNumber()) == null) {
                ShowEntity showEntity = ShowEntity.builder()
                        .showNumber(request.getShowNumber())
                        .numberOfRows(request.getNumberOfRows())
                        .numberOfSeatsPerRow(request.getNumberOfSeatsPerRow())
                        .totalNumberOfSeats(request.getNumberOfRows() * request.getNumberOfSeatsPerRow())
                        .cancellationWindowInMinutes(request.getCancellationWindowInMinutes())
                        .build();
                showRepositoryDataAccessImpl.addShow(showEntity);
                return "success";
            } else {
                log.info("Show number {} can't be added as it is already in database", request.getShowNumber());
                return "failed";
            }
        } else {
            log.info("Invalid show request");
            return "failed";
        }
    }

}
