package com.yuxi.xgkwx.adapter.driver.restful.usermng;

import com.yuxi.xgkwx.application.usermng.dto.UserRegisterDto;
import com.yuxi.xgkwx.application.usermng.UserService;
import com.yuxi.xgkwx.common.res.CommonResponse;
import com.yuxi.xgkwx.common.utils.ResponseUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public CommonResponse<Long> register(@RequestBody /*@Valid*/ UserRegisterDto userDto) {
        log.info("message in...");
        long register = userService.register(userDto);
        return ResponseUtil.responsSuccess(register);
    }
}
