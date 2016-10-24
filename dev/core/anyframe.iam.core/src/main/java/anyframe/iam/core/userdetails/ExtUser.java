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
package anyframe.iam.core.userdetails;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.ReflectionUtils;

/**
 * Spring Security 의 User 를 extends 하고 있으며 custom 사용자 정보 객체(별도의 VO 등으로 처리 가능,
 * default 는 Map) 를 포함하고 있다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtUser extends User implements UserDetails {

	private static final long serialVersionUID = -5348173960385039444L;

	/**
	 * USER_GROUP_PROPERTY_NAME 사용자에 대한 Group 을 나타내는 프로퍼티 명 - default 는
	 * "userGroup" 임
	 */
	private static String USER_GROUP_PROPERTY_NAME = "userGroup";

	/**
	 * 확장 가능한 커스텀 사용자 정보 객체
	 */
	private Object customUser;

	private String userGroup;

	/**
	 * ExtUser 생성자 - customUser 를 추가로 설정할 수 있다. userGroup 을 설정할 수 있는 경우 해당 정보를
	 * 포함한다.
	 * 
	 * @see org.springframework.security.userdetails.User#User(String, String,
	 * boolean, boolean, boolean, boolean, GrantedAuthority[])
	 */
	public ExtUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities, Object customUser)
			throws IllegalArgumentException {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.customUser = customUser;
		setUserGroupIfPossible(customUser);

	}

	/**
	 * static 필드로 최초 한번 설정해 두면 해당 USER_GROUP_PROPERTY_NAME 값을 customUser 객체로 부터
	 * 검사하여 존재하는 경우 userGroup 으로 설정한다.
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
					FieldUtils.getFieldValue(customUser, field.getName());
				}
				catch (IllegalAccessException e) {
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
	 * @see anyframe.iam.core.userdetails.jdbc.ExtJdbcUserDetailsManager 초기화 시
	 * userGroupPropertyName property 가 설정되어 있는 경우 호출된다.
	 * @param userGroupPropertyName
	 */
	public static void setUserGroupPropertyName(String userGroupPropertyName) {
		USER_GROUP_PROPERTY_NAME = userGroupPropertyName;
	}

}
