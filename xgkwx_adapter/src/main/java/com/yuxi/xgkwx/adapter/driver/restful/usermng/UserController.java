package com.yuxi.xgkwx.adapter.driver.restful.usermng;

import com.yuxi.xgkwx.application.usermng.UserService;
import com.yuxi.xgkwx.application.usermng.dto.req.*;
import com.yuxi.xgkwx.application.usermng.dto.res.*;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.res.CommonResponse;
import com.yuxi.xgkwx.common.utils.ResponseUtil;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ThrowableUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public CommonResponse<Long> register(@RequestBody @Valid UserRegisterReqDto userDto) {
        CommonResponse<Long> res = new CommonResponse<>();
        log.info("UserController.register {} req: {}", userDto.getUnifyId(), JSONObject.toJSONString(userDto));

        try {
            long register_num = userService.register(userDto);
            res = ResponseUtil.responseSuccess(register_num);
        } catch (CommonException e) {
            log.error("UserController.register error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responseBusinessError(e);
        } catch (Throwable e) {
            log.error("UserController.register Error: 服务异常 ", e); // Log the full stack trace
            res = ResponseUtil.responseError();
        } finally {
            log.info("UserController.register {} res: {}", userDto.getUnifyId(), JSONObject.toJSONString(res));
        }
        return res;
    }

    // 获取用户信息，不包括敏感信息，密码等
    @GetMapping("/getUserInfo/{unifyId}")
    public CommonResponse<UserInfoRespDto> getUserInfo(@PathVariable String unifyId) {
        CommonResponse<UserInfoRespDto> res = new CommonResponse<>();
        log.info("UserController.getUserInfo unifyId: {}", unifyId);

        try {
            UserInfoRespDto user_info = userService.getUserInfo(unifyId);
            res = ResponseUtil.responseSuccess(user_info);
        } catch (CommonException e) {
            log.error("UserController.getUserInfo error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responseBusinessError(e);
        } catch (Throwable e) {
            log.error("UserController.getUserInfo error: 服务异常 ", e);
            res = ResponseUtil.responseError();
        } finally {
            log.info("UserController.getUserInfo res: {}", JSONObject.toJSONString(res));
        }
        return res;
    }

    // 更新用户的信息，包括nickname，phone，email
    @PutMapping("updateUserInfo")
    public CommonResponse<Long> updateUserInfo(@RequestBody @Valid UserUpdateReqDto reqDto) {
        CommonResponse<Long> res = new CommonResponse<>();
        log.info("UserController.updateUserInfo req: {}", JSONObject.toJSONString(reqDto));

        try {
            Long update_num = userService.updateUserInfo(reqDto);
            res = ResponseUtil.responseSuccess(update_num);
        } catch (CommonException e) {
            log.error("UserController.updateUserInfo error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responseBusinessError(e);
        } catch (Throwable e) {
            log.error("UserController.updateUserInfo error: 服务异常", e);
            res = ResponseUtil.responseError();
        } finally {
            log.info("UserController.updateUserInfo res: {}", JSONObject.toJSONString(res));
        }
        return res;
    }

    // 修改用户密码
    @PutMapping("/changePassword")
    public CommonResponse<Long> changePassword(@RequestBody @Valid ChangePasswordReqDto reqDto) {
        CommonResponse<Long> res = new CommonResponse<>();
        log.info("UserController.changePassword req: {}", JSONObject.toJSONString(reqDto));

        try {
            Long change_num = userService.changePassword(reqDto);
            res = ResponseUtil.responseSuccess(change_num);
        } catch (CommonException e) {
            log.error("UserController.changePassword error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responseBusinessError(e);
        } catch (Throwable e) {
            log.error("UserController.changePassword error: 服务异常", e);
            res = ResponseUtil.responseError();
        } finally {
            log.info("UserController.changePassword res: {}", JSONObject.toJSONString(res));
        }
        return res;
    }

    // 用户注销
    @DeleteMapping("/delete")
    public CommonResponse<Long> deleteUser(@RequestBody @Valid DeleteUserReqDto reqDto) {
        CommonResponse<Long> res = new CommonResponse<>();
        log.info("UserController.delete req: {}", JSONObject.toJSONString(reqDto));

        try {
            Long delete_num = userService.deleteUser(reqDto);
            res = ResponseUtil.responseSuccess(delete_num);
        } catch (CommonException e) {
            log.error("UserController.deleteUser error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responseBusinessError(e);
        } catch (Throwable e) {
            log.error("UserController.deleteUser error: 服务异常", e);
            res = ResponseUtil.responseError();
        }finally {
            log.info("UserController.deleteUser res: {}", JSONObject.toJSONString(res));
        }
        return res;
    }
}
