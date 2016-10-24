package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VIEW_RESOURCES")
public class TempViewResources implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "compKey", unique = true, nullable = false, length = 250)
	private String compKey;
	
	public String getCompKey() {
		return viewResourceId + refId;
	}

	public void setCompKey(String compKey) {
		this.compKey = viewResourceId + refId;
	}
	
	@Column(name = "viewResourceId", nullable = false, unique = true, length = 50)
	private String viewResourceId;

	@Column(name = "category", length = 255)
	private String category;
	
	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@Column(name = "viewInfo", length = 255)
	private String viewInfo;

	@Column(name = "viewName", nullable = false, length = 50)
	private String viewName;

	@Column(name = "viewType", nullable = false, length = 10)
	private String viewType;

	@Column(name = "visible", nullable = false, length = 1)
	private String visible;

	@Column(name = "systemName", length = 15)
	private String systemName;

	@Column(name = "parentView", length = 50)
	private String parentView;
	
	@Column(name = "refId", length = 200)
	private String refId;
	
	@Column(name = "refType", length = 1)
	private String refType;
	
	@Column(name = "permissions", length = 255)
	private String permissions;

	public TempViewResources(){
		
	}

	public String getViewResourceId() {
		return viewResourceId;
	}
	
	public void setViewResourceId(String viewResourceId) {
		this.viewResourceId = viewResourceId;
	}
	
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getViewInfo() {
		return this.viewInfo;
	}

	public void setViewInfo(String viewInfo) {
		this.viewInfo = viewInfo;
	}

	public String getViewName() {
		return this.viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public String getParentView() {
		return parentView;
	}

	public void setParentView(String parentView) {
		this.parentView = parentView;
	}
	
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TempViewResources pojo = (TempViewResources) o;

		if ((viewName != null) ? (!viewName.equals(pojo.viewName)) : (pojo.viewName != null)) {
			return false;
		}

		if ((description != null) ? (!description.equals(pojo.description)) : (pojo.description != null)) {
			return false;
		}

		if ((viewResourceId != null) ? (!viewResourceId.equals(pojo.viewResourceId)) : (pojo.viewResourceId != null)) {
			return false;
		}

		if ((category != null) ? (!category.equals(pojo.category)) : (pojo.category != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((viewResourceId != null) ? viewResourceId.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);
		result = (31 * result) + ((viewName != null) ? viewName.hashCode() : 0);
		result = (31 * result) + ((category != null) ? category.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("viewResourceId").append("='").append(getViewResourceId()).append("', ");
		sb.append("viewName").append("='").append(getViewName()).append("', ");
		sb.append("description").append("='").append(getDescription()).append("', ");
		sb.append("category").append("='").append(getCategory()).append("', ");
		sb.append("viewInfo").append("='").append(getViewInfo()).append("', ");
		sb.append("viewType").append("='").append(getViewType()).append("', ");
		sb.append("visible").append("='").append(getVisible()).append("', ");
		sb.append("parentView").append("='").append(getParentView()).append("', ");
		sb.append("systemName").append("='").append(getSystemName()).append("', ");
		sb.append("refId").append("='").append(getRefId()).append("', ");
		sb.append("refType").append("='").append(getRefType()).append("', ");
		sb.append("permissions").append("='").append(getPermissions());

		sb.append("]");

		return sb.toString();
	}
	
	
}
