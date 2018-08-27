package com.tian.cloud.service;

import org.springframework.util.StringUtils;

/**
 * @author tianguang
 * 2018/8/26 下午12:34
 **/
public class SearchUtil {

    public static final String PERCENTAGE = "%";

    public static String toSearchKeyWord(String key) {
        if (StringUtils.isEmpty(key)) {
            return key;
        }
        StringBuilder stringBuilder = new StringBuilder(key.length() * 2 + 1);

        for (char c : key.toCharArray()) {
            stringBuilder.append(PERCENTAGE).append(c);
        }
        return stringBuilder.toString();
    }
}
