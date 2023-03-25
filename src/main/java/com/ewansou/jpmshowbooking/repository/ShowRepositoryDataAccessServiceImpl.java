package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowRepositoryDataAccessServiceImpl implements ShowRepositoryDataAccessService {

    private final ShowRepository showRepository;

    @Override
    public List<ShowEntity> findAll() {
        return showRepository.findAll();
    }

    @Override
    public void addShow(ShowEntity showEntity) {
       showRepository.save(showEntity);
       log.info("Show number {} added into DB", showEntity.getShowNumber());
    }

    @Override
    public ShowEntity findByShowNumber(int showNumber) {
        return showRepository.findByShowNumber(showNumber);
    }
}
