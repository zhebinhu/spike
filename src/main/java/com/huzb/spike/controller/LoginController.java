package com.huzb.spike.controller;

import com.huzb.spike.rabbitmq.MQSender;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.result.Result;
import com.huzb.spike.service.UserService;
import com.huzb.spike.vo.LoginVo;
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

    @Autowired
    MQSender mqSender;

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

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
        mqSender.send("hello,uestc");
        return Result.success("hello,uestc");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
        mqSender.sendTopic("hello,uestc1");
        return Result.success("hello,uestc1");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
        mqSender.sendFanout("hello,uestc2");
        return Result.success("hello,uestc2");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeaders("hello,uestc3");
        return Result.success("hello,uestc3");
    }
}
