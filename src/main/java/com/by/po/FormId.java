package com.by.po;

import com.by.bean.UserStandard;

import java.util.List;

public class FormId {
    private String openid;
    private List formIds;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public List getFormIds() {
        return formIds;
    }

    public void setFormIds(List formIds) {
        this.formIds = formIds;
    }
}