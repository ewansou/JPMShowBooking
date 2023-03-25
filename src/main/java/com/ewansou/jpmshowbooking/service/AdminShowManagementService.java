package com.ewansou.jpmshowbooking.service;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminShowManagementService {
    private final Gson gsonObj;
    private final ShowRepositoryDataAccessImpl showRepositoryDataAccessServiceImpl;

    public List<ShowEntity> loadShowsFromDb() {
        return showRepositoryDataAccessServiceImpl.findAll();
    }

    public void addShowIntoDB(ShowEntity showEntity) {
        showRepositoryDataAccessServiceImpl.addShow(showEntity);
    }

    public ShowEntity viewShowFromDB(int showNumber) {
        return showRepositoryDataAccessServiceImpl.findByShowNumber(showNumber);
    }
}
