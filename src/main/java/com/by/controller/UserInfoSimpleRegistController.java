package com.by.controller;

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
public class UserInfoSimpleRegistController {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @RequestMapping(value = "/userInfoSimpleRegist", method = RequestMethod.GET)
    public Map userInfoRegist(@Param("openid") String openid,@Param("sex") String sex,
                              @Param("birthday") String birthday,@Param("height") String height,@Param("weight") String weight,
                              @Param("standard") String standard,@Param("phone") String phone,
                              @Param("job") String job, @Param("hometown") String hometown,
                              @Param("userwx") String userwx) {
        WxUser wxUser = wxUserLoginMapper.selectByOpenid(openid);

        User user = new User();
        user.setSex(Short.parseShort(sex));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthday_date = format.parse(birthday);  // Thu Jan 18 00:00:00 CST 2007
            user.setBirthday(birthday_date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(height != null){
            user.setHeight(Integer.parseInt(height));
        }
        if(weight != null){
            user.setWeight(Integer.parseInt(weight));
        }
        if(job != null){
            user.setJob(job);
        }
        user.setStandard(standard);
        user.setPhone(phone);
        user.setHometown(hometown);
        user.setUserwx(userwx);

        Long userId = wxUser.getUserId();
        if(userId == null){
            user.setCreateTime(new Date().getTime());
            wxUserLoginMapper.insertAndReturnPK(user);
            long uid = user.getId();
            wxUser.setUserId(uid);
            wxUser.setActiveStatus(ActiveStatus.INFO_REGISTED_BUT_NOT_REAL);
            wxUserMapper.updateByPrimaryKeySelective(wxUser);
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