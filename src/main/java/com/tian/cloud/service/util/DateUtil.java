package com.tian.cloud.service.util;


import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tianguang
 * 2018/9/2 下午12:21
 **/
@Slf4j
public class DateUtil {

    public static final String FMT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_YYYY_MM = "yyyy-MM-dd";
    public static final String FMT_YYYY_MM1 = "yyyy/MM/dd";

    public static final Date str2Date(String input, String patten) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patten);
            Date date = simpleDateFormat.parse(input);
            return date;
        } catch (ParseException e) {
            log.error("input:{}", input, e);
            return null;
        }
    }
}
