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

package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class TempUsers implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "compKey", unique = true, nullable = false, length = 70)
	@SuppressWarnings("unused")
	private String compKey;
	
	public String getCompKey() {
		return userId + roleId;
	}

	public void setCompKey(String compKey) {
		this.compKey = userId + roleId;
	}
	
	@Column(name = "userId", length = 20)
	private String userId;

	@Column(name = "userName", length = 50)
	private String userName;

	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "enabled", length = 1)
	private String enabled;

	@Column(name = "groupId", length = 20)
	private String groupId;
	
	@Column(name = "roleId", length = 50)
	private String roleId;
	
	@Column(name = "createDate", length = 8, updatable = false)
	private String createDate;

	@Column(name = "modifyDate", length = 8)
	private String modifyDate;
	
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TempUsers pojo = (TempUsers) o;

		if ((userName != null) ? (!userName.equals(pojo.userName)) : (pojo.userName != null)) {
			return false;
		}

		if ((password != null) ? (!password.equals(pojo.password)) : (pojo.password != null)) {
			return false;
		}

		if ((enabled != null) ? (!enabled.equals(pojo.enabled)) : (pojo.enabled != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((userName != null) ? userName.hashCode() : 0);
		result = (31 * result) + ((password != null) ? password.hashCode() : 0);
		result = (31 * result) + ((enabled != null) ? enabled.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("userId").append("='").append(getUserId()).append("', ");
		sb.append("userName").append("='").append(getUserName()).append("', ");
		sb.append("password").append("='").append(getPassword()).append("', ");
		sb.append("enabled").append("='").append(getEnabled()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("', ");
		sb.append("groupId").append("='").append(getGroupId()).append("', ");
		sb.append("roleId").append("='").append(getRoleId()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
