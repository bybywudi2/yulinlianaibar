package com.by.dao;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.po.MatchingStandard;

import java.util.List;

public interface MatchingMapper {
    List<WxUser> selectMatchedUsersByOpenid(MatchingStandard record);
}