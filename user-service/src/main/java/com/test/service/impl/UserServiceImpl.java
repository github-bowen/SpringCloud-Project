package com.test.service.impl;

import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper mapper;

    @Override
    public User getUserById(String uid) {
        return mapper.getUserById(uid);
    }

    @Override
    public void modifyUserInfo(String uid, String pwd) {
        mapper.modifyUserInfo(uid, pwd);
    }

    @Override
    public List<User> allUsers() {
        return mapper.allUsers();
    }

    @Override
    public void register(String uid, String pwd) {
        mapper.register(uid, pwd);
    }
}
