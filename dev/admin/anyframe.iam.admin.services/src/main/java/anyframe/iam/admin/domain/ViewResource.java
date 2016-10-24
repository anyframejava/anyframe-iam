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
 * The persistent class for the VIEW_RESOURCES database table.
 * 
 */
@Entity
@Table(name = "VIEW_RESOURCES")
public class ViewResource extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VIEW_RESOURCE_ID", nullable = false, unique = true, length = 50)
	private String viewResourceId;

	@Column(name = "category", length = 255)
	private String category;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@Column(name = "VIEW_INFO", length = 255)
	private String viewInfo;

	@Column(name = "VIEW_NAME", nullable = false, length = 50)
	private String viewName;
	
	@Column(name = "VIEW_TYPE", nullable = false, length = 10)
	private String viewType;
	
	@Column(name = "VISIBLE", nullable = false, length = 1)
	private String visible;
	
	@Column(name = "SYSTEM_NAME", length = 15)
	private String systemName;

	// bi-directional many-to-one association to ViewResourcesMapping
	@OneToMany(mappedBy = "viewResource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ViewResourcesMapping> viewResourcesMappings;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "viewByParentView")
	private Set<ViewHierarchy> viewHierarchiesForParentView = new HashSet<ViewHierarchy>(0);
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "viewByChildView")
	private Set<ViewHierarchy> viewHierarchiesForChildView = new HashSet<ViewHierarchy>(0);
	
	public ViewResource() {
	}

	public String getViewResourceId() {
		return this.viewResourceId;
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

	public Set<ViewResourcesMapping> getViewResourcesMappings() {
		return this.viewResourcesMappings;
	}

	public void setViewResourcesMappings(Set<ViewResourcesMapping> viewResourcesMappings) {
		this.viewResourcesMappings = viewResourcesMappings;
	}
	
	public Set<ViewHierarchy> getViewHierarchiesForParentView() {
		return this.viewHierarchiesForParentView;
	}

	public void setViewHierarchiesForParentView(Set<ViewHierarchy> viewHierarchiesForParentView) {
		this.viewHierarchiesForParentView = viewHierarchiesForParentView;
	}
	
	public Set<ViewHierarchy> getViewHierarchiesForChildView() {
		return this.viewHierarchiesForChildView;
	}
	
	public void setViewHierarchiesForChildView(Set<ViewHierarchy> viewHierarchiesForChildView) {
		this.viewHierarchiesForChildView = viewHierarchiesForChildView;
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


	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		ViewResource pojo = (ViewResource) o;

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
		sb.append("visible").append("='").append(getVisible());

		sb.append("]");

		return sb.toString();
	}
}