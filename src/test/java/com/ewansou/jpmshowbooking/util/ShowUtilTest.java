package com.ewansou.jpmshowbooking.util;

import com.ewansou.jpmshowbooking.model.UIShow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShowUtilTest {

    @Autowired
    ShowUtil showUtil;

    @ParameterizedTest
    @CsvSource(value = {
            "1, 3, 4, 0, true"
    })
    void testIsValidShowRequest(int showNumber, int numberOfRow, int numberOfSeatsPerRow,
                                int cancellationWindowInMinutes,  boolean result) {
        UIShow input = new UIShow(showNumber, numberOfRow, numberOfSeatsPerRow, cancellationWindowInMinutes);
        Assert.assertEquals(showUtil.isValidShowRequest(input), result);
    }





}