package com.yuxi.xgkwx.adapter.driver.restful.usermng;

import com.alibaba.fastjson.JSONObject;
import com.yuxi.xgkwx.application.usermng.dto.UserRegisterReqDto;
import com.yuxi.xgkwx.application.usermng.UserService;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.res.CommonResponse;
import com.yuxi.xgkwx.common.utils.ResponseUtil;
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

    @PostMapping("/register")
    public CommonResponse<Long> register(@RequestBody @Valid UserRegisterReqDto userDto) {
        CommonResponse<Long> res = new CommonResponse<>();
        try {
            log.info("UserController.register {} req: {}", userDto.getUnifyId(),  JSONObject.toJSONString(userDto));
            long register = userService.register(userDto);
            res = ResponseUtil.responsSuccess(register);
        } catch (CommonException e) {
            log.error("UserController.register error, code:{}, msg:{}", e.getCode(), e.getMessage());
        } catch (Throwable e) {
            log.error("UserController.register error：服务异常");
        } finally {
            log.info("UserController.register {} res: {}", userDto.getUnifyId(), JSONObject.toJSONString(res));
        }
        return res;
    }
}
