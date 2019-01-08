package com.by.dao;

import com.by.bean.User;
import com.by.bean.WxUser;
import com.by.po.MatchingStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MatchingMapper {
    List<WxUser> selectMatchedUsersByOpenid(MatchingStandard record);

    List<Long> selectMatchTimeOutUsers(long systemTime);

    int updateMatchStatus(@Param("ids")List idList);

    List<WxUser> selectMatchReadyMaleUsers();

    List<WxUser> selectMatchReadyFemaleUsers();
}