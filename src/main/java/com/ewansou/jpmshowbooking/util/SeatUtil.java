package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.model.UIBookShow;
import com.ewansou.jpmshowbooking.model.UIShow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class SeatUtil {

    public boolean isValidBookShowRequest(UIBookShow request) {

        if (request.getShowNumber() != 0 && request.getSeats().length > 0
                && isValidMobileNumber(request.getMobileNumber())) {
            for (String seatNumber : request.getSeats()) {
                if (!isValidSeatNumber(seatNumber)) {
                    return false;
                }
            }
            return true;
        } else {
            log.info("Seat booking request is invalid");
            return false;
        }
    }

    public boolean isValidSeatNumber(String seatNumber) {
        char[] characters = seatNumber.toCharArray();

        if (characters.length > 3 || characters.length == 1) {
            return false;
        }

        if (characters.length == 2) {
            if (!Character.isLetter(seatNumber.charAt(0)) || !Character.isDigit(seatNumber.charAt(1))) {
                return false;
            }
        }

        if (characters.length == 3) {
            if (!Character.isLetter(seatNumber.charAt(0)) || !Character.isDigit(seatNumber.charAt(1))
                    || !Character.isDigit(seatNumber.charAt(2))) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidMobileNumber(long mobileNumber) {
        String regex = "(8|9)\\d{7}";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(Long.toString(mobileNumber)).matches()) {
            return true;
        }
        return false;
    }
}
