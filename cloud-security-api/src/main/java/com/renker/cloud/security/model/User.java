package com.renker.cloud.security.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private String id;

    private String account;

    private String password;

    private Integer root;

    private Integer loginErrorAllowNum;

    private Integer loginErrorNum;

    private Integer status;

    private String salt;

    private Date validTimeBegin;

    private Date validTimeEnd;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoot() {
        return root;
    }

    public void setRoot(Integer root) {
        this.root = root;
    }

    public Integer getLoginErrorAllowNum() {
        return loginErrorAllowNum;
    }

    public void setLoginErrorAllowNum(Integer loginErrorAllowNum) {
        this.loginErrorAllowNum = loginErrorAllowNum;
    }

    public Integer getLoginErrorNum() {
        return loginErrorNum;
    }

    public void setLoginErrorNum(Integer loginErrorNum) {
        this.loginErrorNum = loginErrorNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getValidTimeBegin() {
        return validTimeBegin;
    }

    public void setValidTimeBegin(Date validTimeBegin) {
        this.validTimeBegin = validTimeBegin;
    }

    public Date getValidTimeEnd() {
        return validTimeEnd;
    }

    public void setValidTimeEnd(Date validTimeEnd) {
        this.validTimeEnd = validTimeEnd;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccount() == null ? other.getAccount() == null : this.getAccount().equals(other.getAccount()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getRoot() == null ? other.getRoot() == null : this.getRoot().equals(other.getRoot()))
            && (this.getLoginErrorAllowNum() == null ? other.getLoginErrorAllowNum() == null : this.getLoginErrorAllowNum().equals(other.getLoginErrorAllowNum()))
            && (this.getLoginErrorNum() == null ? other.getLoginErrorNum() == null : this.getLoginErrorNum().equals(other.getLoginErrorNum()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
            && (this.getValidTimeBegin() == null ? other.getValidTimeBegin() == null : this.getValidTimeBegin().equals(other.getValidTimeBegin()))
            && (this.getValidTimeEnd() == null ? other.getValidTimeEnd() == null : this.getValidTimeEnd().equals(other.getValidTimeEnd()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccount() == null) ? 0 : getAccount().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getRoot() == null) ? 0 : getRoot().hashCode());
        result = prime * result + ((getLoginErrorAllowNum() == null) ? 0 : getLoginErrorAllowNum().hashCode());
        result = prime * result + ((getLoginErrorNum() == null) ? 0 : getLoginErrorNum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getValidTimeBegin() == null) ? 0 : getValidTimeBegin().hashCode());
        result = prime * result + ((getValidTimeEnd() == null) ? 0 : getValidTimeEnd().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", root=").append(root);
        sb.append(", loginErrorAllowNum=").append(loginErrorAllowNum);
        sb.append(", loginErrorNum=").append(loginErrorNum);
        sb.append(", status=").append(status);
        sb.append(", salt=").append(salt);
        sb.append(", validTimeBegin=").append(validTimeBegin);
        sb.append(", validTimeEnd=").append(validTimeEnd);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
    
    private void user() {
	}
}