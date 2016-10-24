package org.anyframe.iam.core.acl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.anyframe.iam.core.userdetails.jdbc.CustomUsersByUsernameMapping;

public class GroupAddedCustomUserMapping extends CustomUsersByUsernameMapping {

	public GroupAddedCustomUserMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	}

	@Override
	public Object makeCustomUser(ResultSet rs) throws SQLException {
		GroupAddedCustomUser customUser = new GroupAddedCustomUser();

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

		customUser.setGroupId(rs.getString("GROUP_ID"));

		return customUser;
	}

}
