package com.example.sl.demospringboot.controller;

import com.example.sl.demospringboot.model.User;
import com.example.sl.demospringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAllUser(){
        List<User> userlist=userService.findAll();
        return userlist;
    }
}
