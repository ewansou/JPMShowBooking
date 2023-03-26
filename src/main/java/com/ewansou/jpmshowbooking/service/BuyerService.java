package com.ewansou.jpmshowbooking.service;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.model.UIBookShow;
import com.ewansou.jpmshowbooking.model.UICancelTicket;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.SeatingRepository;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import com.ewansou.jpmshowbooking.util.SeatUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BuyerService {
    private final SeatingRepository seatingRepository;
    private final SeatingDataAccessImpl seatingDataAccessImpl;
    private final ShowRepositoryDataAccessImpl showRepositoryDataAccessImpl;
    private final SeatUtil seatUtil;

    //SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public List<String> retrieveAvailableSeatingsByShowNumber(int showNumber) {
        List<SeatingEntity> lSeatingEntity = seatingDataAccessImpl
                .findByShowNumber(showNumber, SeatStatus.AVAILABLE.toString());
        List<String> availableSeats = new ArrayList<>();
        lSeatingEntity.forEach(s -> availableSeats.add(s.getSeatNumber()));
        return availableSeats;
    }

    public List<String> bookSeats(UIBookShow request) {
        List<String> lTicketsBooked = new ArrayList<>();

        if (seatingRepository.checkBuyerBookOnce(request.getShowNumber(), request.getMobileNumber()).size() == 0 &&
                seatUtil.isValidBookShowRequest(request)) {
            ShowEntity show = showRepositoryDataAccessImpl.findByShowNumber(request.getShowNumber());

            for (String seatNumber : request.getSeats()) {
                try {
                    SeatingEntity seatingEntity = seatingDataAccessImpl
                            .findBySeatNumber(request.getShowNumber(), seatNumber);

                    if (seatingEntity.getSeatStatus().equals(SeatStatus.AVAILABLE.toString())) {
                        seatingEntity.setSeatStatus(SeatStatus.BOOKED.toString());
                        seatingEntity.setBuyerMobile(request.getMobileNumber());
                        seatingEntity.setTicketNumber(UUID.randomUUID().toString());
                        seatingEntity.setValidTill(new Date(System.currentTimeMillis() +
                                (show.getCancellationWindowInMinutes() * 60000)));
                        seatingRepository.save(seatingEntity);
                        lTicketsBooked.add(seatingEntity.getTicketNumber());
                        log.info("Seat number {} for show number {} booked success", seatNumber,
                                request.getShowNumber());
                    } else {
                        log.warn("Seat number {}  for show number {} is not available for booking", seatNumber,
                                request.getShowNumber());
                    }
                } catch (NullPointerException e) {
                    log.warn("Invalid seat number {} for show number {}", seatNumber, request.getShowNumber());
                }
            }
        } else {
            log.warn("Request to book seats failed. Invalid booking request");
        }
        return lTicketsBooked;
    }

    public String cancelTicket(UICancelTicket request) {
        if (seatUtil.isValidCancelTicketRequest(request)) {
            SeatingEntity seatingEntity = seatingDataAccessImpl.findByTicketNumber(request.getTicket());
            if (seatingEntity.getValidTill().before(new Date(System.currentTimeMillis()))) {
                log.info("Ticket number {} can't be cancelled anymore. Passed cancellation window", request.getTicket());
                return "Ticket cancellation failed. Ticket passed cancellation window.";
            } else {
                if (seatingEntity.getBuyerMobile() != request.getMobileNumber()) {
                    log.warn("Ticket number {} does not below to user with mobile number {}. Cancel ticket failed",
                            request.getTicket(), request.getMobileNumber());
                    return "Ticket cancellation failed. Ticket number and mobile number don't match.";
                } else {
                    log.info("Cancelling ticket numeber {} in progress", request.getTicket());
                    seatingEntity.setSeatStatus(SeatStatus.AVAILABLE.toString());
                    seatingEntity.setValidTill(null);
                    seatingEntity.setTicketNumber(null);
                    seatingEntity.setBuyerMobile(null);
                    seatingRepository.save(seatingEntity);
                    log.info("Ticket cancelled successfully");
                    return "Ticket cancelled successfully";
                }
            }
        } else {
            return "Ticket cancellation failed. Invalid request.";
        }
    }


}
