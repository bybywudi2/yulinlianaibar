package com.by.controller;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.po.MatchingStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/regist")
public class HasUserRegistController {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;

    @RequestMapping("/getUserStatus/{openid}")
    public @ResponseBody
    Map hasRegist(@PathVariable String openid){
        Map map = new HashMap();
        WxUser thisUser = wxUserLoginMapper.selectByOpenid(openid);

        if(thisUser.getUserId() != null){
            map.put("hasRegist","1");
        }else{
            map.put("hasRegist","0");
        }

        if(thisUser.getIsReady().equals(new Short("1"))){
            map.put("isReady","1");
        }else{
            map.put("isReady","0");
        }

        if(thisUser.getMatchingUserId().equals(new Long("0"))){
            map.put("isMatching","0");
        }else{
            map.put("isMatching","1");
        }

        map.put("success","true");
        return map;
    }
}