package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;

import java.util.List;

public interface ShowRepositoryDataAccessService {
    List<ShowEntity> findAll();
    void addShow(ShowEntity show);
    ShowEntity findByShowNumber(int showNumber);
}
