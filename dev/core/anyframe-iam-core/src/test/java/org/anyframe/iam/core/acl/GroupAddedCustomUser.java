package org.anyframe.iam.core.acl;

import org.anyframe.iam.core.userdetails.jdbc.CustomUser;

public class GroupAddedCustomUser extends CustomUser {

	private static final long serialVersionUID = -552301198799599846L;

	private String groupId;

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

}
