package com.sds.emp.sale.services;

import java.io.Serializable;
import java.sql.Timestamp;

public class CategoryVO implements Serializable {
    private String categoryNo;

    private String categoryName;

    private String categoryDesc;

    private String useYn;

    private String regId;

    private Timestamp regDate;

    private String modifyId;

    private Timestamp modifyDate;


    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public String getRegId() {
        return regId;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" categoryNo - " + categoryNo + "\n");
        arguments.append(" categoryName - " + categoryName + "\n");
        arguments.append(" categoryDesc - " + categoryDesc + "\n");
        arguments.append(" useYn - " + useYn + "\n");
        arguments.append(" regId - " + regId + "\n");
        arguments.append(" regDate - " + regDate + "\n");
        arguments.append(" modifyId - " + modifyId + "\n");
        arguments.append(" modifyDate - " + modifyDate);

        return arguments.toString();
    }
}
