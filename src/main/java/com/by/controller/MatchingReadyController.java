package com.by.controller;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.po.MatchingStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/matching")
public class MatchingReadyController {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private MatchingMapper matchingMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/ready/{openid}")
    public @ResponseBody
    Map match(@PathVariable String openid){
        Map map = new HashMap();
        WxUser thisUser = wxUserLoginMapper.selectByOpenid(openid);

        if(thisUser.getMatchingUserId() != 0){
            map.put("success","false");
            map.put("msg","今天已有匹配对象");
            return map;
        }
        User thisUser_info = userMapper.selectByPrimaryKey(thisUser.getUserId());

        WxUser wxUser = new WxUser();
        wxUser.setId(thisUser.getId());
        wxUser.setIsReady(new Short("1"));
        wxUserMapper.updateByPrimaryKeySelective(wxUser);

        MatchingStandard matchingStandard = new MatchingStandard();
        matchingStandard.setOpenid(openid);
        if(thisUser_info.getSex().equals(new Short("0"))){
            matchingStandard.setSex(new Short("1"));
        }else{
            matchingStandard.setSex(new Short("0"));
        }
        List<WxUser> list = matchingMapper.selectMatchedUsersByOpenid(matchingStandard);

        if(list != null){
            int rand = (int)(0+Math.random()*(list.size()));
            WxUser matchedUser = list.get(rand);
            matchedUser.setMatchingUserId(thisUser.getId());
            thisUser.setMatchingUserId(matchedUser.getId());

            wxUserMapper.updateByPrimaryKeySelective(thisUser);
            wxUserMapper.updateByPrimaryKeySelective(matchedUser);

            map.put("success","true");
            map.put("matchingUser",matchedUser);
        }else{
            map.put("success","false");
            map.put("msg","暂无匹配对象");
        }

        return map;
    }
}