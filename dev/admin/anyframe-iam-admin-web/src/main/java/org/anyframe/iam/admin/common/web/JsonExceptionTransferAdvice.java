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

package org.anyframe.iam.admin.common.web;

import org.anyframe.iam.admin.common.IAMException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * An object to throw JsonIAMException when JsonError occur
 * @author byounghoon.woo
 * 
 */
public class JsonExceptionTransferAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation mi) throws Throwable {
		try {
			return mi.proceed();
		}
		catch (Throwable t) {
			Exception exception = (Exception) t;

			if (exception instanceof IAMException) {
				throw new JsonIAMException(exception.getMessage(), exception);
			}
			else {
				throw new JsonIAMException(exception.getMessage(), exception);
			}
		}
	}
}