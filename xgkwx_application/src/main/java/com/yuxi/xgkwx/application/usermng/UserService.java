package com.yuxi.xgkwx.application.usermng;

import com.yuxi.xgkwx.application.usermng.dto.req.*;
import com.yuxi.xgkwx.application.usermng.dto.res.UserInfoRespDto;
import com.yuxi.xgkwx.common.utils.SnowUtil;
import com.yuxi.xgkwx.domain.usermng.User;
import com.yuxi.xgkwx.domain.usermng.mapper.UserMapper;

import cn.hutool.core.date.DateTime;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userRepository;

    // 注册用户
    public long register(UserRegisterReqDto userDto) {
        DateTime now = DateTime.now();
        String formattedTime = now.toString("yyyy-MM-dd HH:mm:ss");  // Use Hutool's built-in formatting

        User user = new User()
                .setUserId(SnowUtil.getSnowflakeNextId())
                .setUnifyId(userDto.getUnifyId())
                .setPhone(userDto.getPhone())
                .setNickname(userDto.getNickname())
                .setRanking(0)
                .setCoin(10000)
                .setInsertTime(formattedTime)
                .setUpdateTime(formattedTime);

        return userRepository.registerNewUser(user);
    }

    // 通过unifyId获取用户信息（对外使用）
    public UserInfoRespDto getUserInfo(String unifyId) {
        User user = userRepository.getUserInfoById(unifyId);

        UserInfoRespDto userInfoRes = new UserInfoRespDto();
        if (user != null) {
            userInfoRes.setId(user.getUserId())
                    .setUsername(user.getUsername())
                    .setEmail(user.getEmail())
                    .setPhone(user.getPhone())
                    .setNickname(user.getNickname())
                    .setRanking(user.getRanking())
                    .setCoin(user.getCoin())
                    .setVoucher(user.getVoucher());

            return userInfoRes;
        }
        return null;
    }

    // 通过unifyId获取用户密码（内部使用）
    public Boolean checkPassword(String unifyId, String password) {
        String storedPassword = userRepository.getUserPasswordById(unifyId);
        return storedPassword == null || storedPassword.equals(password);
    }

    // 修改用户信息
    public Boolean updateUserInfo(UserUpdateReqDto reqDto) {
        User user = userRepository.getUserInfoById(reqDto.getUnifyId());

        Boolean flag = false;
        if (user != null) {
            user.setNickname(reqDto.getNickname());
            user.setPhone(reqDto.getPhone());
            user.setEmail(reqDto.getEmail());

            int rowsAffected = userRepository.updateUserInfo(user);

            flag = (rowsAffected > 0);
        }

        return flag;
    }

    // 修改用户密码
    public Boolean changePassword(ChangePasswordReqDto reqDto){
        User user = userRepository.getUserInfoById(reqDto.getUnifyId());
        System.out.println(user);

        Boolean flag = false;
        if (user != null){
            DateTime now = DateTime.now();
            String formattedTime = now.toString("yyyy-MM-dd HH:mm:ss");

            user.setUpdateTime(formattedTime);
            user.setPassword(reqDto.getNewPassword());

            int rowsAffected = userRepository.updateUserPassword(user);
            flag = (rowsAffected > 0);
        }

        return flag;
    }

    // 删除用户
    public Boolean deleteUser(String unifyId) {
        int rowsAffected = userRepository.deleteUserById(unifyId);

        return (rowsAffected > 0);
    }
}
