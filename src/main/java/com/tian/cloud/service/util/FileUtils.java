package com.tian.cloud.service.util;

import org.springframework.util.StringUtils;

/**
 * @author tianguang
 * 2018/9/17 下午10:11
 **/
public class FileUtils {

    public static String getFileName(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static String cutFileName(String fileName, int length) {
        if (StringUtils.isEmpty(fileName)) {
            return fileName;
        }
        return fileName.length() > length ? fileName.substring(0, length) : fileName;
    }
}
