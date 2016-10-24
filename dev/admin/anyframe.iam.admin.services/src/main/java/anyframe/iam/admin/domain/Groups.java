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
 * GROUPS Domain Object that related with GROUPS table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "GROUPS")
public class Groups extends BaseObject implements Serializable {
	private String groupId;

	private String groupName;

	private String createDate;

	private String modifyDate;

	private Set<GroupsHierarchy> groupsHierarchiesForChildGroup = new HashSet<GroupsHierarchy>(0);

	private Set<GroupsUsers> groupsUserses = new HashSet<GroupsUsers>(0);

	private Set<GroupsHierarchy> groupsHierarchiesForParentGroup = new HashSet<GroupsHierarchy>(0);

	public Groups() {

	}

	@Id
	@Column(name = "GROUP_ID", unique = true, nullable = false, length = 20)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GROUP_NAME", nullable = false, length = 50)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupsByChildGroup")
	public Set<GroupsHierarchy> getGroupsHierarchiesForChildGroup() {
		return this.groupsHierarchiesForChildGroup;
	}

	public void setGroupsHierarchiesForChildGroup(Set<GroupsHierarchy> groupsHierarchiesForChildGroup) {
		this.groupsHierarchiesForChildGroup = groupsHierarchiesForChildGroup;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<GroupsUsers> getGroupsUserses() {
		return this.groupsUserses;
	}

	public void setGroupsUserses(Set<GroupsUsers> groupsUserses) {
		this.groupsUserses = groupsUserses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupsByParentGroup")
	public Set<GroupsHierarchy> getGroupsHierarchiesForParentGroup() {
		return this.groupsHierarchiesForParentGroup;
	}

	public void setGroupsHierarchiesForParentGroup(Set<GroupsHierarchy> groupsHierarchiesForParentGroup) {
		this.groupsHierarchiesForParentGroup = groupsHierarchiesForParentGroup;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Groups pojo = (Groups) o;

		if ((groupName != null) ? (!groupName.equals(pojo.groupName)) : (pojo.groupName != null)) {
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
		result = ((groupName != null) ? groupName.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("groupId").append("='").append(getGroupId()).append("', ");
		sb.append("groupName").append("='").append(getGroupName()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
