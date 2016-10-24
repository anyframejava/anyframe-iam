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
package anyframe.iam.core.userdetails.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.hierarchicalroles.UserDetailsWrapper;
import org.springframework.security.core.context.SecurityContextHolder;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * Get custom user information about currently log-in user
 * 
 * @author Byunghun Woo
 * 
 */
public class CustomUserDetailsHelper {

	private final static Log log = LogFactory.getLog(CustomUserDetailsHelper.class);

	/**
	 * In case of using IAM ExtJdbcUserDetailsManager, 
	 * get ExtUser that contains custom user information about currently log-in user 
	 * 
	 * @return ExtUser
	 * 				ExtUser that contains custom user information
	 */
	public static ExtUser getAuthenticatedUser() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetailsWrapper) {
				return (ExtUser) ((UserDetailsWrapper) principal).getUnwrappedUserDetails();
			}
			else {
				return (ExtUser) principal;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
