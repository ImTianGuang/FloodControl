package com.tian.cloud.service.util;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void test() {
        System.out.println(DateUtil.str2Date("2017-08-08 11:00:00", DateUtil.FMT_YYYY_MM_DD_HH_MM_SS));
        System.out.println(DateUtil.instantToStr(System.currentTimeMillis()));
    }

}