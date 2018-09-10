package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.FloodUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author tianguang
 * 2018/9/9 下午8:38
 **/
public interface FloodUserMapper {

    FloodUser selectByNameAndPass(@Param("userName") String userName, @Param("password") String password);
}
