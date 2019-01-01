package com.by.controller;

import Utils.HttpRequest;
import Utils.WxConfig;
import com.alibaba.fastjson.JSONObject;
import com.by.bean.WxUser;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
微信用户注册小程序的逻辑：
如果微信用户登录过小程序，并想要与其他用户交换信息，则弹出注册页面，只有注册过真实信息的用户才可以使用
 */
@Controller
@RequestMapping("/regist")
public class UserInfoRegistController {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @ResponseBody
    @RequestMapping(value = "/userInfoRegist", method = RequestMethod.POST)
    public Map userInfoRegist(@Param("openid") String code,@Param("realname") String realname,@Param("sex") String sex,
                              @Param("birthday") Date birthday,@Param("height") String height,@Param("weight") String weight,
                              @Param("education") String education,@Param("compiled") String compiled,
                              @Param("work_of_father") String work_of_father,@Param("work_of_mom") String work_of_mom,
                              @Param("standard") String standard,@Param("phone") String phone,
                              @Param("is_live_location_yulin") String isLiveLocationYulin,@Param("job") String job,
                              @Param("live_location") String live_location,
                              @Param("is_information_public") String isInformationPublic,@Param("hometown") String hometown) {


        Map map = new HashMap();
        return map;
    }

}