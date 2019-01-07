package com.by.cronjobs;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.dao.MatchingMapper;
import com.by.dao.UserMapper;
import com.by.dao.WxUserLoginMapper;
import com.by.dao.WxUserMapper;
import com.by.po.MatchingStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchTimeOut {
    @Autowired
    private WxUserLoginMapper wxUserLoginMapper;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private MatchingMapper matchingMapper;
    @Autowired
    private UserMapper userMapper;

    public void cancelMatch(){
        List<Long> list = matchingMapper.selectMatchTimeOutUsers(new Date().getTime());

        if(list.size() != 0){
            for(int i=0;i<list.size();i++){
                System.out.println("id==============="+list.get(i));
            }
            matchingMapper.updateMatchStatus(list);
        }else{
            System.out.println("nononono");
        }

    }
}