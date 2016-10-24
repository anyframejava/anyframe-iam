package com.sds.emp.code.services;

import java.io.Serializable;

public class CodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codeType;

    private String code;

    private String codeName;

    private String codeDesc;

    private String regId;

    private java.sql.Timestamp regDate;

    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getRegId() {
        return this.regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public java.sql.Timestamp getRegDate() {
        return this.regDate;
    }

    public void setRegDate(java.sql.Timestamp regDate) {
        this.regDate = regDate;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" codeType - " + codeType + "\n");
        arguments.append(" code - " + code + "\n");
        arguments.append(" codeName - " + codeName + "\n");
        arguments.append(" codeDesc - " + codeDesc + "\n");
        arguments.append(" regId - " + regId + "\n");
        arguments.append(" regDate - " + regDate);

        return arguments.toString();
    }
}
