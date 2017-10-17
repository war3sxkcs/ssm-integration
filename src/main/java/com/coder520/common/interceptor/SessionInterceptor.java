package com.coder520.common.interceptor;

import com.coder520.user.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/20 23:15
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        if ((uri.indexOf("login") >= 0) || (uri.indexOf("sign") >= 0) || (uri.indexOf("reAttend") >= 0)) {
            return true;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userinfo");
        if (user != null) {
            return true;
        }
        // 转发页面，没有session就转发到登陆界面
        request.getRequestDispatcher("/login").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}