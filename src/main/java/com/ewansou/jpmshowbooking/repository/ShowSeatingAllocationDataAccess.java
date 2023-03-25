package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.entity.ShowSeatingAllocationEntity;

import java.util.List;

public interface ShowSeatingAllocationDataAccess {
    List<ShowSeatingAllocationEntity> findAll();
    void addSingleShowSeatingAllocation (ShowSeatingAllocationEntity showSeatingAllocationEntity);
    void addListShowSeatingAllocation (List<ShowSeatingAllocationEntity> lShowSeatingAllocationEntity);
}
