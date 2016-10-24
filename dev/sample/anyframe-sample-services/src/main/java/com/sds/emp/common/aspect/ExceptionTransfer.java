package com.sds.emp.common.aspect;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.core.query.QueryServiceException;

import com.sds.emp.common.EmpException;

public class ExceptionTransfer implements ApplicationContextAware {
    /**
     * messageSource is used to call getMessage method
     */
    private MessageSource messageSource;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.messageSource =
            (MessageSource) applicationContext.getBean("messageSource");
    }

    public void transfer(JoinPoint thisJoinPoint, Exception exception)
            throws EmpException {
        String pkgName =
            thisJoinPoint.getTarget().getClass().getName().toLowerCase();
        int lastIndex = pkgName.lastIndexOf(".");
        String className = pkgName.substring(lastIndex + 1);
        String opName = (thisJoinPoint.getSignature().getName()).toLowerCase();

        Log logger = LogFactory.getLog(thisJoinPoint.getTarget().getClass());

        if (exception instanceof EmpException) {
            EmpException empEx = (EmpException) exception;
            logger.error(empEx.getMessage(), empEx);
            throw empEx;
        }

        if (exception instanceof QueryServiceException) {
            logger.error(messageSource.getMessage("error." + className + "."
                + opName + ".query", new String[] {}, Locale.getDefault()),
                exception);
            throw new EmpException(messageSource.getMessage("error."
                + className + "." + opName + ".query", new String[] {}, Locale
                .getDefault()), exception);
        } else {
            logger.error(messageSource.getMessage("error." + className + "."
                + opName, new String[] {}, Locale.getDefault()), exception);
            throw new EmpException(messageSource.getMessage("error."
                + className + "." + opName, new String[] {}, Locale
                .getDefault()), exception);
        }
    }
}
