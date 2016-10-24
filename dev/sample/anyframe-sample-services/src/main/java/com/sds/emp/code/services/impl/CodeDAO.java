package com.sds.emp.code.services.impl;

import java.util.Collection;

import anyframe.core.query.IQueryService;

public class CodeDAO {
    private IQueryService queryService;

    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    public Collection getCodeList(String codeType) throws Exception {
        return queryService.find("getCodeList",
            new Object[] {codeType });
    }
}
