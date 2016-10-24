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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

/**
 * ROLES_HIERARCHY Domain Object that related with ROLES_HIERARCHY table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "ROLES_HIERARCHY")
public class RolesHierarchy extends BaseObject implements Serializable {
	private RolesHierarchyId id;

	private Roles rolesByChildRole;

	private Roles rolesByParentRole;

	private String createDate;

	private String modifyDate;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "parentRole", column = @Column(name = "PARENT_ROLE", nullable = false, length = 50)),
			@AttributeOverride(name = "childRole", column = @Column(name = "CHILD_ROLE", nullable = false, length = 50)) })
	public RolesHierarchyId getId() {
		return this.id;
	}

	public void setId(RolesHierarchyId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHILD_ROLE", nullable = false, insertable = false, updatable = false)
	public Roles getRolesByChildRole() {
		return this.rolesByChildRole;
	}

	public void setRolesByChildRole(Roles rolesByChildRole) {
		this.rolesByChildRole = rolesByChildRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ROLE", nullable = false, insertable = false, updatable = false)
	public Roles getRolesByParentRole() {
		return this.rolesByParentRole;
	}

	public void setRolesByParentRole(Roles rolesByParentRole) {
		this.rolesByParentRole = rolesByParentRole;
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

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		RolesHierarchy pojo = (RolesHierarchy) o;

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
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");

		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
