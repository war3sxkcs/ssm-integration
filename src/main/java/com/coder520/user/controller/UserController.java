package com.coder520.user.controller;

import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/13 12:59
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/lock")
    public String lock() {
        return "lockscreen";
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/21 21:37
     * Description  获取用户信息
     */
    @RequestMapping("/userinfo")
    @ResponseBody
    public User getUser(HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        return user;
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/21 23:41
     * Description  登出方法;自动跳转到登陆页面
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
