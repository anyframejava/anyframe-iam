package com.sds.emp.purchase.services;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The serializable class DeliveryCompanyVO does not
 * declare a static final serialVersionUID field of
 * type long.
 */

public class DeliveryCompanyVO implements Serializable {

    private String dlvyCompNo;

    private String dlvyCompName;

    private String businessNo;

    private String dlvyCompDesc;

    private String useYn;

    private String regId;

    private Timestamp regDate;

    private String modifyId;

    private Timestamp modifyDate;

    /**
     * Returns the businessNo
     * @return businessNo
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * Sets the business no value to the internal
     * private variable
     * @param businessNo
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * @return
     */
    public String getDlvyCompDesc() {
        return dlvyCompDesc;
    }

    /**
     * @param dlvyCompDesc
     */
    public void setDlvyCompDesc(String dlvyCompDesc) {
        this.dlvyCompDesc = dlvyCompDesc;
    }

    /**
     * @return
     */
    public String getDlvyCompName() {
        return dlvyCompName;
    }

    /**
     * @param dlvyCompName
     */
    public void setDlvyCompName(String dlvyCompName) {
        this.dlvyCompName = dlvyCompName;
    }

    /**
     * @return
     */
    public String getDlvyCompNo() {
        return dlvyCompNo;
    }

    /**
     * @param dlvyCompNo
     */
    public void setDlvyCompNo(String dlvyCompNo) {
        this.dlvyCompNo = dlvyCompNo;
    }

    /**
     * @return
     */
    public Timestamp getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate
     */
    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * @param modifyId
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    /**
     * @return
     */
    public Timestamp getRegDate() {
        return regDate;
    }

    /**
     * @param regDate
     */
    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    /**
     * @return
     */
    public String getRegId() {
        return regId;
    }

    /**
     * @param regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" dlvyCompNo - " + dlvyCompNo + "\n");
        arguments.append(" dlvyCompName - " + dlvyCompName + "\n");
        arguments.append(" businessNo - " + businessNo + "\n");
        arguments.append(" dlvyCompDesc - " + dlvyCompDesc + "\n");
        arguments.append(" useYn - " + useYn + "\n");
        arguments.append(" regId - " + regId + "\n");
        arguments.append(" regDate - " + regDate + "\n");
        arguments.append(" modifyId - " + modifyId + "\n");
        arguments.append(" modifyDate - " + modifyDate);

        return arguments.toString();
    }
}
