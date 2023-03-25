package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public final class ShowValidator {

    @Autowired
    ShowRepositoryDataAccessServiceImpl showRepositoryDataAccessServiceImpl;

    public boolean isValidShowRequest(UIShow request) {
        if (request.getShowNumber() == 0 || request.getNumberOfRows() == 0 ||
                request.getNumberOfSeatsPerRow() == 0) {
            log.warn("User has entered invalid show paraemter");
            return false;
        } else {
            if(showRepositoryDataAccessServiceImpl.findByShowNumber(request.getShowNumber()) != null) {
                log.warn("Show number {} has already been added", request.getShowNumber());
                return false;
            } else {
                return true;
            }
        }
    }
}
