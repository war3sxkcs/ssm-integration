package com.coder520.user.service;

import com.coder520.user.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/13 13:02
 */
public interface UserService {
    User findUserByUserName(String username);

    void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
