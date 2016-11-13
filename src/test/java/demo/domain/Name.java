package demo.domain;

import java.io.Serializable;
import java.util.Date;

public class Name implements Serializable {
    private Long id;

    private String name;

    private Date creatTime;

    private Byte isDeleted;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private Name(Builder b) {
        id = b.id;
        name = b.name;
        creatTime = b.creatTime;
        isDeleted = b.isDeleted;
        updateTime = b.updateTime;
    }

    public Name() {
        super();
    }

    public static class Builder {
        private Long id;

        private String name;

        private Date creatTime;

        private Byte isDeleted;

        private Date updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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

        public Name build() {
            return new Name(this);
        }
    }
}