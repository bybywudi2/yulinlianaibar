package com.by.bean;

public class User {
    private Long id;

    private Long userwx;

    private String realname;

    private Short sex;

    private Long birthday;

    private Integer height;

    private Integer weight;

    private Integer education;

    private String company;

    private Integer compiled;

    private String workOfFather;

    private String workOfMom;

    private String photoSelf;

    private String photoOthers;

    private Long createTime;

    private String phone;

    private Integer status;

    private Long modifyTime;

    private Short isLiveLocationYulin;

    private String liveLocation;

    private Short isInformationPublic;

    private String hometown;

    private String job;

    private String standard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserwx() {
        return userwx;
    }

    public void setUserwx(Long userwx) {
        this.userwx = userwx;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Integer getCompiled() {
        return compiled;
    }

    public void setCompiled(Integer compiled) {
        this.compiled = compiled;
    }

    public String getWorkOfFather() {
        return workOfFather;
    }

    public void setWorkOfFather(String workOfFather) {
        this.workOfFather = workOfFather == null ? null : workOfFather.trim();
    }

    public String getWorkOfMom() {
        return workOfMom;
    }

    public void setWorkOfMom(String workOfMom) {
        this.workOfMom = workOfMom == null ? null : workOfMom.trim();
    }

    public String getPhotoSelf() {
        return photoSelf;
    }

    public void setPhotoSelf(String photoSelf) {
        this.photoSelf = photoSelf == null ? null : photoSelf.trim();
    }

    public String getPhotoOthers() {
        return photoOthers;
    }

    public void setPhotoOthers(String photoOthers) {
        this.photoOthers = photoOthers == null ? null : photoOthers.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public Short getIsLiveLocationYulin() {
        return isLiveLocationYulin;
    }

    public void setIsLiveLocationYulin(Short isLiveLocationYulin) {
        this.isLiveLocationYulin = isLiveLocationYulin;
    }

    public String getLiveLocation() {
        return liveLocation;
    }

    public void setLiveLocation(String liveLocation) {
        this.liveLocation = liveLocation == null ? null : liveLocation.trim();
    }

    public Short getIsInformationPublic() {
        return isInformationPublic;
    }

    public void setIsInformationPublic(Short isInformationPublic) {
        this.isInformationPublic = isInformationPublic;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown == null ? null : hometown.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }
}