package com.tian.cloud.service.controller.response;

import com.tian.cloud.service.dao.entity.Asserts;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author tianguang
 * 2018/8/26 下午6:32
 **/
@Data
public class CompanyInfo {

    private List<PhoneInfo> phoneList;

    private List<Asserts> assertsList;

    private Company company;

    @Data
    public static class PhoneInfo {

        private int id;

        private String name;

        private List<User> userList;
    }
}
