package com.ewansou.jpmshowbooking.service;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.model.UIBookShow;
import com.ewansou.jpmshowbooking.model.UICancelTicket;
import com.ewansou.jpmshowbooking.repository.SeatingDataAccessImpl;
import com.ewansou.jpmshowbooking.repository.SeatingRepository;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BuyerServiceTest {

    @Autowired
    BuyerService buyerService;

    @Autowired
    SeatingDataAccessImpl seatingDataAccessImpl;

    @Autowired
    ShowRepositoryDataAccessImpl showRepositoryDataAccessImpl;

    @Autowired
    SeatingRepository seatingRepository;

    @Test
    public void testAvailableSeatingsByShowNumber() {
        assertEquals(buyerService.retrieveAvailableSeatingsByShowNumber(1).size(), 2);
    }

    @Test
    void testBookSeatsWhenSameUserBookedTwice() {
        UIBookShow data = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(data);
        assertEquals(ticketBooked.size(), 1);

        //Same user proceed to book a different seat in the same show again
        UIBookShow data2 = new UIBookShow(1, 97801671, new String[]{"A2"});
        List<String> ticketBooked2 = buyerService.bookSeats(data2);
        assertEquals(ticketBooked2.size(), 0);
        assertEquals(ticketBooked2, Collections.emptyList());
    }

    @Test
    void testBookSeatsWithIncorrectSeats() {
        UIBookShow data = new UIBookShow(1, 97801671, new String[]{"Z1"});
        List<String> ticketBooked = buyerService.bookSeats(data);
        assertEquals(ticketBooked, Collections.emptyList());
    }

    @Test
    void testBookSeats() {
        UIBookShow data = new UIBookShow(1, 97801671, new String[]{"A1"});

        List<String> ticketBooked = buyerService.bookSeats(data);

        assertEquals(ticketBooked.size(), 1);
        assertEquals(Long.toString(seatingDataAccessImpl.findBySeatNumber(1, "A1")
                .getBuyerMobile()), "97801671");
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getSeatStatus(),
                SeatStatus.BOOKED.toString());
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1")
                .getTicketNumber().isEmpty(), false);
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1")
                .getVersion(), Integer.valueOf(1));
        Date seatValidTill = seatingDataAccessImpl.findBySeatNumber(1, "A1").getValidTill();
        Date expectedValidTill = new Date(System.currentTimeMillis() + (2 * 60000));
        long diffInMillies = Math.abs(expectedValidTill.getTime() - seatValidTill.getTime());
        assertEquals(diffInMillies, 0, 100); //tolerance within 100miliseconds
    }

    @Test
    void testBookSeatsWhenSeatNotAvailable() {
        UIBookShow data = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(data);
        assertEquals(ticketBooked.size(), 1);

        UIBookShow data2 = new UIBookShow(1, 90691453, new String[]{"A1"});
        assertEquals(buyerService.bookSeats(data2), Collections.emptyList());
    }

    @Test
    void testCancelTicket() {
        UIBookShow bookData = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(bookData);
        assertEquals(ticketBooked.size(), 1);

        UICancelTicket cancelData = new UICancelTicket(ticketBooked.get(0), 97801671);
        assertEquals(buyerService.cancelTicket(cancelData), "Ticket cancelled successfully");
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getSeatStatus(),
                SeatStatus.AVAILABLE.toString());
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getValidTill(),
                null);
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getTicketNumber(),
                null);
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getBuyerMobile(),
                null);
    }

    @Test
    void testCancelTicketPastExpiry() {
        UIBookShow bookData = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(bookData);
        assertEquals(ticketBooked.size(), 1);

        SeatingEntity seat = seatingDataAccessImpl.findByTicketNumber(ticketBooked.get(0));
        //After retrieving ticket, alter its validTill time for the purpose of testing
        //Set validTill time to be current time - 10 minutes
        seat.setValidTill(new Date(System.currentTimeMillis() - (10 * 60000)));
        seatingRepository.save(seat);

        UICancelTicket cancelData = new UICancelTicket(ticketBooked.get(0), 97801671);
        assertEquals(buyerService.cancelTicket(cancelData),
                "Ticket cancellation failed. Ticket passed cancellation window.");
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1").getSeatStatus(),
                SeatStatus.BOOKED.toString());
        assertEquals(seatingDataAccessImpl.findBySeatNumber(1, "A1")
                .getTicketNumber().isEmpty(), false);
        assertEquals(Long.toString(seatingDataAccessImpl.findBySeatNumber(1, "A1")
                .getBuyerMobile()), "97801671");
    }

    @Test
    void testCancelTicketWithInvalidRequest() {
        UIBookShow bookData = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(bookData);
        assertEquals(ticketBooked.size(), 1);

        UICancelTicket cancelData = new UICancelTicket(ticketBooked.get(0), 123);
        assertEquals(buyerService.cancelTicket(cancelData),
                "Ticket cancellation failed. Invalid request.");
        UICancelTicket cancelData2 = new UICancelTicket("invalidticketnumber", 97801671);
        assertEquals(buyerService.cancelTicket(cancelData),
                "Ticket cancellation failed. Invalid request.");
    }

    @Test
    void testCancelTicketWithInvalidMobileNumber() {
        UIBookShow bookData = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(bookData);
        assertEquals(ticketBooked.size(), 1);

        UICancelTicket cancelData = new UICancelTicket(ticketBooked.get(0), 90691453);
        assertEquals(buyerService.cancelTicket(cancelData),
                "Ticket cancellation failed. Ticket number and mobile number don't match.");
    }

    @Test
    void testIsValidTicketNumber() {
        UIBookShow bookData = new UIBookShow(1, 97801671, new String[]{"A1"});
        List<String> ticketBooked = buyerService.bookSeats(bookData);
        assertEquals(ticketBooked.size(), 1);

        String validTicketNumber = seatingDataAccessImpl.findByTicketNumber(ticketBooked.get(0)).getTicketNumber();

        assertEquals(buyerService.isValidTicketNumber(validTicketNumber), true);
        assertEquals(buyerService.isValidTicketNumber("randomticketnumber"), false);
    }

    @Test
    void testDifferentUserMakingBookingAtSameTime_OptimisticLocking() throws InterruptedException {
        UIBookShow bookData1 = new UIBookShow(1, 97801671, new String[]{"A1"});
        UIBookShow bookData2 = new UIBookShow(1, 90691453, new String[]{"A1"});
        List<UIBookShow> combinedBookData = Arrays.asList(bookData1, bookData2);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        for (UIBookShow uiBookShow : combinedBookData) {
            executor.execute(() -> buyerService.bookSeats(uiBookShow));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        SeatingEntity seat = seatingDataAccessImpl.findBySeatNumber(1, "A1");
        assertEquals(java.util.Optional.ofNullable(seat.getVersion()), Optional.of(1));
        assertEquals(seat.getSeatStatus(), SeatStatus.BOOKED.toString());
    }

    @Test
    void testDifferentUserMakingBookingAtSameTime_WithoutOptimisticLocking() {

    }

}