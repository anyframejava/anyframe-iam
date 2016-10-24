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

package org.anyframe.iam.admin.common;

import org.anyframe.exception.BaseException;
import org.springframework.context.MessageSource;

/**
 * This IAMException class contains various constructor for different
 * functionality on this project.
 * @author Taeho Kim
 */
public class IAMException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * The constructor with a message key, with parameters, and with a wrapped
	 * exception (with all the formal parameters).
	 * @param messageSource message management service to extract a message
	 * @param messageKey the message key of this exception
	 */
	public IAMException(final MessageSource messageSource, final String messageKey) {
		super(messageSource, messageKey);
	}

	/**
	 * The constructor with a message key, with parameters, and with a wrapped
	 * exception (with all the formal parameters).
	 * @param messageSource message management service to extract a message
	 * @param messageKey the message key of this exception
	 * @param wrappedException the exception that is wrapped in this exception
	 */
	public IAMException(final MessageSource messageSource, final String messageKey, final Throwable wrappedException) {
		super(messageSource, messageKey, wrappedException);
	}

	/**
	 * Constructor with a message key and an exception.
	 * @param message the message of this exception
	 * @param exception the exception that is wrapped in this exception
	 */
	public IAMException(final String message, final Throwable exception) {
		super(message, null, exception);
	}

	/**
	 * Constructor with a message key and an exception.
	 * @param message the message of this exception
	 */
	public IAMException(final String message) {
		super(message);
	}
}
