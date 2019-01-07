package com.by.po;

import com.by.bean.UserStandard;

public class MatchingStandard {
    private UserStandard userStandard;
    private String openid;
    private Short sex;

    public UserStandard getUserStandard() {
        return userStandard;
    }

    public void setUserStandard(UserStandard userStandard) {
        this.userStandard = userStandard;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }
}