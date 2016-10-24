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
 * Spring Security 의 JdbcUserDetailsManager 를 extends 하고 있으며 커스텀 사용자 정보를 처리 가능토록
 * 확장하고 있다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtJdbcUserDetailsManager extends JdbcUserDetailsManager {

	/**
	 * 사용자 정보 확장을 위한 MappingSqlQuery
	 */
	private MappingSqlQuery extUsersByUsernameMapping;

	/**
	 * 커스텀 사용자 정보 객체를 위한 맵핑 javaBeans 를 만든 경우 해당 property 로 설정함
	 */
	private String mapClass;

	/**
	 * 커스텀 사용자 정보 조회 시 사용자의 group 정보 를 포함하는 경우 해당 group 정보에 대한 property 명
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

	/**
	 * 
	 */
	protected void initDao() throws ApplicationContextException {
		super.initDao();

		initExtUserMappingSqlQueries();
	}

	/**
	 * Spring Security 상속 영역의 초기화 후 커스텀 사용자 정보 처리를 위한 추가 초기화 로직을 수행한다.
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
	 * extUsersByUsernameMapping 을 설정한 경우 커스텀 사용자 정보 처리를 위한 IAM 의
	 * ExtUsersByUsernameMapping 을 확장한 해당 MappingSqlQuery 를 실행한다. 설정하지 않은 경우 기본
	 * ExtUsersByUsernameMapping 를 실행한다.(결과는 Map 으로 되돌려짐)
	 */
	protected List loadUsersByUsername(String username) {
		return extUsersByUsernameMapping.execute(username);
	}

	/**
	 * Spring Security 가 처리하는 UserDetails 를 ExtUser 로 설정한다. <br/>
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
