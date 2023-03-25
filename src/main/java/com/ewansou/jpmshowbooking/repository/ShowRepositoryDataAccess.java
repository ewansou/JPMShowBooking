package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;

import java.util.List;

public interface ShowRepositoryDataAccess {
    List<ShowEntity> findAll();
    void addShow(ShowEntity show);
    ShowEntity findByShowNumber(int showNumber);
}
