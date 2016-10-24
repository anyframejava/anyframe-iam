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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * This class extends MappingSqlQuery. Class treat custom user information as Map.
 * 
 * Following is example of ExtUsersByUsernameMapping extension
 * treating custom user object by JavaBeans called CustomeUser. 
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
	 * User name, password, enabled information must be at first field in selection SQL
	 * as the basic processing of Spring Security.
	 * Keep in mind that custom user will be set as separate member of ExtUser.
	 */
	protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		String username = rs.getString(1);
		String password = rs.getString(2);
		boolean enabled = rs.getBoolean(3);

		if (!metaExists) {
			setColumnNames(rs);
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("HOLDER"));
		UserDetails user = new ExtUser(username, password, enabled, true, true, true,
				authorities, makeCustomUser(rs));

		return user;
	}

	/**
	 * make customUser object to Map object
	 * 
	 * @param rs
	 * 				ResultSet object
	 * @return Object
	 * @throws SQLException
	 * 				fail to make Map object
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
	 * Save column name in member variable from ResultSetMetaData. 
	 * 
	 * @param rs
	 * 				ResultSet
	 * @throws SQLException
	 * 				fail to save column name
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
