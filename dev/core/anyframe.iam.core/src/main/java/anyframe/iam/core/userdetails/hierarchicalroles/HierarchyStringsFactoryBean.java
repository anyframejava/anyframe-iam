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
package anyframe.iam.core.userdetails.hierarchicalroles;

import org.springframework.beans.factory.FactoryBean;

import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * Get hierarchy information of Role based on DB
 * and offer as initialize data to Bean.
 * 
 * @author marcos.sousa - reference
 * http://forum.springframework.org/showthread.php
 * ?t=56615&highlight=database&page=2
 * @author ByungHun Woo
 */
public class HierarchyStringsFactoryBean implements FactoryBean {

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	private String hierarchyStrings;

	public void init() throws Exception {
		hierarchyStrings = (String) securedObjectService.getHierarchicalRoles();
	}

	public Object getObject() throws Exception {
		if (hierarchyStrings == null) {
			init();
		}
		return hierarchyStrings;
	}

	public Class getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
