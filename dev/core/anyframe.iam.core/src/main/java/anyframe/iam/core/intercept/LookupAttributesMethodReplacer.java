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

import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.security.ConfigAttributeDefinition;

import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * 매 request 마다 요청 url 에 대한 best matching 보호자원-권한 맵핑 정보를 DB 기반으로 찾기 위해
 * DefaultFilterInvocationDefinitionSource 의 lookupAttributes 메서드를 가로채어 수행하기 위한
 * MethodReplacer 이다.
 * 
 * @author Byunghun Woo
 * 
 */
public class LookupAttributesMethodReplacer implements MethodReplacer {

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.beans.factory.support.
	 * MethodReplacer#reimplement(java.lang.Object, java.lang.reflect.Method,
	 * java.lang.Object[])
	 */
	public Object reimplement(Object target, Method method, Object[] args) throws Exception {
		ConfigAttributeDefinition attributes = null;

		// DB 검색
		attributes = securedObjectService.getMatchedRequestMapping((String) args[0]);

		return attributes;
	}

}
