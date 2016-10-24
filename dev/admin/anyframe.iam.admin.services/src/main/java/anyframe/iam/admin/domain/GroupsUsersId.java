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

package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import anyframe.core.generic.model.BaseObject;

/**
 * The primary key class for the GROUPS_USERS database table.
 * 
 */
@Embeddable
public class GroupsUsersId extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String groupId;

	private String userId;

	@Column(name = "GROUP_ID", nullable = false, length = 20)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "USER_ID", nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		GroupsUsersId pojo = (GroupsUsersId) o;

		if ((groupId != null) ? (!groupId.equals(pojo.groupId)) : (pojo.groupId != null)) {
			return false;
		}

		if ((userId != null) ? (!userId.equals(pojo.userId)) : (pojo.userId != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((groupId != null) ? groupId.hashCode() : 0);
		result = ((userId != null) ? userId.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("groupId").append("='").append(getGroupId()).append("', ");
		sb.append("userId").append("='").append(getUserId()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
