package demo.domain.oob;

import java.io.Serializable;

public class OperateLog implements Serializable {
    private Long id;

    private String action;

    private String data;

    private Long authorId;

    private String ip;

    private Long created;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    private OperateLog(Builder b) {
        id = b.id;
        action = b.action;
        data = b.data;
        authorId = b.authorId;
        ip = b.ip;
        created = b.created;
    }

    public OperateLog() {
        super();
    }

    public static class Builder {
        private Long id;

        private String action;

        private String data;

        private Long authorId;

        private String ip;

        private Long created;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder authorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder created(Long created) {
            this.created = created;
            return this;
        }

        public OperateLog build() {
            return new OperateLog(this);
        }
    }
}