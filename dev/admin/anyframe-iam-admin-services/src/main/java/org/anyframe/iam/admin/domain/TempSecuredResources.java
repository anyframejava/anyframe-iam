package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SECURED_RESOURCES")
public class TempSecuredResources implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "compKey", unique = true, nullable = false, length = 210)
	@SuppressWarnings("unused")
	private String compKey;
	
	public String getCompKey() {
		return resourceId + roleId;
	}

	public void setCompKey(String compKey) {
		this.compKey = resourceId + roleId;
	}
	
	@Column(name = "resourceId", unique = true, nullable = false, length = 10)
	private String resourceId;
	
	@Column(name = "resourceName", length = 50)
	private String resourceName;

	@Column(name = "resourcePattern", nullable = false, length = 300)
	private String resourcePattern;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "resourceType", nullable = false, length = 10)
	private String resourceType;

	@Column(name = "sortOrder", precision = 22, scale = 0)
	private String sortOrder;

	@Column(name = "systemName", length = 15)
	private String systemName;	

	@Column(name = "roleId", length = 200)
	private String roleId;

	@Column(name = "createDate", length = 8, updatable = false)
	private String createDate;

	@Column(name = "modifyDate", length = 8)
	private String modifyDate;

	public TempSecuredResources() {

	}
	
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourcePattern() {
		return this.resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSystemName() {
		return this.systemName;
	}
	
	public void setSystemName(String systemName){
		this.systemName = systemName;
	}
	
	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

		TempSecuredResources pojo = (TempSecuredResources) o;

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

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((resourceName != null) ? resourceName.hashCode() : 0);
		result = (31 * result) + ((resourcePattern != null) ? resourcePattern.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);
		result = (31 * result) + ((resourceType != null) ? resourceType.hashCode() : 0);
		result = (31 * result) + ((sortOrder != null) ? sortOrder.hashCode() : 0);

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
		sb.append("roleId").append("='").append(getRoleId()).append("', ");
		sb.append("A.SYSTEM_NAME").append("='").append(getSystemName());

		sb.append("]");

		return sb.toString();
	}

}
