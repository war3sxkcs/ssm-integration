package com.coder520.user.service;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.dao.UserMapper;
import com.coder520.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/13 13:12
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/20 0:40
     * Description 根据用户名查询用户
     */
    @Override
    public User findUserByUserName(String username) {
        User user = userMapper.selectByUserName(username);
        return user;
    }

    @Override
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));
        userMapper.insertSelective(user);
    }
}
