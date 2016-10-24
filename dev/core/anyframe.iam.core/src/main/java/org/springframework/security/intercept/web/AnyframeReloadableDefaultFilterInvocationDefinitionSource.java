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
package org.springframework.security.intercept.web;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.util.UrlMatcher;

import anyframe.common.exception.BaseException;
import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * Current secured resources based on DB with URL type - Extension class of 
 * DefaultFilterInvocationDefinitionSource of Spring Security to reflect directly 
 * authority mapping information in runtime.
 * It follows Spring Security package for extension of method situated default modifier.
 * 
 * @author ByungHun Woo
 */
public class AnyframeReloadableDefaultFilterInvocationDefinitionSource extends DefaultFilterInvocationDefinitionSource
		implements ApplicationContextAware {

	private MessageSource messageSource;

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

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	/**
	 * 
	 */
	public AnyframeReloadableDefaultFilterInvocationDefinitionSource(UrlMatcher urlMatcher, LinkedHashMap requestMap) {
		super(urlMatcher, requestMap);
	}

	/**
	 * Reloading authority mapping information of secured resources
	 * 
	 * @throws Exception
	 */
	public void reloadRequestMap() throws Exception {

		try {
			Map reloadedMap = securedObjectService.getRolesAndUrl();

			Iterator iterator = reloadedMap.entrySet().iterator();

			// 이전 데이터 삭제
			Map mapToUse = getRequestMap();
			mapToUse.clear();

			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				RequestKey reqKey = (RequestKey) entry.getKey();
				addSecureUrl(reqKey.getUrl(), reqKey.getMethod(), (ConfigAttributeDefinition) entry.getValue());
			}

			if (logger.isInfoEnabled()) {
				logger.info("Secured Url Resources - Role Mappings reloaded at Runtime!");
			}

		}
		catch (Exception e) {
			logger.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Reload RequestMap" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}
	}

	void addSecureUrl(String pattern, String method, ConfigAttributeDefinition attr) {
		Map mapToUse = getRequestMap();

		mapToUse.put(getUrlMatcher().compile(pattern), attr);

		if (logger.isDebugEnabled()) {
			logger.debug("Added URL pattern: " + pattern + "; attributes: " + attr
					+ (method == null ? "" : " for HTTP method '" + method + "'"));
		}
	}

}
