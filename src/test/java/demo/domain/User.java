package demo.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;

    private String userName;

    private Date creatTime;

    private Byte isDeleted;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private User(Builder b) {
        id = b.id;
        userName = b.userName;
        creatTime = b.creatTime;
        isDeleted = b.isDeleted;
        updateTime = b.updateTime;
    }

    public User() {
        super();
    }

    public static class Builder {
        private Integer id;

        private String userName;

        private Date creatTime;

        private Byte isDeleted;

        private Date updateTime;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder creatTime(Date creatTime) {
            this.creatTime = creatTime;
            return this;
        }

        public Builder isDeleted(Byte isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}