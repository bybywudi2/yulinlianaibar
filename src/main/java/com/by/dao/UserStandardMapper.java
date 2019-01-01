package com.by.dao;

import com.by.bean.UserStandard;

public interface UserStandardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStandard record);

    int insertSelective(UserStandard record);

    UserStandard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStandard record);

    int updateByPrimaryKey(UserStandard record);
}