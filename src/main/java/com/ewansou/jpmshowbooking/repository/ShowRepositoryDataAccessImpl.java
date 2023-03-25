package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import com.ewansou.jpmshowbooking.enums.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowRepositoryDataAccessImpl implements ShowRepositoryDataAccess {

    private final ShowRepository showRepository;
    private final SeatingDataAccessImpl showSeatingAllocationDataAccessImpl;

    final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public List<ShowEntity> findAll() {
        return showRepository.findAll();
    }

    @Override
    @Transactional
    public void addShow(ShowEntity showEntity) {
        try {
            showRepository.save(showEntity);
            List<SeatingEntity> lSeatingEntity =
                    populateShowSeatingAllocation(showEntity.getShowNumber());
            showSeatingAllocationDataAccessImpl.addListShowSeatingAllocation(lSeatingEntity);
            log.info("Show number {} added into database", showEntity.getShowNumber());
            log.info("Show seatings populated into database");
        } catch (Exception e) {
            log.error("Please try again. Exception occured {}", e);
        }
    }

    public List<SeatingEntity> populateShowSeatingAllocation(int showNumber) {

        ShowEntity show = findByShowNumber(showNumber);
        List<SeatingEntity> lSeatingEntity = new ArrayList<>();

        if (show != null) {
            int numberOfRows = show.getNumberOfRows();
            int numberOfSeatsPerRow = show.getNumberOfSeatsPerRow();

            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 1; j <= numberOfSeatsPerRow; j++) {
                    SeatingEntity seatingAllocationEntity = SeatingEntity.builder()
                            .showNumber(showNumber)
                            .seatNumber(ALPHABET.charAt(i) + Integer.toString(j))
                            .seatStatus(SeatStatus.AVAILABLE.toString())
                            .build();
                    lSeatingEntity.add(seatingAllocationEntity);
                }
            }
        }
        return lSeatingEntity;
    }

    @Override
    public ShowEntity findByShowNumber(int showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
