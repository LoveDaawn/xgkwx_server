package com.yuxi.xgkwx.application.usermng;

import cn.hutool.core.date.DateTime;
import com.yuxi.xgkwx.application.usermng.dto.UserRegisterReqDto;
import com.yuxi.xgkwx.common.utils.SnowUtil;
import com.yuxi.xgkwx.domain.usermng.User;
import com.yuxi.xgkwx.domain.usermng.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userRepository;

    public long register(UserRegisterReqDto userDto) {
        DateTime now = DateTime.now();
        User user = new User();
        user.setUserId(SnowUtil.getSnowflakeNextId());
        user.setUnifyId(userDto.getUnifyId());
        user.setPhone(userDto.getPhone());
        user.setNickname(userDto.getNickname());
        user.setRanking(0);
        user.setCoin(10000);
        user.setInsertTime(now.toString(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)));
        user.setUpdateTime(now.toString(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)));
        return userRepository.registerNewUser(user);
    }
}
