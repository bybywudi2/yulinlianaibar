package com.by.controller;

import Utils.CommonUtils;
import Utils.WxConfig;
import com.alibaba.fastjson.JSONObject;
import com.by.bean.User;
import com.by.dao.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/mvc")
public class getOpenIdController {
    /*@Autowired
    private UserMapper userMapper;


    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET) // 获取用户信息
    public @ResponseBody User getOpenId(@Param("code") String code) {
        User u = new User();
        u.setPhotoSelf(code);
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
            if (code.equals("")) {
                System.out.println("code为空");
            } else {
                String requestUrl = WX_URL.replace("APPID", WxConfig.APPID).replace("SECRET", WxConfig.APPSECRECT)
                        .replace("JSCODE", code).replace("authorization_code", WxConfig.GRANTTYPE);
                JSONObject jsonObject = CommonUtils.httpsRequest(requestUrl, "GET", null);

                if (jsonObject != null) {
                    try {
                        // 业务操作
                        String openid = jsonObject.getString("openid");
                        //wechatService.selectUserByOpenId(openid, headurl, nickname, sex, country, province, city);
                        u.setRealname(openid);
                        return u;
                    } catch (Exception e) {
                        System.out.println("业务操作失败");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("code无效");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        u.setPhotoSelf("2");
        u.setCompany("error~~~~~~~~");
        return u;
    }
*/
}