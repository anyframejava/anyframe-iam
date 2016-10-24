package com.sds.emp.code.services;

import java.util.Collection;

public interface CodeService {
    Collection getCodeList(String codeType) throws Exception;
}
