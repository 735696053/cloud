package com.cloud.user.controller;

import com.cloud.user.Enum.ResponseEnum;
import com.cloud.user.exception.BusinessException;
import com.cloud.user.pojo.User;
import com.cloud.user.service.UserService;
import com.cloud.user.utils.JwtHelper;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/isLogin")
    public String isLogin() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        System.out.println("index");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }

    @RequestMapping("/auth")
    @HystrixCommand(fallbackMethod = "authFallback")
    public String Login(HttpServletResponse response, String username, String password) throws BusinessException {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return "login";
        }
        User user = userService.authUser(username);
        if (password.equals(user.getPassword())) {
            String token = JwtHelper.getToken(user);
            user.setToken(token);
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            return "redirect:/user/index";
        }
        return "login";
    }

    @RequestMapping("/inToken/{token}")
    @ResponseBody
    public User authToken(@PathVariable String token) throws BusinessException {
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(ResponseEnum.USER_ERROR_TOKEN.getCode(), ResponseEnum.USER_ERROR_TOKEN.getMessage());
        }
        String result = JwtHelper.verifyToken(token); //校验tiken是否有效
        User user = new Gson().fromJson(result, User.class);
        user.setToken(token);
        if (user != null)
            return user;
        throw new BusinessException(ResponseEnum.USER_ERROR_TOKEN.getCode(), ResponseEnum.USER_ERROR_TOKEN.getMessage());
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) throws BusinessException {
        Cookie cookie = new Cookie("token", "");
        response.addCookie(cookie);
        return "redirect:/user/index";
    }

    public String authFallback(HttpServletResponse response, String username, String password) throws BusinessException {
        throw new BusinessException(ResponseEnum.USER_ERROR_USERNAME.getCode(), ResponseEnum.USER_ERROR_USERNAME.getMessage());
    }


}
