package com.by.cronjobs;

import Utils.HttpRequest;
import Utils.WxConfig;
import com.alibaba.fastjson.JSONObject;
import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class RefreshAccess {
    public void doRefresh(){
        String params = "appid=" + WxConfig.APPID + "&secret=" + WxConfig.APPSECRECT +  "&grant_type=" + WxConfig.ACEESTYPE;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);


        JSONObject json = JSONObject.parseObject(sr);
        String key = "appAccess";
        JedisUtil.getJedis().set(key,json.getString("access_token"));
    }

}