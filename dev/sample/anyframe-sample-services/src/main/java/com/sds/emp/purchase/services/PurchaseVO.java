package com.sds.emp.purchase.services;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class PurchaseVO implements Serializable {
    private String prodNo;

    private String prodName;

    private String sellerId;

    private String categoryNo;

    private String categoryName;

    private String prodDetail;

    private String manufactureDay;

    private String asYn;

    private java.math.BigDecimal sellQuantity = new java.math.BigDecimal(0);

    private java.math.BigDecimal sellAmount = new java.math.BigDecimal(0);

    private MultipartFile realImageFile;

    private String imageFile;

    private Timestamp regDate =
        new java.sql.Timestamp(new java.util.Date().getTime());

    private String buyerId;

    private String buyerName;

    private String receiptYn;

    private String paymentOption;

    private String paymentOptionName;

    private String receiverId;

    private String receiverName;

    private String receiverPhone;

    private String dlvyExpectDay;

    private String dlvyAddr;

    private String dlvyCompNo;

    private String dlvyCompName;

    private String dlvyRequest;
    
    private String tranStatusCode;

    private String tranStatusCodeName;

    private java.sql.Timestamp orderDate =
        new java.sql.Timestamp(new java.util.Date().getTime());

    private java.sql.Timestamp dlvyDate =
        new java.sql.Timestamp(new java.util.Date().getTime());
    
    public String getAsYn() {
        return asYn;
    }

    public void setAsYn(String asYn) {
        this.asYn = asYn;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    public String getDlvyAddr() {
        return dlvyAddr;
    }

    public void setDlvyAddr(String dlvyAddr) {
        this.dlvyAddr = dlvyAddr;
    }

    public String getDlvyCompName() {
        return dlvyCompName;
    }

    public void setDlvyCompName(String dlvyCompName) {
        this.dlvyCompName = dlvyCompName;
    }

    public String getDlvyCompNo() {
        return dlvyCompNo;
    }

    public void setDlvyCompNo(String dlvyCompNo) {
        this.dlvyCompNo = dlvyCompNo;
    }

    public java.sql.Timestamp getDlvyDate() {
        return dlvyDate;
    }

    public void setDlvyDate(java.sql.Timestamp dlvyDate) {
        this.dlvyDate = dlvyDate;
    }

    public String getDlvyExpectDay() {
        return dlvyExpectDay;
    }

    public void setDlvyExpectDay(String dlvyExpectDay) {
        this.dlvyExpectDay = dlvyExpectDay;
    }

    public String getDlvyRequest() {
        return dlvyRequest;
    }

    public void setDlvyRequest(String dlvyRequest) {
        this.dlvyRequest = dlvyRequest;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getManufactureDay() {
        return manufactureDay;
    }

    public void setManufactureDay(String manufactureDay) {
        this.manufactureDay = manufactureDay;
    }

    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getPaymentOptionName() {
        return paymentOptionName;
    }

    public void setPaymentOptionName(String paymentOptionName) {
        this.paymentOptionName = paymentOptionName;
    }

    public String getProdDetail() {
        return prodDetail;
    }

    public void setProdDetail(String prodDetail) {
        this.prodDetail = prodDetail;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getReceiptYn() {
        return receiptYn;
    }

    public void setReceiptYn(String receiptYn) {
        this.receiptYn = receiptYn;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public java.math.BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(java.math.BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public java.math.BigDecimal getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(java.math.BigDecimal sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public String getTranStatusCode() {
        return tranStatusCode;
    }

    public void setTranStatusCode(String tranStatusCode) {
        this.tranStatusCode = tranStatusCode;
    }

    public String getTranStatusCodeName() {
        return tranStatusCodeName;
    }

    public void setTranStatusCodeName(String tranStatusCodeName) {
        this.tranStatusCodeName = tranStatusCodeName;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" prodNo - " + prodNo + "\n");
        arguments.append(" prodName - " + prodName + "\n");
        arguments.append(" sellerId - " + sellerId + "\n");
        arguments.append(" categoryNo - " + categoryNo + "\n");
        arguments.append(" categoryName - " + categoryName + "\n");
        arguments.append(" prodDetail - " + prodDetail + "\n");
        arguments.append(" manufactureDay - " + manufactureDay + "\n");
        arguments.append(" asYn - " + asYn + "\n");
        arguments.append(" sellQuantity - " + sellQuantity + "\n");
        arguments.append(" sellAmount - " + sellAmount + "\n");
        arguments.append(" imageFile - " + imageFile + "\n");
        arguments.append(" tranStatusCode - " + tranStatusCode + "\n");
        arguments.append(" tranStatusCodeName - " + tranStatusCodeName + "\n");
        arguments.append(" regDate - " + regDate + "\n");
        arguments.append(" buyerId - " + buyerId + "\n");
        arguments.append(" buyerName - " + buyerName + "\n");
        arguments.append(" receiptYn - " + receiptYn + "\n");
        arguments.append(" paymentOption - " + paymentOption + "\n");
        arguments.append(" paymentOptionName - " + paymentOptionName + "\n");
        arguments.append(" receiverId - " + receiverId + "\n");
        arguments.append(" receiverName - " + receiverName + "\n");
        arguments.append(" receiverPhone - " + receiverPhone + "\n");
        arguments.append(" dlvyExpectDay - " + dlvyExpectDay + "\n");
        arguments.append(" dlvyAddr - " + dlvyAddr + "\n");
        arguments.append(" dlvyCompNo - " + dlvyCompNo + "\n");
        arguments.append(" dlvyCompName - " + dlvyCompName + "\n");
        arguments.append(" dlvyRequest - " + dlvyRequest + "\n");
        arguments.append(" orderDate - " + orderDate + "\n");
        arguments.append(" dlvyDate - " + dlvyDate);

        return arguments.toString();
    }

    public MultipartFile getRealImageFile() {
        return realImageFile;
    }

    public void setRealImageFile(MultipartFile realImageFile) {
        this.realImageFile = realImageFile;
    }
}
