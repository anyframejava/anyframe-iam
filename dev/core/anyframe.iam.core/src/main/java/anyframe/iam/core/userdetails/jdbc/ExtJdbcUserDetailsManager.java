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

import java.lang.reflect.Constructor;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContextException;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.jdbc.JdbcUserDetailsManager;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * This class extends JdbcUserDetailsManager of Spring Security.
 * It treat custom user information.
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtJdbcUserDetailsManager extends JdbcUserDetailsManager {

	/**
	 * MappingSqlQuery to user extension
	 */
	private MappingSqlQuery extUsersByUsernameMapping;

	/**
	 * in case of making mapping javaBeans for custom user information object, 
	 * set property.
	 */
	private String mapClass;

	/**
	 * this field is property name of group information 
	 * in case of user information has group info when custom user finding 
	 */
	private String userGroupPropertyName;

	public String getMapClass() {
		return mapClass;
	}

	public void setMapClass(String mapClass) {
		this.mapClass = mapClass;
	}

	public void setUserGroupPropertyName(String userGroupPropertyName) {
		this.userGroupPropertyName = userGroupPropertyName;
	}

	public String getUserGroupPropertyName() {
		return userGroupPropertyName;
	}

	protected void initDao() throws ApplicationContextException {
		super.initDao();

		initExtUserMappingSqlQueries();
	}

	/**
	 * This method operates initialization in inheritance area of Spring Security.
	 * After that to treat custom user information, method operates additional initialization logic. 
	 * 
	 * @throws ApplicationContextException
	 */
	private void initExtUserMappingSqlQueries() throws ApplicationContextException {
		// userGroupFieldName 이 설정되어 있으면 이를 적용함
		if (userGroupPropertyName != null) {
			ExtUser.setUserGroupPropertyName(getUserGroupPropertyName());
		}

		if (mapClass == null) {
			this.extUsersByUsernameMapping = new ExtUsersByUsernameMapping(getDataSource(), getUsersByUsernameQuery());
		}
		else {
			try {
				Class clazz = Class.forName(mapClass);
				Constructor constructor = clazz.getConstructor(new Class[] { DataSource.class, String.class });
				this.extUsersByUsernameMapping = (MappingSqlQuery) constructor.newInstance(new Object[] {
						getDataSource(), getUsersByUsernameQuery() });

			}
			catch (Exception e) {
				throw new ApplicationContextException(e.getMessage(), e);
			}
		}
	}

	/**
	 * In case of setting extUsersByUsernameMapping, 
	 * this method operate MappingSqlQuery that extends IAM ExtUsersByUsernameMapping to treat custom user information.
	 * In case of not setting extUSersByUsernameMapping,
	 * this method run base ExtUserByUsernameMapping.(Return will be Map object)  
	 */
	protected List loadUsersByUsername(String username) {
		return extUsersByUsernameMapping.execute(username);
	}

	/**
	 * Setting UserDetails treated by Spring Security to ExtUSer 
	 */
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
			GrantedAuthority[] combinedAuthorities) {

		String returnUsername = userFromUserQuery.getUsername();

		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}

		Object customUserVO = ((ExtUser) userFromUserQuery).getCustomUser();

		return new ExtUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true,
				true, combinedAuthorities, customUserVO);
	}

}
