package com.huzb.splike.controller;

import com.huzb.splike.domain.User;
import com.huzb.splike.redis.RedisService;
import com.huzb.splike.result.Result;
import com.huzb.splike.service.UserService;
import com.huzb.splike.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/22
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
    public String toLogin(@CookieValue(value = UserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                          @RequestParam(value = UserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {
        if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        User user = userService.getByToken(token);
        System.out.println(user);
        return "goods_list";
    }

}
