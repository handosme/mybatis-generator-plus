package org.ihansen.app.domain;

import java.io.Serializable;
import java.util.Date;

public class SecKey implements Serializable {
    private Long id;

    private String merId;

    private String useType;

    private String keyPosi;

    private String pubFilepath;

    private String priFilepath;

    private String priPwd;

    private Date createDate;

    private Date doneDate;

    private String remark;

    private String state;

    private String userId;

    private String userPass;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType == null ? null : useType.trim();
    }

    public String getKeyPosi() {
        return keyPosi;
    }

    public void setKeyPosi(String keyPosi) {
        this.keyPosi = keyPosi == null ? null : keyPosi.trim();
    }

    public String getPubFilepath() {
        return pubFilepath;
    }

    public void setPubFilepath(String pubFilepath) {
        this.pubFilepath = pubFilepath == null ? null : pubFilepath.trim();
    }

    public String getPriFilepath() {
        return priFilepath;
    }

    public void setPriFilepath(String priFilepath) {
        this.priFilepath = priFilepath == null ? null : priFilepath.trim();
    }

    public String getPriPwd() {
        return priPwd;
    }

    public void setPriPwd(String priPwd) {
        this.priPwd = priPwd == null ? null : priPwd.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }
}