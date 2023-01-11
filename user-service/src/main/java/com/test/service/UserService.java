package com.test.service;

import com.test.entity.User;

public interface UserService {
    User getUserById(String uid);
    void register(String uid, String pwd);
}
