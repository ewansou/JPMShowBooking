package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.model.UIBookShow;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
class SeatUtilTest {

    @Autowired
    SeatUtil seatUtil;

    @ParameterizedTest
    @CsvSource(value = {
            "A, false",
            "A345, false",
            "A23, true",
            "*x, false",
            "12, false"
    })
    void testIsValidSeatNumber(String seatNumber, boolean result) {
        Assert.assertEquals(seatUtil.isValidSeatNumber(seatNumber), result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "97801671, true",
            "12345678990, false",
            "87879988, true",
            "123, false",
            "777, false"
    })
    void testIsValidMobileNumber(long mobileNumber, boolean result) {
        Assert.assertEquals(seatUtil.isValidMobileNumber(mobileNumber), result);
    }

    @ParameterizedTest
    @MethodSource("generateDataForBookShowRequest")
    void testIsValidSeatNumber(int showNumber, long mobileNumber, String[] seats, boolean result) {
        UIBookShow input = new UIBookShow(showNumber, mobileNumber, seats);
        Assert.assertEquals(seatUtil.isValidBookShowRequest(input), result);
    }

    static Stream<Arguments> generateDataForBookShowRequest() {
        return Stream.of(
                Arguments.of(1, 97801671, new String[]{"A1", "A2"}, true),
                Arguments.of(1, 91234567, new String[]{"B1", "B2", "B3"}, true),
                Arguments.of(0, 91234567, new String[]{"B1", "B2", "B3"}, false),
                Arguments.of(1, 91234567, new String[]{}, false),
                Arguments.of(1, 123, new String[]{}, false)
        );
    }
}