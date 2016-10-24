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
package org.anyframe.iam.core.intercept.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.FilterInvocation;

/**
 * This class extends FilterInvocation of Spring Security.
 * It is used to divide with FilterInvocation
 * 
 * @author Byunghun Woo
 * 
 */
public class FilterInvocationWrapper extends FilterInvocation {

	/**
	 * This method set request, response, chain about original filterInvocation.
	 * After this, it will call super's method.
	 * 
	 * @param fi
	 * 				FilterInvocation object
	 */
	public FilterInvocationWrapper(FilterInvocation fi) {
		super(fi.getRequest(), fi.getResponse(), fi.getChain());
	}

	/**
	 * This method set request, response, chain about original filterInvocation.
	 * After this, it will call super's method.
	 * 
	 * @param request
	 * 				ServletRequest object
	 * @param response
	 * 				ServletResponse object
	 * @param chain
	 * 				FilterChain object
	 */
	public FilterInvocationWrapper(ServletRequest request, ServletResponse response, FilterChain chain) {
		super(request, response, chain);
	}

}
