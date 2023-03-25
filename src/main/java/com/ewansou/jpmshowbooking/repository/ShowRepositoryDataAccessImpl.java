package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.entity.ShowSeatingAllocationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowRepositoryDataAccessImpl implements ShowRepositoryDataAccess {

    private final ShowRepository showRepository;
    private final ShowSeatingAllocationDataAccessImpl showSeatingAllocationDataAccessImpl;

    @Override
    public List<ShowEntity> findAll() {
        return showRepository.findAll();
    }

    @Override
    @Transactional
    public void addShow(ShowEntity showEntity) {
        try {
            showRepository.save(showEntity);
            log.info("Show number {} added into DB", showEntity.getShowNumber());

            List<ShowSeatingAllocationEntity> lShowSeatingAllocationEntity =
                    populateShowSeatingAllocation(showEntity.getShowNumber());
            showSeatingAllocationDataAccessImpl.addListShowSeatingAllocation(lShowSeatingAllocationEntity);

        } catch (Exception e) {
            log.error("Please try again. Exception occured {}", e);
        }
    }

    public List<ShowSeatingAllocationEntity> populateShowSeatingAllocation(int showNumber) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        ShowEntity show = findByShowNumber(showNumber);

        if (show != null) {
            int numberOfRows = show.getNumberOfRows();
            int numberOfSeatsPerRow = show.getNumberOfSeatsPerRow();
            String[] seats = new String[show.getTotalNumberOfSeats()];

            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 1; j <= numberOfSeatsPerRow; j++) {
                    log.info(alphabet.charAt(i) + Integer.toString(j));
                }
            }

        }
        return null;
    }

    @Override
    public ShowEntity findByShowNumber(int showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
