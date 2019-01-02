package com.by.dao;

import com.by.bean.User;
import com.by.bean.WxUser;

public interface WxUserLoginMapper {
    WxUser selectByOpenid(String openid);

    int insertAndReturnPK(User record);
}