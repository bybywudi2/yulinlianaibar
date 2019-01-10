package com.by.controller;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.redis.JedisUtil;
import com.by.redis.SerializeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chat")
public class SendMessageController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;

    @RequestMapping("/sendMessage")
    public @ResponseBody Map hello(@Param("openid") String openid, @Param("content") String content){
        Map res = new HashMap();
        WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);
        long target = wxUser.getMatchingUserId();
        if(target != 0){
            WxUser targetUser = wxUserMapper.selectByPrimaryKey(target);
            String targetOpenId = targetUser.getOpenid();
            String key = openid + "->" + targetOpenId;
            Map map = new HashMap();
            map.put("content",content);
            map.put("time",new Date().getTime());
            JedisUtil.getJedis().lpush(SerializeUtil.serialize(key), SerializeUtil.serialize(map));
            res.put("success","true");
            return res;
        }
        res.put("success","false");
        res.put("msg","找不到发送消息的目标");
        return res;
    }
}