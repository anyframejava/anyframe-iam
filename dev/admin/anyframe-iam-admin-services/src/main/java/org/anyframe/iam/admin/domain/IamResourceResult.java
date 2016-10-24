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

/**
 * An object that obtains both RESOURCE information and ROLE information
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "SECURED_RESOURCES")
public class IamResourceResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private int lev;

	private String roleId;

	private String roleName;

	private String resourceId;

	private String resourceName;

	private String resourcePattern;

	private String resourceType;

	private int sortOrder;

	@Id
	@Column(name = "RESOURCEID", unique = true, nullable = false, length = 10)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCENAME", nullable = false, length = 50)
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCEPATTERN", nullable = false, length = 300)
	public String getResourcePattern() {
		return resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	@Column(name = "RESOURCETYPE", nullable = false, length = 10)
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "LEV", length = 2)
	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	@Column(name = "ROLEID", nullable = false, length = 50)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "SORTORDER", nullable = false, length = 5)
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "ROLENAME", nullable = false, length = 50)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("roleId").append("='").append(getRoleId()).append("',");
		sb.append("roleName").append("='").append(getRoleName()).append("',");
		sb.append("resourceId").append("='").append(getResourceId()).append("',");
		sb.append("resourceName").append("='").append(getResourceName()).append("',");
		sb.append("resourcePattern").append("='").append(getResourcePattern()).append("',");
		sb.append("resourceType").append("='").append(getResourceType()).append("',");
		sb.append("sortOrder").append("='").append(getSortOrder()).append("',");
		sb.append(" ]");

		return sb.toString();
	}

}
