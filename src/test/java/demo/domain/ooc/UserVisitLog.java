package demo.domain.ooc;

import java.io.Serializable;
import java.util.Date;

public class UserVisitLog implements Serializable {
    private Integer id;//自增主键

    private Date createtime;//记录建立时间

    private String userid;//userid

    private Date visittime;//记录建立时间

    private String title;//网页标题

    private String url;//网页链接

    private String ip;//ip

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getVisittime() {
        return visittime;
    }

    public void setVisittime(Date visittime) {
        this.visittime = visittime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private UserVisitLog(Builder b) {
        id = b.id;
        createtime = b.createtime;
        userid = b.userid;
        visittime = b.visittime;
        title = b.title;
        url = b.url;
        ip = b.ip;
    }

    public UserVisitLog() {
        super();
    }

    public static class Builder {
        private Integer id;//自增主键

        private Date createtime;//记录建立时间

        private String userid;//userid

        private Date visittime;//记录建立时间

        private String title;//网页标题

        private String url;//网页链接

        private String ip;//ip

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder createtime(Date createtime) {
            this.createtime = createtime;
            return this;
        }

        public Builder userid(String userid) {
            this.userid = userid;
            return this;
        }

        public Builder visittime(Date visittime) {
            this.visittime = visittime;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public UserVisitLog build() {
            return new UserVisitLog(this);
        }
    }
}