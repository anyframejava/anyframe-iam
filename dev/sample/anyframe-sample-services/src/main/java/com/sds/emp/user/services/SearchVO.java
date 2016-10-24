package com.sds.emp.user.services;

import java.io.Serializable;

public class SearchVO implements Serializable {
    private String searchCondition = "";

    private String searchKeyword = "";

    private int pageIndex = 1;

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

    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" searchCondition - " + searchCondition + "\n");
        arguments.append(" searchKeyword - " + searchKeyword + "\n");
        arguments.append(" pageIndex - " + pageIndex + "\n");

        return arguments.toString();
    }

}
