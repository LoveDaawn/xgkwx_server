package com.yuxi.xgkwx.domain.usermng.mapper;

import com.yuxi.xgkwx.domain.usermng.User;

import java.util.Optional;

public interface UserMapper {

    Optional<User> findByUsername(String username);

    Long count();

    User save(User user);
}
