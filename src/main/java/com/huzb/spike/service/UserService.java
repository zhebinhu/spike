package com.huzb.spike.service;

import com.huzb.spike.dao.UserDao;
import com.huzb.spike.domain.User;
import com.huzb.spike.execption.GlobalException;
import com.huzb.spike.redis.RedisService;
import com.huzb.spike.redis.UserKey;
import com.huzb.spike.result.CodeMsg;
import com.huzb.spike.util.MD5Util;
import com.huzb.spike.validator.UUIDUtil;
import com.huzb.spike.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Service
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    RedisService redisService;
    @Autowired
    UserDao userdao;

    public User getById(long id) {
        User user = redisService.get(UserKey.user, "" + id, User.class);
        if (user != null) {
            return user;
        }
        user = userdao.getById(id);
        if (user != null) {
            redisService.set(UserKey.user, "" + id, user);
        }
        return user;
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
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, user, token);
        return true;
    }


    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期
        if (user != null) {
            addCookie(response, user, token);
        }
        return user;
    }

    public Boolean updatePassword(String token, long id, String password) {
        User user = userdao.getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        User newUser = new User();
        newUser.setId(id);
        newUser.setPassword(MD5Util.formPassToDBPass(password, user.getSalt()));
        userdao.updatePassword(newUser);
        redisService.delete(UserKey.user, "" + id);
        user.setPassword(newUser.getPassword());
        redisService.set(UserKey.token, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, User user, String token) {
        //生成cookie
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
