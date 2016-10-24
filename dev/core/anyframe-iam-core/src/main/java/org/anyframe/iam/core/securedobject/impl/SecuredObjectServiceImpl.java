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
package org.anyframe.iam.core.securedobject.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.anyframe.exception.BaseException;
import org.anyframe.iam.core.securedobject.ISecuredObjectService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.access.ConfigAttribute;

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
					ISecuredObjectService.LOGGER.error(messageSource.getMessage("[SecuredObject Service] There are something wrong definitions in a service configuration file or dependency beans.",
							new String[] {}, Locale.getDefault()));
					throw new BaseException(messageSource, "[SecuredObject Service] Fail to initialize a SecuredObject Service.", e);
				}
			}
		}
	}

	public LinkedHashMap getRolesAndUrl() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndUrl();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" +  "Roles and Url" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public LinkedHashMap getRolesAndMethod() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndMethod();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "Roles and Method" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public LinkedHashMap getRolesAndPointcut() throws BaseException {
		try {
			return securedObjectDAO.getRolesAndPointcut();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "Roles and Pointcut" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public List<ConfigAttribute> getMatchedRequestMapping(String url) throws BaseException {
		try {
			return securedObjectDAO.getRegexMatchedRequestMapping(url);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" +  "MatchedRequestMapping : " + url + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public String getHierarchicalRoles() throws BaseException {
		try {
			return securedObjectDAO.getHierarchicalRoles();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "Hierarchical Roles" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public List getRestrictedTimesRoles() throws BaseException {
		try {
			return securedObjectDAO.getRestrictedTimesRoles();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "Restricted Times Roles" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage()+ "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public List getRestrictedTimesResources() throws BaseException {
		try {
			return securedObjectDAO.getRestrictedTimesResources();
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" +  "Restricted Times Resources" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}

	public List getViewResourceMapping(Map paramMap) throws BaseException {
		try {
			return securedObjectDAO.getViewResourceMapping(paramMap);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "View Resource Mapping" + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
	}
	
	public String getViewHierarchy(String viewResourceId) throws BaseException{
		try {
			return securedObjectDAO.getViewHierarchy(viewResourceId);
		}
		catch (Exception e) {
			ISecuredObjectService.LOGGER.error(getMessageSource().getMessage("[SecuredObject Service] SecuredObject Service [" + "View Hierarchy : " + viewResourceId + "]: Fail to get SecuredObject Data from Database.",
					new Object[] {}, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "[SecuredObject Service] SecuredObject Service [" + e.getMessage() + "]: Fail to get SecuredObject Data from Database.", new Object[] {}, e);
			}
		}
		
	}

}
