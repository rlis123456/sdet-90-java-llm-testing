package com.rashmi.sdet90.smoke;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest {

    @Test
    public void firstPassingTest() {
        int expected = 2;
        int actual = 1 + 1;
        Assert.assertEquals(actual, expected, "Math should work 🙂");
    }
}