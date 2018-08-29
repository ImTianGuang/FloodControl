package com.tian.cloud.service;

import com.tian.cloud.service.boot.Application;
import com.tian.cloud.service.dao.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author tianguang
 * 2018/8/29 下午8:15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestBase {

    @Test
    public void test() {
        System.out.println(1);
    }

}
