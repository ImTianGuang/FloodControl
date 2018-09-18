package com.tian.cloud.service.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilsTest {

    @Test
    public void getFileName() {
        System.out.println(FileUtils.getFileName("/var/log/test.pdf"));
    }
}