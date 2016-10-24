package com.sds.emp.sale.services;

import java.io.Serializable;

public class SearchVO implements Serializable {
    private String searchCondition = "";

    private String searchKeyword = "";

    private int pageIndex = 1;

    private String searchUseYn;

    private String searchTranStatusCode;

    private String searchCategoryNo;

    private String searchSellerId;

    private String sortColumn;

    private String sortDirection;
    
    private String searchAsYn;
    
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
    
    public String getSearchSellerId() {
        return searchSellerId;
    }

    public void setSearchSellerId(String searchSellerId) {
        this.searchSellerId = searchSellerId;
    }
    
    public String getSearchTranStatusCode() {
        return searchTranStatusCode;
    }

    public void setSearchTranStatusCode(String searchTranStatusCode) {
        this.searchTranStatusCode = searchTranStatusCode;
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

    public void setSearchCategoryNo(String searchCategoryNo) {
        this.searchCategoryNo = searchCategoryNo;
    }

    public String getSearchCategoryNo() {
        return searchCategoryNo;
    }

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" searchCondition - " + searchCondition + "\n");
        arguments.append(" searchKeyword - " + searchKeyword + "\n");
        arguments.append(" pageIndex - " + pageIndex + "\n");
        arguments.append(" searchUseYn - " + searchUseYn + "\n");
        arguments.append(" searchTranStatusCode - " + searchTranStatusCode
            + "\n");
        arguments.append(" searchSellerId - " + searchSellerId);

        return arguments.toString();
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSearchAsYn() {
        return searchAsYn;
    }

    public void setSearchAsYn(String searchAsYn) {
        this.searchAsYn = searchAsYn;
    }

}
