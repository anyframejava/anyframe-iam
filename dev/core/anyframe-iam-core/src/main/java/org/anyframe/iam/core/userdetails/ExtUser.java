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
package org.anyframe.iam.core.userdetails;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.ReflectionUtils;

/**
 * This class extends User Class of Spring Security. Also class contains custom
 * user information object.
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtUser extends User implements UserDetails {

	private static final long serialVersionUID = -5348173960385039444L;

	/**
	 * Property name that represent group of user. default value is "userGroup"
	 */
	private static String USER_GROUP_PROPERTY_NAME = "userGroup";

	/**
	 * Custom user information object that can be extended
	 */
	private Object customUser;

	private String userGroup;

	/**
	 * Constructor of ExtUser : It is able to set customUser additional. In case
	 * of being able to set userGroup, Class contains that information.
	 * 
	 * @see org.springframework.security.userdetails.User#User(String, String,
	 * boolean, boolean, boolean, boolean, GrantedAuthority[])
	 */
	public ExtUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> authorities, Object customUser)
			throws IllegalArgumentException {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.customUser = customUser;
		setUserGroupIfPossible(customUser);

	}

	/**
	 * Checking USER_GROUP_PROPERTY_NAME value. After then setting it to
	 * userGroup if it exist.
	 * 
	 * @param customUser
	 */
	private void setUserGroupIfPossible(Object customUser) {
		String userGroup = null;

		if (customUser instanceof Map) {
			userGroup = (String) ((Map) customUser).get(getUserGroupPropertyName());
			if (userGroup == null) {
				userGroup = (String) ((Map) customUser).get(getUserGroupPropertyName().toUpperCase());
			}
		}
		else {
			Field field = ReflectionUtils.findField(customUser.getClass(), getUserGroupPropertyName());
			if (field != null) {
				try {
					userGroup = (String) FieldUtils.getFieldValue(customUser, field.getName());
				}
				catch (Exception e) {
					// nothing
				}
			}
		}
		setUserGroup(userGroup);
	}

	public Object getCustomUser() {
		return customUser;
	}

	public void setCustomUser(Object customUser) {
		this.customUser = customUser;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public static String getUserGroupPropertyName() {
		return USER_GROUP_PROPERTY_NAME;
	}

	/**
	 * @see anyframe.iam.core.userdetails.jdbc.ExtJdbcUserDetailsManager When
	 * initialize the class, this method will be called if userGroupPropertyName
	 * is set.
	 * 
	 * @param userGroupPropertyName
	 */
	public static void setUserGroupPropertyName(String userGroupPropertyName) {
		USER_GROUP_PROPERTY_NAME = userGroupPropertyName;
	}

}
