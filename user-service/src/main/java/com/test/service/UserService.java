package com.test.service;

import com.test.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(String uid);
    void register(String uid, String pwd);
    void modifyUserInfo(String uid, String pwd);

    List<User> allUsers();
}
