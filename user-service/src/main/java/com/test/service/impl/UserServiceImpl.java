package com.test.service.impl;

import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper mapper;

    @Override
    public User getUserById(String uid) {
        return mapper.getUserById(uid);
    }

    @Override
    public void register(String uid, String pwd) {
        mapper.register(uid, pwd);
    }
}
