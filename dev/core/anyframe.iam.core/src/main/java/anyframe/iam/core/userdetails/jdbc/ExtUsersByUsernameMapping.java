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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * MappingSqlQuery 를 extends 하고 있으며 사용자가 설정한 쿼리에 따른 커스텀 사용자 정보를 기본 객체인 Map 으로
 * 처리한다.<br/>
 * 
 * 아래는 CustomUser 라는 JavaBeans 로 커스텀 사용자 객체를 처리하는 ExtUsersByUsernameMapping 의
 * 사이트 확장 예이다.
 * 
 * <pre>
 * public class CustomUsersByUsernameMapping extends ExtUsersByUsernameMapping {
 * 
 * 	public CustomUsersByUsernameMapping(DataSource ds, String usersByUsernameQuery) {
 * 		super(ds, usersByUsernameQuery);
 * 	}
 * 
 * 	&#064;Override
 * 	public Object makeCustomUser(ResultSet rs) throws SQLException {
 * 		CustomUser customUser = new CustomUser();
 * 
 * 		customUser.setUserId(rs.getString(&quot;USER_ID&quot;));
 * 		customUser.setUserName(rs.getString(&quot;USER_NAME&quot;));
 * 		customUser.setPassword(rs.getString(&quot;PASSWORD&quot;));
 * 		customUser.setEnabled(rs.getBoolean(&quot;ENABLED&quot;));
 * 		customUser.setSsn(rs.getString(&quot;SSN&quot;));
 * 		customUser.setSlYn(rs.getString(&quot;SL_YN&quot;).charAt(0));
 * 		customUser.setBirthDay(rs.getString(&quot;BIRTH_DAY&quot;));
 * 		customUser.setAge(rs.getShort(&quot;AGE&quot;));
 * 		customUser.setCellPhone(rs.getString(&quot;CELL_PHONE&quot;));
 * 		customUser.setAddr(rs.getString(&quot;ADDR&quot;));
 * 		customUser.setEmail(rs.getString(&quot;EMAIL&quot;));
 * 		customUser.setEmailYn(rs.getString(&quot;EMAIL_YN&quot;).charAt(0));
 * 		customUser.setImageFile(rs.getString(&quot;IMAGE_FILE&quot;));
 * 		customUser.setRegDate(rs.getDate(&quot;REG_DATE&quot;));
 * 
 * 		return customUser;
 * 	}
 * 
 * }
 * </pre>
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtUsersByUsernameMapping extends MappingSqlQuery {

	protected int columnCount;

	protected boolean metaExists = false;

	protected String[] columnNames;

	protected ExtUsersByUsernameMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
		declareParameter(new SqlParameter(Types.VARCHAR));
		compile();
	}

	/**
	 * Spring Security 의 기본 처리를 따라 username, password, enabled 정보가 반드시 최초 3개 조회
	 * 필드로 먼저 나타나야 한다. customUser 는 ExtUser 의 별도 멤버로 설정됨에 유의한다.<br/>
	 */
	protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		String username = rs.getString(1);
		String password = rs.getString(2);
		boolean enabled = rs.getBoolean(3);

		if (!metaExists) {
			setColumnNames(rs);
		}

		UserDetails user = new ExtUser(username, password, enabled, true, true, true,
				new GrantedAuthority[] { new GrantedAuthorityImpl("HOLDER") }, makeCustomUser(rs));

		return user;
	}

	/**
	 * 사용자가 정의한 쿼리문에 따른 customUser 객체를 기본인 Map 형태로 작성한다.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected Object makeCustomUser(ResultSet rs) throws SQLException {
		Map map = new ListOrderedMap();

		for (int i = 1; i <= columnCount; ++i) {
			String name = columnNames[i - 1];
			Object value = rs.getObject(i);
			map.put(name, value);
		}

		return map;
	}

	/**
	 * ResultSetMetaData 는 최초 한번만 참고하여 칼럼명을 멤버로 미리 설정해 둔다.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	protected void setColumnNames(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		this.columnCount = meta.getColumnCount();
		columnNames = new String[columnCount];
		this.metaExists = true;

		for (int i = 0; i < columnCount; ++i) {
			columnNames[i] = meta.getColumnName(i + 1);
		}
	}

}
