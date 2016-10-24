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
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.hierarchicalroles.UserDetailsWrapper;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * 로그인한 사용자에 대한 커스텀 사용자 정보를 쉽게 얻을 수 있도록 제공한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class CustomUserDetailsHelper {

	private final static Log log = LogFactory.getLog(CustomUserDetailsHelper.class);

	/**
	 * IAM 의 ExtJdbcUserDetailsManager 를 사용하는 경우 로그인한 사용자에 대한 커스텀 사용자 정보를 포함하는
	 * ExtUser 를 얻는다.
	 * 
	 * @return 커스텀 사용자 정보를 포함하는 ExtUser
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
