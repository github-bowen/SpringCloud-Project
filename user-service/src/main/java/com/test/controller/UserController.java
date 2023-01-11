package com.test.controller;


import com.alibaba.fastjson.JSONObject;
import com.test.entity.User;
import com.test.service.UserService;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Resource
    UserService service;

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/user/{uid}")
    public User findUserById(@PathVariable("uid") String uid) {
        return service.getUserById(uid);
    }

    @PostMapping("/login")
    @CrossOrigin
    public Map<String, Object> login(@RequestBody String reqBody) {
        System.out.println(reqBody);
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        Map<String, Object> response = new HashMap<>();
        String uid = jsonObject.getString("userId");
        String pwd = jsonObject.getString("password");
        User curUser = service.getUserById(uid);
        System.out.println(uid + " " + pwd);
        if (curUser != null && curUser.getPassword().equals(pwd)) {
            response.put("success", true);
            Map<String, Object> data = new HashMap<>();
            data.put("token", uid);
            data.put("userId", uid);
            data.put("isAdmin", curUser.is_admin());
            response.put("data", data);
        } else {
            response.put("success", false);
        }
        return response;
    }

    @PostMapping("/register")
    @CrossOrigin
    public Map<String, Object> register(@RequestBody String reqBody) {
        System.out.println(reqBody);
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        Map<String, Object> response = new HashMap<>();
        String uid = jsonObject.getString("userId");
        String pwd = jsonObject.getString("password");
        User curUser = service.getUserById(uid);
        System.out.println(curUser);
        if (curUser == null) {
            response.put("success", true);
            service.register(uid, pwd);
        } else {
            response.put("success", false);
        }
        System.out.println(response);
        return response;
    }

    @PostMapping("/modifyUserInfo")
    public Map<String, Object> modifyUserInfo(@RequestBody String reqBody) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(reqBody);
        System.out.println(request.getHeader("Authorization"));
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        String token = request.getHeader("Authorization");
        String prePwd = jsonObject.getString("prePassword");
        String pwd = jsonObject.getString("password");
        User curUser = service.getUserById(token);
        if (curUser != null && curUser.getPassword().equals(prePwd)) {
            service.modifyUserInfo(curUser.getUid(), pwd);
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }

}
