package com.example.springproject.service.Impl;

import com.example.springproject.mapper.UserMapper;
import com.example.springproject.pojo.Result;
import com.example.springproject.pojo.User;
import com.example.springproject.service.UserService;
import com.example.springproject.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }


    @Override
    public void register(String username, String password) {
        userMapper.addUser(username, password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        HashMap map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        userMapper.updateUsername(user.getUsername(),username);
    }

    @Override
    public void updatePic(String picUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePic(picUrl,id);
    }


}
