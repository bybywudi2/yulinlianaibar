package com.by.controller;

import Utils.CommonUtils;
import Utils.HttpRequest;
import Utils.WxConfig;
import com.alibaba.fastjson.JSONObject;
import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
微信用户打开小程序的逻辑：
根据用户的code获取openid
如果该用户未登录过，则将信息注册到wx_user表中，
如果已登录过，则直接返回用户信息并更新上次登录时间
 */
@Controller
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @ResponseBody
    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    public Map decodeUserInfo(@Param("code") String code,@Param("headurl") String headurl,@Param("nickname") String nickname,
                              @Param("sex") String sex,@Param("country") String country,@Param("province") String province,
                              @Param("city") String city) throws UnsupportedEncodingException {

        Map map = new HashMap();

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("success", "false");
            map.put("msg", "code不能为空");
            return map;
        }

        String params = "appid=" + WxConfig.APPID + "&secret=" + WxConfig.APPSECRECT + "&js_code=" + code + "&grant_type=" + WxConfig.GRANTTYPE;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);


        JSONObject json = JSONObject.parseObject(sr);

        //获取会话密钥（session_key）
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        if(openid.equals("") || openid == null || openid.length() == 0){
            map.put("success","false");
            map.put("msg","未获取到用户信息，请稍后重试");
            return map;
        }
        WxUser wu = wxUserLoginMapper.selectByOpenid(openid);
        if(wu != null){
            String base64Nickname = wu.getNickname();
            wu.setNickname(new String(Base64.decodeBase64(base64Nickname),"utf-8"));
            map.put("success","true");
            map.put("userinfo",wu);
            map.put("msg","已注册用户，登录成功");
            return map;
        }
        WxUser wxUser = new WxUser();
        wxUser.setOpenid(openid);
        wxUser.setActiveStatus(0);
        wxUser.setCity(city);
        wxUser.setCountry(country);
        wxUser.setHeadUrl(headurl);
        wxUser.setNickname(Base64.encodeBase64String(nickname.getBytes("utf-8")));
        wxUser.setProvince(province);
        wxUser.setSex(Short.parseShort("1"));
        wxUser.setCreateTime(new Date().getTime());
        wxUser.setLatestLoginTime(new Date().getTime());
        wxUserMapper.insert(wxUser);
        map.put("success","true");
        map.put("userinfo",wxUser);
        map.put("msg","未注册用户，已注册并登录成功");

        return map;

    }

}