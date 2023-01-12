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
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    UserService service;

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/all-users")
    public List<User> allUsers() {
        return service.allUsers();
    }

    // 新增，从 TicketController 中调用，获取当前登录用户的信息
    @GetMapping("/current-user")
    public User currentUser() {
        String userId = request.getHeader("Authorization");
        return service.getUserById(userId);
    }

    @RequestMapping("/user/{uid}")
    public User findUserById(@PathVariable("uid") String uid) {
        return service.getUserById(uid);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody String reqBody) {
        System.out.println(reqBody);
        JSONObject jsonObject = JSONObject.parseObject(reqBody);
        Map<String, Object> response = new HashMap<>();
        String uid = jsonObject.getString("userId");
        String pwd = jsonObject.getString("password");
        User curUser = service.getUserById(uid);
        if (curUser != null && curUser.getPassword().equals(pwd)) {
            response.put("success", true);
            Map<String, Object> data = new HashMap<>();
            data.put("token", uid);
            data.put("userId", uid);
            data.put("isAdmin", curUser.is_admin());
            response.put("data", data);
            System.out.println(response);
        } else {
            response.put("success", false);
        }
        return response;
    }

    @PostMapping("/register")
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
