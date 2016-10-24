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
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * @author Byunghun Woo
 *
 */
public class CustomUsersByUsernameMapping extends ExtUsersByUsernameMapping {

	public CustomUsersByUsernameMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	}

	@Override
	public Object makeCustomUser(ResultSet rs) throws SQLException {
		CustomUser customUser = new CustomUser();

		customUser.setUserId(rs.getString("USER_ID"));
		customUser.setUserName(rs.getString("USER_NAME"));
		customUser.setPassword(rs.getString("PASSWORD"));
		customUser.setEnabled(rs.getBoolean("ENABLED"));
		customUser.setSsn(rs.getString("SSN"));
		customUser.setSlYn(rs.getString("SL_YN").charAt(0));
		customUser.setBirthDay(rs.getString("BIRTH_DAY"));
		customUser.setAge(rs.getShort("AGE"));
		customUser.setCellPhone(rs.getString("CELL_PHONE"));
		customUser.setAddr(rs.getString("ADDR"));
		customUser.setEmail(rs.getString("EMAIL"));
		customUser.setEmailYn(rs.getString("EMAIL_YN").charAt(0));
		customUser.setImageFile(rs.getString("IMAGE_FILE"));
		customUser.setRegDate(rs.getDate("REG_DATE"));

		return customUser;
	}

}
