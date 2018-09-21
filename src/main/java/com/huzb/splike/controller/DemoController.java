package com.huzb.splike.controller;

import com.huzb.splike.domain.User;
import com.huzb.splike.redis.RedisService;
import com.huzb.splike.result.Result;
import com.huzb.splike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/8
 */
@Controller
public class DemoController {

 @Autowired
 UserService userService;
 @Autowired
 RedisService redisService;

 @RequestMapping("/db/get")
 @ResponseBody
 public Result<User> dbGet(){

  User user = userService.getById(1);

  return Result.success(user);

 }

 @RequestMapping("/db/tx")
 @ResponseBody
 public Result<Boolean> tx(){
   return Result.success(userService.tx());
 }

 @RequestMapping("/redis/get")
 @ResponseBody
 public Result<User> redisGet(){

  redisService.get(String,Class<T> clazz);

  return Result.success(user);

 }
}
