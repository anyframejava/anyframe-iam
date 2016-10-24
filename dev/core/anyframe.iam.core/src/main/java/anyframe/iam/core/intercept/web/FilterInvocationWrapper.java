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
package anyframe.iam.core.intercept.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.intercept.web.FilterInvocation;

/**
 * Spring Security 의 FilterInvocation 을 extends 하고 있으며 FilterInvocation 과의 단순
 * 구분을 위해 사용된다.
 * 
 * @author Byunghun Woo
 * 
 */
public class FilterInvocationWrapper extends FilterInvocation {

	/**
	 * 원본 filterInvocation 에 대한 request, response, chain 을 설정하여 super 를 호출한다.
	 * 
	 * @param fi
	 */
	public FilterInvocationWrapper(FilterInvocation fi) {
		super(fi.getRequest(), fi.getResponse(), fi.getChain());
	}

	/**
	 * 
	 */
	public FilterInvocationWrapper(ServletRequest request, ServletResponse response, FilterChain chain) {
		super(request, response, chain);
	}

}
