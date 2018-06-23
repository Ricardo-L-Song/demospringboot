package com.example.sl.demospringboot.service;

import com.example.sl.demospringboot.dao.UserMapper;
import com.example.sl.demospringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
