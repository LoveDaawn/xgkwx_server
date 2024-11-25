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
        User user = new User()
                .setUserId(SnowUtil.getSnowflakeNextId())
                .setUnifyId(userDto.getUnifyId())
                .setPhone(userDto.getPhone())
                .setNickname(userDto.getNickname())
                .setRanking(0)
                .setCoin(10000)
                .setInsertTime(now.toString(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)))
                .setUpdateTime(now.toString(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)));
        return userRepository.registerNewUser(user);
    }
}
