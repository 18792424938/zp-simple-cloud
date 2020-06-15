package com.zp.common.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 */
public class BaseEntity implements Serializable  {
    protected Date createDate;
    protected Date updateDate;
    protected String createId;
    protected String updateId;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}
