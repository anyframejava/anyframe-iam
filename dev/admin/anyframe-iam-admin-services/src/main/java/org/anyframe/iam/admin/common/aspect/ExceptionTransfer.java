/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.anyframe.iam.admin.common.aspect;

import java.util.Locale;

import org.anyframe.iam.admin.common.IAMException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;


/**
 * This ExceptionTransfer class contains messageSource and transfer method for
 * catching and transferring exception through aspect service
 * 
 * @author Taeho Kim
 */
public class ExceptionTransfer implements ApplicationContextAware {

	/**
	 * messageSource is used to call getMessage method.
	 */
	private MessageSource messageSource;

	/**
	 * catch and transfer exception through aspect service ( setting :
	 * [src/main/resources/spring/common/context-aspect.xml] )
	 * 
	 * @param thisJoinPoint aspect service JoinPoint
	 * @param exception aspect service exception
	 */
	public void transfer(JoinPoint thisJoinPoint, Exception exception) throws IAMException {

		Object target = thisJoinPoint.getTarget();
		while (target instanceof Advised) {
			try {
				target = ((Advised) target).getTargetSource().getTarget();
			}
			catch (Exception e) {
				LogFactory.getLog(this.getClass()).error("Fail to get target object from JointPoint.", e);
				break;
			}
		}

		String className = target.getClass().getSimpleName().toLowerCase();
		String opName = (thisJoinPoint.getSignature().getName()).toLowerCase();
		Log logger = LogFactory.getLog(target.getClass());

		if (exception instanceof IAMException) {
			IAMException iamEx = (IAMException) exception;
			logger.error(iamEx.getMessage(), iamEx);
			throw iamEx;
		}

		logger.error(messageSource.getMessage("error." + className + "." + opName, new String[] {}, "no messages",
				Locale.getDefault()), exception);

		throw new IAMException(messageSource.getMessage("error." + className + "." + opName, new String[] {}, exception
				.getMessage(), Locale.getDefault()), exception);
	}

	/**
	 * This method is used to set messageSource bean
	 * 
	 * @param applicationContext <code>ApplicationContext</code> object.
	 * @throws BeansException if bean is not found.
	 * @see org.springframework.context.ApplicationContextAware
	 * #setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

}