package com.by.controller;

import com.by.bean.User;
import com.by.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/mvc")
public class testController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/hello/{id}")
    public @ResponseBody User hello(@PathVariable long id, @RequestParam(value="age",required=false,defaultValue="24") String age){

        User u = userMapper.selectByPrimaryKey(id);

        u.setId(id);
        u.setBirthday(new Date().getTime());
        u.setCompany("123");
        u.setCompiled(1);
        u.setCreateTime(new Date().getTime());
        u.setPhotoSelf(age);
        return u;
    }
}