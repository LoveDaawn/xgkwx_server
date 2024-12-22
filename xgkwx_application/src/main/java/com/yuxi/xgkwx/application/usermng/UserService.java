package com.yuxi.xgkwx.application.usermng;

import com.yuxi.xgkwx.application.usermng.dto.req.*;
import com.yuxi.xgkwx.application.usermng.dto.res.UserInfoRespDto;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.CommonExceptionEnums;
import com.yuxi.xgkwx.common.utils.SnowUtil;
import com.yuxi.xgkwx.domain.usermng.User;
import com.yuxi.xgkwx.domain.usermng.mapper.UserMapper;

import cn.hutool.core.date.DateTime;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class UserService {
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
        } else {
            throw new CommonException(CommonExceptionEnums.USER_NOT_FOUND);
        }
        return userInfoRes;
    }

    // 修改用户信息
    public long updateUserInfo(UserUpdateReqDto reqDto) {
        User user = userRepository.getUserInfoById(reqDto.getUnifyId());

        if (user != null) {
            user.setNickname(reqDto.getNickname());
            user.setPhone(reqDto.getPhone());
            user.setEmail(reqDto.getEmail());
            return userRepository.updateUserInfo(user);
        } else {
            throw new CommonException(CommonExceptionEnums.USER_NOT_FOUND);
        }
    }

    // 修改用户密码
    public long changePassword(ChangePasswordReqDto reqDto){
        String unifyId = reqDto.getUnifyId();
        User user = userRepository.getUserInfoById(unifyId);

        if (user != null){
            String oldPassword = userRepository.getUserPasswordById(unifyId);
            if (oldPassword.equals(reqDto.getOldPassword())) {
                DateTime now = DateTime.now();
                String formattedTime = now.toString("yyyy-MM-dd HH:mm:ss");

                // 更新密码
                user.setUpdateTime(formattedTime);
                user.setPassword(reqDto.getNewPassword());

                return userRepository.updateUserPassword(user);
            } else {
                throw new CommonException(CommonExceptionEnums.PASSWORD_ERROR);
            }
        } else {
            throw new CommonException(CommonExceptionEnums.USER_NOT_FOUND);
        }
    }

    // 删除用户
    public long deleteUser(DeleteUserReqDto reqDto) {
        String unifyId = reqDto.getUnifyId();

        if (reqDto.getPassword().equals(userRepository.getUserPasswordById(unifyId))) {
            return userRepository.deleteUserById(unifyId);
        } else {
            throw new CommonException(CommonExceptionEnums.PASSWORD_ERROR);
        }
    }
}
