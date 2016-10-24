package com.sds.emp.sale.services;

import java.io.Serializable;
import java.sql.Timestamp;

public class SaleVO implements Serializable {
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

    private String imageFile;

    private String tranStatusCode;

    private String tranStatusCodeName;
    
    private String dlvyExpectDay;

    private String dlvyAddr;

    private String dlvyCompNo;

    private String dlvyCompName;

    private String dlvyRequest;

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

    private java.sql.Timestamp orderDate =
        new java.sql.Timestamp(new java.util.Date().getTime());

    private java.sql.Timestamp dlvyDate =
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

    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String param) {
        this.sellerId = param;
    }

    public String getCategoryNo() {
        return this.categoryNo;
    }

    public void setCategoryNo(String param) {
        this.categoryNo = param;
    }

    public String getProdDetail() {
        return this.prodDetail;
    }

    public void setProdDetail(String param) {
        this.prodDetail = param;
    }

    public String getManufactureDay() {
        return this.manufactureDay;
    }

    public void setManufactureDay(String param) {
        this.manufactureDay = param;
    }

    public String getAsYn() {
        return this.asYn;
    }

    public void setAsYn(String param) {
        this.asYn = param;
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

    public String getBuyerId() {
        return this.buyerId;
    }

    public void setBuyerId(String param) {
        this.buyerId = param;
    }

    public String getReceiptYn() {
        return this.receiptYn;
    }

    public void setReceiptYn(String param) {
        this.receiptYn = param;
    }

    public String getPaymentOption() {
        return this.paymentOption;
    }

    public void setPaymentOption(String param) {
        this.paymentOption = param;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String param) {
        this.receiverId = param;
    }

    public String getReceiverPhone() {
        return this.receiverPhone;
    }

    public void setReceiverPhone(String param) {
        this.receiverPhone = param;
    }

    public String getDlvyExpectDay() {
        return this.dlvyExpectDay;
    }

    public void setDlvyExpectDay(String param) {
        this.dlvyExpectDay = param;
    }

    public String getDlvyAddr() {
        return this.dlvyAddr;
    }

    public void setDlvyAddr(String param) {
        this.dlvyAddr = param;
    }

    public String getDlvyCompNo() {
        return this.dlvyCompNo;
    }

    public void setDlvyCompNo(String param) {
        this.dlvyCompNo = param;
    }

    public String getDlvyRequest() {
        return this.dlvyRequest;
    }

    public void setDlvyRequest(String param) {
        this.dlvyRequest = param;
    }

    public Timestamp getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Timestamp param) {
        this.orderDate = param;
    }

    public Timestamp getDlvyDate() {
        return this.dlvyDate;
    }

    public void setDlvyDate(Timestamp param) {
        this.dlvyDate = param;
    }

    // ��Ī�� vȸ�� �Ӽ� �߰�
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

    public String getPaymentOptionName() {
        return paymentOptionName;
    }

    public void setPaymentOptionName(String paymentOptionName) {
        this.paymentOptionName = paymentOptionName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTranStatusCodeName() {
        return tranStatusCodeName;
    }

    public void setTranStatusCodeName(String tranStatusCodeName) {
        this.tranStatusCodeName = tranStatusCodeName;
    }

    public String getDlvyCompName() {
        return dlvyCompName;
    }

    public void setDlvyCompName(String dlvyCompName) {
        this.dlvyCompName = dlvyCompName;
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
        arguments.append(" tranStatusCodeName - " + tranStatusCodeName + "\n");
        arguments.append(" regDate - " + regDate + "\n");
        arguments.append(" buyerId - " + buyerId + "\n");
        arguments.append(" buyerName - " + buyerName + "\n");
        arguments.append(" receiptYn - " + receiptYn + "\n");
        arguments.append(" paymentOption - " + paymentOption + "\n");
        arguments.append(" paymentOptionName - " + paymentOptionName + "\n");
        arguments.append(" receiverId - " + receiverId + "\n");
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

}
