package com.tian.cloud.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @RequestMapping(value = "/demo-service1", method = RequestMethod.GET)
    public String demoService1(String userId) {
        System.out.println("demo-service1-############:" + userId);
        return userId;
    }
}
