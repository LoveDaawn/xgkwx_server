package com.yuxi.xgkwx.adapter.driver.restful.usermng;

import com.yuxi.xgkwx.application.usermng.UserService;
import com.yuxi.xgkwx.application.usermng.dto.req.*;
import com.yuxi.xgkwx.application.usermng.dto.res.*;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.res.CommonResponse;
import com.yuxi.xgkwx.common.utils.ResponseUtil;

import com.alibaba.fastjson.JSONObject;
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
        try {
            log.info("UserController.register {} req: {}", userDto.getUnifyId(), JSONObject.toJSONString(userDto));
            long register = userService.register(userDto);
            res = ResponseUtil.responsSuccess(register);
        } catch (CommonException e) {
            log.error("UserController.register error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.register error: 服务异常", e); // Log the full stack trace
            res = ResponseUtil.responsError("500", "服务异常");
        } finally {
            log.info("UserController.register {} res: {}", userDto.getUnifyId(), JSONObject.toJSONString(res));
        }
        return res;
    }

    // 用户登录
    @PostMapping("/login")
    public CommonResponse<Boolean> login(@RequestBody @Valid UserLoginReqDto reqDto) {
        CommonResponse<Boolean> res = new CommonResponse<>();
        try {
            if (userService.checkPassword(reqDto.getUnifyId(), reqDto.getPassword())) {
                log.info("UserController.login req: {}", JSONObject.toJSONString(reqDto));
                res = ResponseUtil.responsSuccess(true);
            } else {
                log.info("Password wrong, please try again");
                res = ResponseUtil.responsError("200", "密码错误");
            }
        } catch (CommonException e) {
            log.error("UserController.login error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.login error: 服务异常", e);
            res = ResponseUtil.responsError("500", "服务异常");
        }
        return res;
    }

    // 获取用户信息，不包括敏感信息，密码等
    @GetMapping("/getUserInfo/{unifyId}")
    public CommonResponse<UserInfoRespDto> getUserInfo(@PathVariable String unifyId) {
        CommonResponse<UserInfoRespDto> res = new CommonResponse<>();
        try {
            log.info("UserController.getUserInfo unifyId: {}", unifyId);
            UserInfoRespDto user = userService.getUserInfo(unifyId);
            if (user != null) {
                res = ResponseUtil.responsSuccess(user);
            } else {
                log.info("User not found with unifyId: {}", unifyId);
                res = ResponseUtil.responsError("404", "用户不存在");
            }
        } catch (CommonException e) {
            log.error("UserController.getUserInfo error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.getUserInfo error: 服务异常", e);
            res = ResponseUtil.responsError("500", "服务异常");
        }
        return res;
    }

    // 更新用户的信息，包括nickname，phone，email
    @PutMapping("updateUserInfo")
    public CommonResponse<Boolean> updateUserInfo(@RequestBody @Valid UserUpdateReqDto reqDto) {
        CommonResponse<Boolean> res = new CommonResponse<>();
        try {
            log.info("UserController.updateUserInfo req: {}", JSONObject.toJSONString(reqDto));
            Boolean is_success = userService.updateUserInfo(reqDto);
            res = ResponseUtil.responsSuccess(is_success);
        } catch (CommonException e) {
            log.error("UserController.updateUserInfo error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.updateUserInfo error: 服务异常", e);
            res = ResponseUtil.responsError("500", "服务异常");
        }
        return res;
    }

    // 修改用户密码
    @PutMapping("/changePassword")
    public CommonResponse<Boolean> changePassword(@RequestBody @Valid ChangePasswordReqDto reqDto) {
        CommonResponse<Boolean> res = new CommonResponse<>();
        try {
            if (userService.checkPassword(reqDto.getUnifyId(), reqDto.getOldPassword()))  {
                log.info("UserController.changePassword req: {}", JSONObject.toJSONString(reqDto));
                Boolean is_success = userService.changePassword(reqDto);
                if (is_success) {
                    res = ResponseUtil.responsSuccess(is_success);
                } else {
                    String message = "No user with unifyId: " + reqDto.getUnifyId();
                    log.info(message);
                    res = ResponseUtil.responsError("200", message);
                }
            } else {
                log.info("Password wrong, please try again");
                res = ResponseUtil.responsError("200", "密码错误");
            }
        } catch (CommonException e) {
            log.error("UserController.changePassword error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.changePassword error: 服务异常", e);
            res = ResponseUtil.responsError("500", "服务异常");
        }
        return res;
    }

    // 用户注销
    @DeleteMapping("/delete")
    public CommonResponse<Boolean> deleteUser(@RequestBody @Valid DeleteUserReqDto reqDto) {
        CommonResponse<Boolean> res = new CommonResponse<>();
        try {
            String unifyId = reqDto.getUnifyId();
            if (userService.checkPassword(unifyId, reqDto.getPassword()))  {
                log.info("UserController.deleteUser userId: {}", unifyId);
                Boolean is_success = userService.deleteUser(unifyId);
                if (is_success) {
                    res = ResponseUtil.responsSuccess(is_success);
                } else {
                    String message = "No user with unifyId: " + reqDto.getUnifyId();
                    log.info(message);
                    res = ResponseUtil.responsError("200", message);
                }
            } else {
                log.info("Password wrong, please try again");
                res = ResponseUtil.responsError("200", "密码错误");
            }
        } catch (CommonException e) {
            log.error("UserController.deleteUser error, code:{}, msg:{}", e.getCode(), e.getMessage(), e);
            res = ResponseUtil.responsError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("UserController.deleteUser error: 服务异常", e);
            res = ResponseUtil.responsError("500", "服务异常");
        }
        return res;
    }
}
