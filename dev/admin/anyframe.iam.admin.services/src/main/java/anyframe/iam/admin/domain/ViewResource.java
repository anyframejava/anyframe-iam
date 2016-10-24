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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the VIEW_RESOURCES database table.
 * 
 */
@Entity
@Table(name = "VIEW_RESOURCES")
public class ViewResource implements Serializable {
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

	// bi-directional many-to-one association to ViewResourcesMapping
	@OneToMany(mappedBy = "viewResource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ViewResourcesMapping> viewResourcesMappings;

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

}