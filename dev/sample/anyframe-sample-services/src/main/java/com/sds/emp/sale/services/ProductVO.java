package com.sds.emp.sale.services;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class ProductVO implements Serializable {
    private String prodNo;

    private String prodName;

    private String sellerId;

    private String categoryNo;

    private String prodDetail;

    private String manufactureDay;

    private String asYn;

    private java.math.BigDecimal sellQuantity = new java.math.BigDecimal(0);

    private java.math.BigDecimal sellAmount = new java.math.BigDecimal(0);

    private String imageFile;

    private MultipartFile realImageFile;

    private String tranStatusCode;

    private Timestamp regDate =
        new java.sql.Timestamp(new java.util.Date().getTime());

    public String getProdNo() {
        return this.prodNo;
    }

    public void setProdNo(String param) {
        this.prodNo = param;
    }

    public String getProdName() {
        return this.prodName;
    }

    public void setProdName(String param) {
        this.prodName = param;
    }

    public String getCategoryNo() {
        return this.categoryNo;
    }

    public void setCategoryNo(String param) {
        this.categoryNo = param;
    }
    
    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String param) {
        this.sellerId = param;
    }
    
    public java.math.BigDecimal getSellQuantity() {
        return this.sellQuantity;
    }

    public void setSellQuantity(java.math.BigDecimal param) {
        this.sellQuantity = param;
    }

    public java.math.BigDecimal getSellAmount() {
        return this.sellAmount;
    }

    public void setSellAmount(java.math.BigDecimal param) {
        this.sellAmount = param;
    }

    public String getImageFile() {
        return this.imageFile;
    }

    public void setImageFile(String param) {
        this.imageFile = param;
    }

    public String getProdDetail() {
        return this.prodDetail;
    }

    public void setProdDetail(String param) {
        this.prodDetail = param;
    }
    
    public String getAsYn() {
        return this.asYn;
    }

    public void setAsYn(String param) {
        this.asYn = param;
    }

    public String getManufactureDay() {
        return this.manufactureDay;
    }

    public void setManufactureDay(String param) {
        this.manufactureDay = param;
    }

    public String getTranStatusCode() {
        return this.tranStatusCode;
    }

    public void setTranStatusCode(String param) {
        this.tranStatusCode = param;
    }

    public Timestamp getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Timestamp param) {
        this.regDate = param;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" prodNo - " + prodNo + "\n");
        arguments.append(" prodName - " + prodName + "\n");
        arguments.append(" sellerId - " + sellerId + "\n");
        arguments.append(" categoryNo - " + categoryNo + "\n");
        arguments.append(" prodDetail - " + prodDetail + "\n");
        arguments.append(" manufactureDay - " + manufactureDay + "\n");
        arguments.append(" asYn - " + asYn + "\n");
        arguments.append(" sellQuantity - " + sellQuantity + "\n");
        arguments.append(" sellAmount - " + sellAmount + "\n");
        arguments.append(" imageFile - " + imageFile + "\n");
        arguments.append(" tranStatusCode - " + tranStatusCode + "\n");
        arguments.append(" regDate - " + regDate);

        return arguments.toString();
    }

    public MultipartFile getRealImageFile() {
        return realImageFile;
    }

    public void setRealImageFile(MultipartFile realImageFile) {
        this.realImageFile = realImageFile;
    }
}
