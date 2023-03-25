package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import com.ewansou.jpmshowbooking.entity.ShowSeatingAllocationEntity;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public final class ShowUtil {

    @Autowired
    ShowRepositoryDataAccessImpl showRepositoryDataAccessServiceImpl;

    public boolean isValidShowRequest(UIShow request) {
        if (request.getShowNumber() == 0 || request.getNumberOfRows() == 0 ||
                request.getNumberOfSeatsPerRow() == 0) {
            log.warn("User has entered invalid show paraemter");
            return false;
        } else {
            if (showRepositoryDataAccessServiceImpl.findByShowNumber(request.getShowNumber()) != null) {
                log.warn("Show number {} has already been added", request.getShowNumber());
                return false;
            } else {
                return true;
            }
        }
    }
}