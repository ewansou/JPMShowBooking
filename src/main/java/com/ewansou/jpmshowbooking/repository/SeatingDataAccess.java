package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;

import java.util.List;

public interface SeatingDataAccess {
    List<SeatingEntity> findAll();
    void addSingleShowSeatingAllocation (SeatingEntity seatingEntity);
    void addListShowSeatingAllocation (List<SeatingEntity> lSeatingEntity);
    List<SeatingEntity> findByShowNumber(int showNumber);
    List<SeatingEntity> findByShowNumber(int showNumber, String seatStatus);
    SeatingEntity findBySeatNumber(int showNumber, String seatNumber);
}
