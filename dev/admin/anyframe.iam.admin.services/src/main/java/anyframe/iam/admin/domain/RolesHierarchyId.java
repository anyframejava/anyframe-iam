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
 * The primary key class for the ROLES_HIERARCHY database table.
 * 
 */
@Embeddable
public class RolesHierarchyId extends BaseObject implements Serializable {
	private String parentRole;

	private String childRole;

	@Column(name = "PARENT_ROLE", nullable = false, length = 50)
	public String getParentRole() {
		return this.parentRole;
	}

	public void setParentRole(String parentRole) {
		this.parentRole = parentRole;
	}

	@Column(name = "CHILD_ROLE", nullable = false, length = 50)
	public String getChildRole() {
		return this.childRole;
	}

	public void setChildRole(String childRole) {
		this.childRole = childRole;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		RolesHierarchyId pojo = (RolesHierarchyId) o;

		if ((parentRole != null) ? (!parentRole.equals(pojo.parentRole)) : (pojo.parentRole != null)) {
			return false;
		}

		if ((childRole != null) ? (!childRole.equals(pojo.childRole)) : (pojo.childRole != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((parentRole != null) ? parentRole.hashCode() : 0);
		result = ((childRole != null) ? childRole.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("parentRole").append("='").append(getParentRole()).append("', ");
		sb.append("childRole").append("='").append(getChildRole()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
