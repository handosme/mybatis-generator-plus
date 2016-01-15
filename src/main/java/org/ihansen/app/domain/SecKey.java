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

    private String orgType;

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
        this.merId = merId;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getKeyPosi() {
        return keyPosi;
    }

    public void setKeyPosi(String keyPosi) {
        this.keyPosi = keyPosi;
    }

    public String getPubFilepath() {
        return pubFilepath;
    }

    public void setPubFilepath(String pubFilepath) {
        this.pubFilepath = pubFilepath;
    }

    public String getPriFilepath() {
        return priFilepath;
    }

    public void setPriFilepath(String priFilepath) {
        this.priFilepath = priFilepath;
    }

    public String getPriPwd() {
        return priPwd;
    }

    public void setPriPwd(String priPwd) {
        this.priPwd = priPwd;
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
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    private SecKey(Builder b) {
        id = b.id;
        merId = b.merId;
        useType = b.useType;
        keyPosi = b.keyPosi;
        pubFilepath = b.pubFilepath;
        priFilepath = b.priFilepath;
        priPwd = b.priPwd;
        createDate = b.createDate;
        doneDate = b.doneDate;
        remark = b.remark;
        state = b.state;
        userId = b.userId;
        userPass = b.userPass;
        orgType = b.orgType;
    }

    public SecKey() {
        super();
    }

    public static class Builder {
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

        private String orgType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder merId(String merId) {
            this.merId = merId;
            return this;
        }

        public Builder useType(String useType) {
            this.useType = useType;
            return this;
        }

        public Builder keyPosi(String keyPosi) {
            this.keyPosi = keyPosi;
            return this;
        }

        public Builder pubFilepath(String pubFilepath) {
            this.pubFilepath = pubFilepath;
            return this;
        }

        public Builder priFilepath(String priFilepath) {
            this.priFilepath = priFilepath;
            return this;
        }

        public Builder priPwd(String priPwd) {
            this.priPwd = priPwd;
            return this;
        }

        public Builder createDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder doneDate(Date doneDate) {
            this.doneDate = doneDate;
            return this;
        }

        public Builder remark(String remark) {
            this.remark = remark;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userPass(String userPass) {
            this.userPass = userPass;
            return this;
        }

        public Builder orgType(String orgType) {
            this.orgType = orgType;
            return this;
        }

        public SecKey build() {
            return new SecKey(this);
        }
    }
}