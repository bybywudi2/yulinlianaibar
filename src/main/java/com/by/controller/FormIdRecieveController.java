package com.by.controller;

import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.po.FormId;
import com.by.redis.JedisUtil;
import com.by.redis.SerializeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/matching")
public class FormIdRecieveController {
    @RequestMapping(value = "/formIdRecieve",method = RequestMethod.POST)
    public @ResponseBody void match(@RequestBody FormId req){
        List list = req.getFormIds();
        String key = req.getOpenid() + "formId";
        byte[] byte_key = SerializeUtil.serialize(key);
        Jedis jedis = JedisUtil.getJedis();
        long num = 0;
        for(int i=0;i<list.size();i++){
            num = jedis.llen(byte_key);
            if(num > 10){
                jedis.rpop(byte_key);
            }
            jedis.lpush(byte_key,SerializeUtil.serialize(list.get(i)));
        }

    }

}