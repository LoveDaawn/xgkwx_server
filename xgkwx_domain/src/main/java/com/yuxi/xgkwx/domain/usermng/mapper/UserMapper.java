package com.yuxi.xgkwx.domain.usermng.mapper;

import com.yuxi.xgkwx.domain.usermng.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    public int registerNewUser(User user);

    Optional<User> findByUnifyId(String username);

    Long count();

    User save(User user);
}
