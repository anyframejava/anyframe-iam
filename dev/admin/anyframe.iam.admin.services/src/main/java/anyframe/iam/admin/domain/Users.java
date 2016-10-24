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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

/**
 * USERS Domain Object that related with USERS table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "USERS")
public class Users extends BaseObject implements Serializable {
	private String userId;

	private String userName;

	private String password;

	private String enabled;

	private String createDate;

	private String modifyDate;

	private Set<GroupsUsers> groupsUserses = new HashSet<GroupsUsers>(0);

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", nullable = false, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ENABLED", length = 1)
	public String getEnabled() {
		return this.enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "CREATE_DATE", length = 8, updatable = false)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFY_DATE", length = 8)
	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	public Set<GroupsUsers> getGroupsUserses() {
		return this.groupsUserses;
	}

	public void setGroupsUserses(Set<GroupsUsers> groupsUserses) {
		this.groupsUserses = groupsUserses;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Users pojo = (Users) o;

		if ((userName != null) ? (!userName.equals(pojo.userName)) : (pojo.userName != null)) {
			return false;
		}

		if ((password != null) ? (!password.equals(pojo.password)) : (pojo.password != null)) {
			return false;
		}

		if ((enabled != null) ? (!enabled.equals(pojo.enabled)) : (pojo.enabled != null)) {
			return false;
		}

		if ((createDate != null) ? (!createDate.equals(pojo.createDate)) : (pojo.createDate != null)) {
			return false;
		}

		if ((modifyDate != null) ? (!modifyDate.equals(pojo.modifyDate)) : (pojo.modifyDate != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((userName != null) ? userName.hashCode() : 0);
		result = (31 * result) + ((password != null) ? password.hashCode() : 0);
		result = (31 * result) + ((enabled != null) ? enabled.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

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

		sb.append("]");

		return sb.toString();
	}
}
