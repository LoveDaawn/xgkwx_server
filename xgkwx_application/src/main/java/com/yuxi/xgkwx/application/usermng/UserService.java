package com.yuxi.xgkwx.application.usermng;

import com.yuxi.xgkwx.application.usermng.dto.UserRegisterDto;
import com.yuxi.xgkwx.common.utils.SnowUtil;
import com.yuxi.xgkwx.domain.usermng.User;
import com.yuxi.xgkwx.domain.usermng.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userRepository;

    public long register(UserRegisterDto userDto) {
        User user = new User();
        user.setUserId(SnowUtil.getSnowflakeNextId());
        user.setUsername(userDto.getUsername());
        user.setUnifyId(userDto.getUnifyId());
        user.setPhone(userDto.getPhone());
        user.setNickname(userDto.getNickname());
        user.setRanking(userDto.getRanking());
        user.setCoin(10000);
        user.setInsertTime("1");
        user.setUpdateTime("1");
        return userRepository.registerNewUser(user);
    }
}
