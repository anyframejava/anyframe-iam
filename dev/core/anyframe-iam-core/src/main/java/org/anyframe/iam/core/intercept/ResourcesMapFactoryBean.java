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
package org.anyframe.iam.core.intercept;

import java.util.LinkedHashMap;

import org.anyframe.iam.core.securedobject.ISecuredObjectService;
import org.springframework.beans.factory.FactoryBean;

/**
 * Get mapping information of secured resources based on DB, 
 * offer this information as initial data of Bean
 * 
 * @author marcos.sousa - reference
 * http://forum.springsource.org/showthread.php?t=56615&highlight=database&page=2
 * 
 * @author ByungHun Woo
 */
public class ResourcesMapFactoryBean implements FactoryBean {

	private String resourceType;

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	private LinkedHashMap resourcesMap;

	public void init() throws Exception {
		if ("method".equals(resourceType)) {
			resourcesMap = securedObjectService.getRolesAndMethod();
		}
		else if ("pointcut".equals(resourceType)) {
			resourcesMap = securedObjectService.getRolesAndPointcut();
		}
		else {
			resourcesMap = securedObjectService.getRolesAndUrl();
		}
	}

	public Object getObject() throws Exception {
		if (resourcesMap == null) {
			init();
		}
		return resourcesMap;
	}

	public Class getObjectType() {
		return LinkedHashMap.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
