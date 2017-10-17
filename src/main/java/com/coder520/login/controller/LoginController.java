package com.coder520.login.controller;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by   song 醉美柳舞之众星捧月 Date & Time  2017/9/19 15:46
 */
@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/20 0:36
     * Description 登陆页面
     */
    @RequestMapping
    private String login() {
        return "login";
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/20 0:37
     * Description 校验登陆
     */
    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        // 查数据库 如果查成功 调用MD5加密对比密码
        User user = userService.findUserByUserName(username);
        if (user != null) {
            if (MD5Utils.checkPassword(pwd, user.getPassword())) {
                //验证成功 ， 就可以设置session
                request.getSession().setAttribute("userinfo", user);
                return "login_succ";
            } else {
                //校验失败，返回校验失败signal
                return "login_fail";
            }
        } else {
            //校验失败，返回校验失败signal
            return "login_fail";
        }
        //用户信息存session 返回成功signal
        //如果校验失败, 页面提示信息 返回失败signal
    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.createUser(user);
        return "succ";
    }
}