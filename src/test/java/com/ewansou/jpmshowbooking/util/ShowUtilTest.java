package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.model.UIShow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ShowUtilTest {

    @Autowired
    private ShowUtil showUtil;

    @ParameterizedTest
    @CsvSource(value = {
            "1, 3, 4, 0, true",
            "0, 3, 4, 2, false",
            "2, 0, 5, 1, false",
            "2, 4, 0, 1, false",
            "3, 2, 3, 0, true",
            "4, 27, 0, 0, false",
            "5, 4, 11, 0, false"
    })
    void testIsValidShowRequest(int showNumber, int numberOfRow, int numberOfSeatsPerRow,
                                int cancellationWindowInMinutes, boolean result) {
        UIShow input = new UIShow(showNumber, numberOfRow, numberOfSeatsPerRow, cancellationWindowInMinutes);
        assertEquals(showUtil.isValidShowRequest(input), result);
    }


}