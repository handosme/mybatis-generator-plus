package org.ihansen.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ihansen.mybatis.generator.extend.PageHelper;

public class SecKeyEX implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    protected PageHelper pageHelper;

    public SecKeyEX() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPageHelper(PageHelper pageHelper) {
        this.pageHelper=pageHelper;
    }

    public PageHelper getPageHelper() {
        return pageHelper;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNull() {
            addCriterion("MER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNotNull() {
            addCriterion("MER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerIdEqualTo(String value) {
            addCriterion("MER_ID =", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotEqualTo(String value) {
            addCriterion("MER_ID <>", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThan(String value) {
            addCriterion("MER_ID >", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThanOrEqualTo(String value) {
            addCriterion("MER_ID >=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThan(String value) {
            addCriterion("MER_ID <", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThanOrEqualTo(String value) {
            addCriterion("MER_ID <=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLike(String value) {
            addCriterion("MER_ID like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotLike(String value) {
            addCriterion("MER_ID not like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdIn(List<String> values) {
            addCriterion("MER_ID in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotIn(List<String> values) {
            addCriterion("MER_ID not in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdBetween(String value1, String value2) {
            addCriterion("MER_ID between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotBetween(String value1, String value2) {
            addCriterion("MER_ID not between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andUseTypeIsNull() {
            addCriterion("USE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUseTypeIsNotNull() {
            addCriterion("USE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUseTypeEqualTo(String value) {
            addCriterion("USE_TYPE =", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeNotEqualTo(String value) {
            addCriterion("USE_TYPE <>", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeGreaterThan(String value) {
            addCriterion("USE_TYPE >", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("USE_TYPE >=", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeLessThan(String value) {
            addCriterion("USE_TYPE <", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeLessThanOrEqualTo(String value) {
            addCriterion("USE_TYPE <=", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeLike(String value) {
            addCriterion("USE_TYPE like", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeNotLike(String value) {
            addCriterion("USE_TYPE not like", value, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeIn(List<String> values) {
            addCriterion("USE_TYPE in", values, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeNotIn(List<String> values) {
            addCriterion("USE_TYPE not in", values, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeBetween(String value1, String value2) {
            addCriterion("USE_TYPE between", value1, value2, "useType");
            return (Criteria) this;
        }

        public Criteria andUseTypeNotBetween(String value1, String value2) {
            addCriterion("USE_TYPE not between", value1, value2, "useType");
            return (Criteria) this;
        }

        public Criteria andKeyPosiIsNull() {
            addCriterion("KEY_POSI is null");
            return (Criteria) this;
        }

        public Criteria andKeyPosiIsNotNull() {
            addCriterion("KEY_POSI is not null");
            return (Criteria) this;
        }

        public Criteria andKeyPosiEqualTo(String value) {
            addCriterion("KEY_POSI =", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiNotEqualTo(String value) {
            addCriterion("KEY_POSI <>", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiGreaterThan(String value) {
            addCriterion("KEY_POSI >", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiGreaterThanOrEqualTo(String value) {
            addCriterion("KEY_POSI >=", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiLessThan(String value) {
            addCriterion("KEY_POSI <", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiLessThanOrEqualTo(String value) {
            addCriterion("KEY_POSI <=", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiLike(String value) {
            addCriterion("KEY_POSI like", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiNotLike(String value) {
            addCriterion("KEY_POSI not like", value, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiIn(List<String> values) {
            addCriterion("KEY_POSI in", values, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiNotIn(List<String> values) {
            addCriterion("KEY_POSI not in", values, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiBetween(String value1, String value2) {
            addCriterion("KEY_POSI between", value1, value2, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andKeyPosiNotBetween(String value1, String value2) {
            addCriterion("KEY_POSI not between", value1, value2, "keyPosi");
            return (Criteria) this;
        }

        public Criteria andPubFilepathIsNull() {
            addCriterion("PUB_FILEPATH is null");
            return (Criteria) this;
        }

        public Criteria andPubFilepathIsNotNull() {
            addCriterion("PUB_FILEPATH is not null");
            return (Criteria) this;
        }

        public Criteria andPubFilepathEqualTo(String value) {
            addCriterion("PUB_FILEPATH =", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathNotEqualTo(String value) {
            addCriterion("PUB_FILEPATH <>", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathGreaterThan(String value) {
            addCriterion("PUB_FILEPATH >", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathGreaterThanOrEqualTo(String value) {
            addCriterion("PUB_FILEPATH >=", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathLessThan(String value) {
            addCriterion("PUB_FILEPATH <", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathLessThanOrEqualTo(String value) {
            addCriterion("PUB_FILEPATH <=", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathLike(String value) {
            addCriterion("PUB_FILEPATH like", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathNotLike(String value) {
            addCriterion("PUB_FILEPATH not like", value, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathIn(List<String> values) {
            addCriterion("PUB_FILEPATH in", values, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathNotIn(List<String> values) {
            addCriterion("PUB_FILEPATH not in", values, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathBetween(String value1, String value2) {
            addCriterion("PUB_FILEPATH between", value1, value2, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPubFilepathNotBetween(String value1, String value2) {
            addCriterion("PUB_FILEPATH not between", value1, value2, "pubFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathIsNull() {
            addCriterion("PRI_FILEPATH is null");
            return (Criteria) this;
        }

        public Criteria andPriFilepathIsNotNull() {
            addCriterion("PRI_FILEPATH is not null");
            return (Criteria) this;
        }

        public Criteria andPriFilepathEqualTo(String value) {
            addCriterion("PRI_FILEPATH =", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathNotEqualTo(String value) {
            addCriterion("PRI_FILEPATH <>", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathGreaterThan(String value) {
            addCriterion("PRI_FILEPATH >", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathGreaterThanOrEqualTo(String value) {
            addCriterion("PRI_FILEPATH >=", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathLessThan(String value) {
            addCriterion("PRI_FILEPATH <", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathLessThanOrEqualTo(String value) {
            addCriterion("PRI_FILEPATH <=", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathLike(String value) {
            addCriterion("PRI_FILEPATH like", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathNotLike(String value) {
            addCriterion("PRI_FILEPATH not like", value, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathIn(List<String> values) {
            addCriterion("PRI_FILEPATH in", values, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathNotIn(List<String> values) {
            addCriterion("PRI_FILEPATH not in", values, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathBetween(String value1, String value2) {
            addCriterion("PRI_FILEPATH between", value1, value2, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriFilepathNotBetween(String value1, String value2) {
            addCriterion("PRI_FILEPATH not between", value1, value2, "priFilepath");
            return (Criteria) this;
        }

        public Criteria andPriPwdIsNull() {
            addCriterion("PRI_PWD is null");
            return (Criteria) this;
        }

        public Criteria andPriPwdIsNotNull() {
            addCriterion("PRI_PWD is not null");
            return (Criteria) this;
        }

        public Criteria andPriPwdEqualTo(String value) {
            addCriterion("PRI_PWD =", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdNotEqualTo(String value) {
            addCriterion("PRI_PWD <>", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdGreaterThan(String value) {
            addCriterion("PRI_PWD >", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdGreaterThanOrEqualTo(String value) {
            addCriterion("PRI_PWD >=", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdLessThan(String value) {
            addCriterion("PRI_PWD <", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdLessThanOrEqualTo(String value) {
            addCriterion("PRI_PWD <=", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdLike(String value) {
            addCriterion("PRI_PWD like", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdNotLike(String value) {
            addCriterion("PRI_PWD not like", value, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdIn(List<String> values) {
            addCriterion("PRI_PWD in", values, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdNotIn(List<String> values) {
            addCriterion("PRI_PWD not in", values, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdBetween(String value1, String value2) {
            addCriterion("PRI_PWD between", value1, value2, "priPwd");
            return (Criteria) this;
        }

        public Criteria andPriPwdNotBetween(String value1, String value2) {
            addCriterion("PRI_PWD not between", value1, value2, "priPwd");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateIsNull() {
            addCriterion("DONE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDoneDateIsNotNull() {
            addCriterion("DONE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDoneDateEqualTo(Date value) {
            addCriterion("DONE_DATE =", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotEqualTo(Date value) {
            addCriterion("DONE_DATE <>", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateGreaterThan(Date value) {
            addCriterion("DONE_DATE >", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DONE_DATE >=", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateLessThan(Date value) {
            addCriterion("DONE_DATE <", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateLessThanOrEqualTo(Date value) {
            addCriterion("DONE_DATE <=", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateIn(List<Date> values) {
            addCriterion("DONE_DATE in", values, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotIn(List<Date> values) {
            addCriterion("DONE_DATE not in", values, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateBetween(Date value1, Date value2) {
            addCriterion("DONE_DATE between", value1, value2, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotBetween(Date value1, Date value2) {
            addCriterion("DONE_DATE not between", value1, value2, "doneDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("STATE is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("STATE is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("STATE =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("STATE <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("STATE >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("STATE >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("STATE <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("STATE <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("STATE like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("STATE not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("STATE in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("STATE not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("STATE between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("STATE not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserPassIsNull() {
            addCriterion("USER_PASS is null");
            return (Criteria) this;
        }

        public Criteria andUserPassIsNotNull() {
            addCriterion("USER_PASS is not null");
            return (Criteria) this;
        }

        public Criteria andUserPassEqualTo(String value) {
            addCriterion("USER_PASS =", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotEqualTo(String value) {
            addCriterion("USER_PASS <>", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassGreaterThan(String value) {
            addCriterion("USER_PASS >", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassGreaterThanOrEqualTo(String value) {
            addCriterion("USER_PASS >=", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLessThan(String value) {
            addCriterion("USER_PASS <", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLessThanOrEqualTo(String value) {
            addCriterion("USER_PASS <=", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassLike(String value) {
            addCriterion("USER_PASS like", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotLike(String value) {
            addCriterion("USER_PASS not like", value, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassIn(List<String> values) {
            addCriterion("USER_PASS in", values, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotIn(List<String> values) {
            addCriterion("USER_PASS not in", values, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassBetween(String value1, String value2) {
            addCriterion("USER_PASS between", value1, value2, "userPass");
            return (Criteria) this;
        }

        public Criteria andUserPassNotBetween(String value1, String value2) {
            addCriterion("USER_PASS not between", value1, value2, "userPass");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}