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
package anyframe.iam.core.securedobject.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.ConfigAttributeDefinition;

import anyframe.common.exception.BaseException;
import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * Enabling applications to approach the data of secured object resources from
 * DataBase which is refered to initial data of Spring Security.
 * 
 * @author ByungHun Woo
 */
public class SecuredObjectServiceImpl implements ISecuredObjectService, ApplicationContextAware, InitializingBean {

	private MessageSource messageSource;

	private SecuredObjectDAO securedObjectDAO;

	/**
	 * set ApplicationContext.
	 * 
	 * @param applicationContext to be set by container
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

	/**
	 * @return the messageSource
	 */
	protected MessageSource getMessageSource() {
		return messageSource;
	}

	public void setSecuredObjectDAO(SecuredObjectDAO securedObjectDAO) {
		this.securedObjectDAO = securedObjectDAO;
	}

	/**
	 * initialize SecuredObjectService.
	 * 
	 * @throws BaseException fail to initialize
	 */
	public void afterPropertiesSet() throws BaseException {
		try {
			// TODO
		}
		catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				if (ISecuredObjectService.LOGGER.isErrorEnabled()) {
					ISecuredObjectService.LOGGER.error(messageSource.getMessage("error.security.initialize.reason",
							new String[] {}, Locale.getDefault()));
					throw new BaseException(messageSource, "error.security.initialize", e);
				}
			}
		}
	}

	public LinkedHashMap getRolesAndUrl() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndUrl();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Roles and Url" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public LinkedHashMap getRolesAndMethod() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndMethod();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Roles and Method" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public LinkedHashMap getRolesAndPointcut() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndPointcut();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Roles and Pointcut" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public ConfigAttributeDefinition getMatchedRequestMapping(String url) throws BaseException {
		try {
			return securedObjectDAO.getRegexMatchedRequestMapping(url);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "MatchedRequestMapping : " + url }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public String getHierarchicalRoles() throws BaseException {
		try {
			return securedObjectDAO.getHierarchicalRoles();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Hierarchical Roles" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public List getRestrictedTimesRoles() throws BaseException {
		try {
			return securedObjectDAO.getRestrictedTimesRoles();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Restricted Times Roles" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public List getRestrictedTimesResources() throws BaseException {
		try {
			return securedObjectDAO.getRestrictedTimesResources();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Restricted Times Resources" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	public List getViewResourceMapping(Map paramMap) throws BaseException {
		try {
			return securedObjectDAO.getViewResourceMapping(paramMap);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "View Resource Mapping" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}
	
	public String getViewHierarchy(String viewResourceId) throws BaseException{
		try {
			return securedObjectDAO.getViewHierarchy(viewResourceId);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "View Hierarchy : " + viewResourceId }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
		
	}

}
