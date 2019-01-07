package com.by.bean;

public class WxUser {
    private Long id;

    private String openid;

    private String headUrl;

    private String nickname;

    private Short sex;

    private String country;

    private String province;

    private String city;

    private Long createTime;

    private Long latestLoginTime;

    private Long userId;

    private Integer activeStatus;

    private Integer status;

    private Long modifyTime;

    private Short isReady;

    private Long matchingUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(Long latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Short getIsReady() {
        return isReady;
    }

    public void setIsReady(Short isReady) {
        this.isReady = isReady;
    }

    public Long getMatchingUserId() {
        return matchingUserId;
    }

    public void setMatchingUserId(Long matchingUserId) {
        this.matchingUserId = matchingUserId;
    }
}