package com.tian.cloud.service.dao.mapper;

import com.tian.cloud.service.dao.entity.FloodUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianguang
 * 2018/9/9 下午8:38
 **/
public interface FloodUserMapper {

    FloodUser selectByNameAndPass(@Param("userName") String userName, @Param("password") String password);

    FloodUser selectByName(@Param("userName") String userName);

    int updatePassword(@Param("id") int id, @Param("newPassword") String newPassword);

    int insert(FloodUser floodUser);

    int deleteUser(@Param("id") int id);

    List<FloodUser> selectAllUsable();
}
