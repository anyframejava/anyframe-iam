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
 * SECURED_RESOURCES Domain Object that related with SECURED_RESOURCES table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "SECURED_RESOURCES")
public class SecuredResources extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String resourceId;

	private String resourceName;

	private String resourcePattern;

	private String description;

	private String resourceType;

	private Long sortOrder;

	private String createDate;

	private String modifyDate;

	private Set<SecuredResourcesRoles> securedResourcesRoleses = new HashSet<SecuredResourcesRoles>(0);

	public SecuredResources() {

	}

	public SecuredResources(String resourceId, String resourceName, String resourcePattern, String resourceType) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourcePattern = resourcePattern;
		this.resourceType = resourceType;
	}

	@Id
	@Column(name = "RESOURCE_ID", unique = true, nullable = false, length = 10)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_NAME", length = 50)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_PATTERN", nullable = false, length = 300)
	public String getResourcePattern() {
		return this.resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "RESOURCE_TYPE", nullable = false, length = 10)
	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "SORT_ORDER", precision = 22, scale = 0)
	public Long getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "securedResources")
	public Set<SecuredResourcesRoles> getSecuredResourcesRoleses() {
		return this.securedResourcesRoleses;
	}

	public void setSecuredResourcesRoleses(Set<SecuredResourcesRoles> securedResourcesRoleses) {
		this.securedResourcesRoleses = securedResourcesRoleses;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		SecuredResources pojo = (SecuredResources) o;

		if ((resourceName != null) ? (!resourceName.equals(pojo.resourceName)) : (pojo.resourceName != null)) {
			return false;
		}

		if ((resourcePattern != null) ? (!resourcePattern.equals(pojo.resourcePattern))
				: (pojo.resourcePattern != null)) {
			return false;
		}

		if ((description != null) ? (!description.equals(pojo.description)) : (pojo.description != null)) {
			return false;
		}

		if ((resourceType != null) ? (!resourceType.equals(pojo.resourceType)) : (pojo.resourceType != null)) {
			return false;
		}

		if ((sortOrder != null) ? (!sortOrder.equals(pojo.sortOrder)) : (pojo.sortOrder != null)) {
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
		result = ((resourceName != null) ? resourceName.hashCode() : 0);
		result = (31 * result) + ((resourcePattern != null) ? resourcePattern.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);
		result = (31 * result) + ((resourceType != null) ? resourceType.hashCode() : 0);
		result = (31 * result) + ((sortOrder != null) ? sortOrder.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("resourceId").append("='").append(getResourceId()).append("', ");
		sb.append("resourceName").append("='").append(getResourceName()).append("', ");
		sb.append("resourcePattern").append("='").append(getResourcePattern()).append("', ");
		sb.append("description").append("='").append(getDescription()).append("', ");
		sb.append("resourceType").append("='").append(getResourceType()).append("', ");
		sb.append("sortOrder").append("='").append(getSortOrder()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
