package com.sds.emp.code.services.impl;

import java.util.Collection;

import com.sds.emp.code.services.CodeService;

public class CodeServiceImpl implements CodeService {
    private CodeDAO codeDAO = null;

    public void setCodeDAO(CodeDAO codeDAO) {
        this.codeDAO = codeDAO;
    }

    public Collection getCodeList(String codeType) throws Exception {
        return codeDAO.getCodeList(codeType);
    }
}
