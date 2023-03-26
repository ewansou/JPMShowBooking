package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.config.Settings;
import com.ewansou.jpmshowbooking.model.UIShow;
import com.ewansou.jpmshowbooking.repository.ShowRepositoryDataAccessImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class ShowUtil {

    @Autowired
    Settings settings;

    @Autowired
    ShowRepositoryDataAccessImpl showRepositoryDataAccessServiceImpl;

    public boolean isValidShowRequest(UIShow request) {
        if (request.getShowNumber() == 0 || request.getNumberOfRows() == 0 ||
                request.getNumberOfSeatsPerRow() == 0) {
            log.warn("User has entered invalid show parameter");
            return false;
        }

        if (request.getNumberOfRows() > settings.getMaxRows() ||
                request.getNumberOfSeatsPerRow() > settings.getMaxSeatsPerRow()) {
            log.warn("User has entered invalid show parameter");
            return false;
        }

        if (showRepositoryDataAccessServiceImpl.findByShowNumber(request.getShowNumber()) != null) {
            log.warn("Show number {} has already been added", request.getShowNumber());
            return false;
        } else {
            return true;
        }
    }
}