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
 * ROLES Domain Object that related with ROLES table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "ROLES")
public class Roles extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String roleId;

	private String roleName;

	private String description;

	private String createDate;

	private String modifyDate;

	private Set<Authorities> authoritieses = new HashSet<Authorities>(0);

	private Set<SecuredResourcesRoles> securedResourcesRoleses = new HashSet<SecuredResourcesRoles>(0);

	private Set<RolesHierarchy> rolesHierarchiesForParentRole = new HashSet<RolesHierarchy>(0);

	private Set<RolesHierarchy> rolesHierarchiesForChildRole = new HashSet<RolesHierarchy>(0);

	public Roles() {

	}

	public Roles(String roldId, String roleName) {
		this.roleId = roldId;
		this.roleName = roleName;
	}

	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<Authorities> getAuthoritieses() {
		return this.authoritieses;
	}

	public void setAuthoritieses(Set<Authorities> authoritieses) {
		this.authoritieses = authoritieses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<SecuredResourcesRoles> getSecuredResourcesRoleses() {
		return this.securedResourcesRoleses;
	}

	public void setSecuredResourcesRoleses(Set<SecuredResourcesRoles> securedResourcesRoleses) {
		this.securedResourcesRoleses = securedResourcesRoleses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rolesByParentRole")
	public Set<RolesHierarchy> getRolesHierarchiesForParentRole() {
		return this.rolesHierarchiesForParentRole;
	}

	public void setRolesHierarchiesForParentRole(Set<RolesHierarchy> rolesHierarchiesForParentRole) {
		this.rolesHierarchiesForParentRole = rolesHierarchiesForParentRole;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rolesByChildRole")
	public Set<RolesHierarchy> getRolesHierarchiesForChildRole() {
		return this.rolesHierarchiesForChildRole;
	}

	public void setRolesHierarchiesForChildRole(Set<RolesHierarchy> rolesHierarchiesForChildRole) {
		this.rolesHierarchiesForChildRole = rolesHierarchiesForChildRole;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Roles pojo = (Roles) o;

		if ((roleName != null) ? (!roleName.equals(pojo.roleName)) : (pojo.roleName != null)) {
			return false;
		}

		if ((description != null) ? (!description.equals(pojo.description)) : (pojo.description != null)) {
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
		result = ((roleName != null) ? roleName.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("roleId").append("='").append(getRoleId()).append("', ");
		sb.append("roleName").append("='").append(getRoleName()).append("', ");
		sb.append("description").append("='").append(getDescription()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
