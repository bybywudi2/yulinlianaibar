package com.by.controller;

import Utils.HttpRequest;
import Utils.WxConfig;
import com.alibaba.fastjson.JSONObject;
import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.constant.ActiveStatus;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @RequestMapping(value = "/userInfoRegist", method = RequestMethod.GET)
    public Map userInfoRegist(@Param("openid") String openid,@Param("realname") String realname,@Param("sex") String sex,
                              @Param("birthday") String birthday,@Param("height") String height,@Param("weight") String weight,
                              @Param("education") String education,@Param("compiled") String compiled,
                              @Param("work_of_father") String work_of_father,@Param("work_of_mom") String work_of_mom,
                              @Param("standard") String standard,@Param("phone") String phone,
                              @Param("is_live_location_yulin") String is_live_location_yulin,@Param("job") String job,
                              @Param("live_location") String live_location,
                              @Param("is_information_public") String is_information_public,@Param("hometown") String hometown,
                              @Param("userwx") String userwx,@Param("company") String company) {
        WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);

        User user = new User();
        user.setRealname(realname);
        user.setSex(Short.parseShort(sex));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthday_date = format.parse(birthday);  // Thu Jan 18 00:00:00 CST 2007
            user.setBirthday(birthday_date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("isLiveLocationYulin=================="+is_live_location_yulin);
        user.setHeight(Integer.parseInt(height));
        user.setWeight(Integer.parseInt(weight));
        user.setEducation(Integer.parseInt(education));
        user.setCompiled(Integer.parseInt(compiled));
        user.setWorkOfFather(work_of_father);
        user.setWorkOfMom(work_of_mom);
        user.setStandard(standard);
        user.setPhone(phone);
        user.setIsLiveLocationYulin(Short.valueOf(is_live_location_yulin));
        user.setJob(job);
        user.setLiveLocation(live_location);
        user.setIsInformationPublic(Short.valueOf(is_information_public));
        user.setHometown(hometown);
        user.setUserwx(userwx);
        user.setCompany(company);


        Long userId = wxUser.getUserId();
        if(userId == null){
            user.setCreateTime(new Date().getTime());
            wxUserLoginMapper.insertAndReturnPK(user);
            long uid = user.getId();
            //System.out.println("ididid====================="+uid);
            wxUser.setUserId(uid);
            wxUser.setActiveStatus(ActiveStatus.ONLY_WX_REGISTED);
            wxUserMapper.updateByPrimaryKeySelective(wxUser);
            //System.out.println("id====================="+user.getId());
            //            System.out.println("ididid====================="+ididid);
            //            //wxUser.setUserId(user.getId());
        }else{
            user.setId(userId);
            user.setModifyTime(new Date().getTime());
            userMapper.updateByPrimaryKey(user);
        }




        Map map = new HashMap();
        map.put("success","true");
        return map;

    }

}