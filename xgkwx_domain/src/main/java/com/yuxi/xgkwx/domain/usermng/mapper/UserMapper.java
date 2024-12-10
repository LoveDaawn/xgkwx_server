package com.yuxi.xgkwx.domain.usermng.mapper;

import com.yuxi.xgkwx.domain.usermng.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public int registerNewUser(User user);

    public User getUserInfoById(String unifyId);

    public int updateUserInfo(User user);

    public String getUserPasswordById(String unifyId);

    public int updateUserPassword(User user);

    public int deleteUserById(String unifyId);

    Long count();

    User save(User user);
}
