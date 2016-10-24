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
import javax.persistence.Embeddable;

/**
 * The primary key class for the SECURED_RESOURCES_ROLES database table.
 * 
 */
@Embeddable
public class SecuredResourcesRolesId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String resourceId;

	private String roleId;

	@Column(name = "RESOURCE_ID", nullable = false, length = 10)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		SecuredResourcesRolesId pojo = (SecuredResourcesRolesId) o;

		if ((resourceId != null) ? (!resourceId.equals(pojo.resourceId)) : (pojo.resourceId != null)) {
			return false;
		}

		if ((roleId != null) ? (!roleId.equals(pojo.roleId)) : (pojo.roleId != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((resourceId != null) ? resourceId.hashCode() : 0);
		result = ((roleId != null) ? roleId.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("resourceId").append("='").append(getResourceId()).append("', ");
		sb.append("roleId").append("='").append(getRoleId()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
