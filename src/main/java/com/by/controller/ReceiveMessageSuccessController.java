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
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class ReceiveMessageSuccessController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;

    @RequestMapping("/receiveMessageSuccess")
    public @ResponseBody Map hello(@Param("openid") String openid, @Param("target") String target){

        if(target == null){
            WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);
            long targetId = wxUser.getMatchingUserId();
            if(targetId != 0){
                WxUser targetUser = wxUserMapper.selectByPrimaryKey(targetId);
                target = targetUser.getOpenid();
            }
        }
        Map res = new HashMap();
        String key = target + "->" + openid;
        byte[] byte_key = SerializeUtil.serialize(key);
        try {
            Jedis jedis = JedisUtil.getJedis();
            jedis.del(byte_key);
            res.put("success","true");
            return res;
        }catch (Exception e){
            res.put("success","false");
            res.put("msg","出现异常");
            return res;
        }

    }
}