package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatingDataAccessImpl implements SeatingDataAccess {

    private final SeatingRepository seatingRepository;

    @Override
    public List<SeatingEntity> findAll() {
        return seatingRepository.findAll();
    }

    @Override
    public void addSingleShowSeatingAllocation(SeatingEntity seatingEntity) {
        seatingRepository.save(seatingEntity);
        log.info("Seating {} added for Show Number {}", seatingEntity.getSeatNumber(),
                seatingEntity.getShowNumber());
    }

    @Override
    public void addListShowSeatingAllocation(List<SeatingEntity> lSeatingEntity) {
        for(SeatingEntity entity : lSeatingEntity) {
            addSingleShowSeatingAllocation(entity);
        }
    }

    @Override
    public List<SeatingEntity> findByShowNumber(int showNumber) {
        return seatingRepository.findByShowNumber(showNumber);
    }

    @Override
    public List<SeatingEntity> findByShowNumber(int showNumber, String seatSatus) {
        return seatingRepository.findByShowNumber(showNumber, seatSatus);
    }

    @Override
    public SeatingEntity findBySeatNumber(int showNumber, String seatNumber) {
        return seatingRepository.findBySeatNumber(showNumber, seatNumber);
    }

    @Override
    public SeatingEntity findByTicketNumber(String ticketNumber) {
        return seatingRepository.findByTicketNumber(ticketNumber);
    }


}
