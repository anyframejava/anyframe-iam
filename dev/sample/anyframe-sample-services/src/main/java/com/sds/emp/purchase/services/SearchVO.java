package com.sds.emp.purchase.services;

import java.io.Serializable;

public class SearchVO implements Serializable {
    private String searchCondition = "";

    private String searchKeyword = "";

    private int pageIndex = 1;

    private String searchUseYn = "A";

    private String searchAsYn;

    private String searchTranStatusCode;

    private String searchBuyerId;

    public String getSearchUseYn() {
        return searchUseYn;
    }

    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchAsYn() {
        return searchAsYn;
    }

    public void setSearchAsYn(String searchAsYn) {
        this.searchAsYn = searchAsYn;
    }

    public String getSearchTranStatusCode() {
        return searchTranStatusCode;
    }

    public void setSearchTranStatusCode(String searchTranStatusCode) {
        this.searchTranStatusCode = searchTranStatusCode;
    }

    public String getSearchBuyerId() {
        return searchBuyerId;
    }

    public void setSearchBuyerId(String searchBuyerId) {
        this.searchBuyerId = searchBuyerId;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" searchCondition - " + searchCondition + "\n");
        arguments.append(" searchKeyword - " + searchKeyword + "\n");
        arguments.append(" pageIndex - " + pageIndex + "\n");
        arguments.append(" searchUseYn - " + searchUseYn + "\n");
        arguments.append(" searchAsYn - " + searchAsYn + "\n");
        arguments.append(" searchTranStatusCode - " + searchTranStatusCode
            + "\n");
        arguments.append(" searchBuyerId - " + searchBuyerId);

        return arguments.toString();
    }

}
