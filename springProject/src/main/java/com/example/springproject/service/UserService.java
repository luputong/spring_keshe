package com.example.springproject.service;

import com.example.springproject.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updatePic(String picUrl);
}
