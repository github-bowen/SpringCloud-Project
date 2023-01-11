package com.test.entity;

import lombok.Data;

@Data
public class User {
    String uid;
    String password;
    boolean is_admin;
}
