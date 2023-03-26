package com.ewansou.jpmshowbooking.service;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import com.ewansou.jpmshowbooking.model.UIShow;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Test
    @Order(1)
    public void testRetrieveAllShows() {
        Assert.assertEquals(adminService.retrieveAllShows().size(), 1);
    }

    @Test
    @Order(2)
    public void testRetrieveAllShowsSeatings() {
        Assert.assertEquals(adminService.retrieveAllShowsSeatings().size(), 2);
    }

    @Test
    @Order(3)
    public void testShowSeatingsByShowNumberAndSeatStatus() {
        List<SeatingEntity> data = adminService.retrieveShowSeatingsByShowNumberAndSeatStatus(1,
                SeatStatus.AVAILABLE.toString());
        Assert.assertEquals(data.size(), 2);
    }

    @Test
    @Order(4)
    public void testViewShow() {
        ShowEntity data = adminService.viewShow(1);
        Assert.assertEquals(Optional.ofNullable(data.getCancellationWindowInMinutes()), Optional.of(2));
        Assert.assertEquals(Optional.ofNullable(data.getNumberOfRows()), Optional.of(1));
        Assert.assertEquals(Optional.ofNullable(data.getNumberOfSeatsPerRow()), Optional.of(2));
        Assert.assertEquals(Optional.ofNullable(data.getShowNumber()), Optional.of(1));
        Assert.assertEquals(Optional.ofNullable(data.getTotalNumberOfSeats()), Optional.of(2));
    }

    @Test
    @Order(5)
    @DirtiesContext
    public void testSetupShow() {
        UIShow request = new UIShow(2, 3, 3, 2);
        Assert.assertEquals(adminService.setupShow(request), "success");
        Assert.assertEquals(adminService.retrieveAllShows().size(), 2);
        Assert.assertEquals(Optional.ofNullable(adminService.viewShow(2).getNumberOfRows()),
                Optional.of(3));
        Assert.assertEquals(Optional.ofNullable(adminService.viewShow(2).getNumberOfSeatsPerRow()),
                Optional.of(3));
        Assert.assertEquals(Optional.ofNullable(adminService.viewShow(2).getCancellationWindowInMinutes()),
                Optional.of(2));
        Assert.assertEquals(Optional.ofNullable(adminService.viewShow(2).getTotalNumberOfSeats()),
                Optional.of(9));

        Assert.assertEquals(adminService.retrieveAllShowsSeatings().size(), 11);
        Assert.assertEquals(adminService.retrieveShowSeatingsByShowNumber(2).size(), 9);
        Assert.assertEquals(adminService.retrieveShowSeatingsByShowNumberAndSeatStatus(2,
                SeatStatus.AVAILABLE.toString()).size(), 9);

        UIShow request2 = new UIShow(2, 3, 3, 2);
        Assert.assertEquals(adminService.setupShow(request2), "failed");

        UIShow request3 = new UIShow(0, 3, 3, 2);
        Assert.assertEquals(adminService.setupShow(request3), "failed");
    }
}