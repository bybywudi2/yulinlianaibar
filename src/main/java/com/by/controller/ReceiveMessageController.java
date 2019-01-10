package com.by.controller;

import com.by.bean.WxUser;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.redis.JedisUtil;
import com.by.redis.SerializeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ReceiveMessageController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;

    @RequestMapping("/receiveMessage")
    public @ResponseBody Map hello(@Param("openid") String openid, @Param("target") String target){
        Map res = new HashMap();
        String key = target + "->" + openid;
        try {
            Map message = (HashMap) SerializeUtil.unserialize(JedisUtil.getJedis().rpop(
                    SerializeUtil.serialize(key)));
            if(message != null){
                return message;
            }else{
                res.put("success","false");
                res.put("msg","无消息");
                return res;
            }

        }catch (Exception e){
            res.put("success","false");
            res.put("msg","出现异常");
            return res;
        }


    }
}