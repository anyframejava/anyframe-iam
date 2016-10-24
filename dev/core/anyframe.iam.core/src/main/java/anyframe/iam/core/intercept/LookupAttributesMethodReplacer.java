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
package anyframe.iam.core.intercept;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.security.access.ConfigAttribute;

import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * This class is a MethodReplacer to operate method that interrupted from lookupAttributes of 
 * DefaultFilterInvocationDefinitionSource in order to find mapping information of secured resources-role 
 * related with each request URL based on DB.
 * 
 * @author Byunghun Woo
 * 
 */
public class LookupAttributesMethodReplacer implements MethodReplacer {

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	/**
	 * @see org.springframework.beans.factory.support.MethodReplacer
	 * #reimplement(java.lang.Object, java.lang.reflect.Method,java.lang.Object[])
	 */
	public Object reimplement(Object target, Method method, Object[] args) throws Exception {
		List<ConfigAttribute> attributes = null;

		// DB 검색
		attributes = securedObjectService.getMatchedRequestMapping((String) args[0]);

		return attributes;
	}

}
