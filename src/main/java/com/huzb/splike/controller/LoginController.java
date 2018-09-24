package com.huzb.splike.controller;

import com.huzb.splike.redis.RedisService;
import com.huzb.splike.result.CodeMsg;
import com.huzb.splike.result.Result;
import com.huzb.splike.service.UserService;
import com.huzb.splike.util.ValidatorUtil;
import com.huzb.splike.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result doLogin(HttpServletResponse response, @Validated LoginVo loginVo) {
        //登录
        userService.login(response, loginVo);
        return Result.success(true);
    }
}
