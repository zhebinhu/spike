package com.huzb.splike.service;

import com.huzb.splike.dao.UserDao;
import com.huzb.splike.domain.User;
import com.huzb.splike.execption.GlobalException;
import com.huzb.splike.redis.RedisService;
import com.huzb.splike.redis.UserKey;
import com.huzb.splike.result.CodeMsg;
import com.huzb.splike.util.MD5Util;
import com.huzb.splike.validator.UUIDUtil;
import com.huzb.splike.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Service
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    RedisService redisService;
    @Autowired
    UserDao userdao;

    public User getById(long id) {
        return userdao.getById(id);
    }


    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBpass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;
    }


    public User getByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return redisService.get(UserKey.token, token, User.class);
    }
}
