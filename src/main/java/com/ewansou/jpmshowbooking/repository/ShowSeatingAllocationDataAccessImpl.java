package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowSeatingAllocationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowSeatingAllocationDataAccessImpl implements  ShowSeatingAllocationDataAccess{

    private final ShowSeatingAllocationRepository showSeatingAllocationRepository;

    @Override
    public List<ShowSeatingAllocationEntity> findAll() {
        return showSeatingAllocationRepository.findAll();
    }

    @Override
    public void addSingleShowSeatingAllocation(ShowSeatingAllocationEntity showSeatingAllocationEntity) {
        showSeatingAllocationRepository.save(showSeatingAllocationEntity);
        log.info("Seating {} added for Show Number {}", showSeatingAllocationEntity.getSeatNumber(),
                showSeatingAllocationEntity.getShowNumber());
    }

    @Override
    public void addListShowSeatingAllocation(List<ShowSeatingAllocationEntity> lShowSeatingAllocationEntity) {
        for(ShowSeatingAllocationEntity entity : lShowSeatingAllocationEntity) {
            addSingleShowSeatingAllocation(entity);
        }
    }


}
