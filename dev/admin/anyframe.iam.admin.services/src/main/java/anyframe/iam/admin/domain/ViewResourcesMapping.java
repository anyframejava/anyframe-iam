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

/**
 * The persistent class for the VIEW_RESOURCES_MAPPING database table.
 * 
 */
@Entity
@Table(name = "VIEW_RESOURCES_MAPPING")
public class ViewResourcesMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	private ViewResourcesMappingPK id;

	private Integer mask;

	private String permissions;

	private String refType;

	private ViewResource viewResource;

	public ViewResourcesMapping() {
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "viewResourceId", column = @Column(name = "VIEW_RESOURCE_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "refId", column = @Column(name = "REF_ID", nullable = false, length = 50)) })
	public ViewResourcesMappingPK getId() {
		return this.id;
	}

	@Column(name = "REF_TYPE")
	public String getRefType() {
		return this.refType;
	}

	// bi-directional many-to-one association to ViewResource
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VIEW_RESOURCE_ID", nullable = false, insertable = false, updatable = false)
	public ViewResource getViewResource() {
		return this.viewResource;
	}

	@Column(name = "PERMISSIONS", nullable = false, length = 255)
	public String getPermissions() {
		return this.permissions;
	}

	@Column(name = "MASK", nullable = false)
	public Integer getMask() {
		return this.mask;
	}

	public void setId(ViewResourcesMappingPK id) {
		this.id = id;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public void setViewResource(ViewResource viewResource) {
		this.viewResource = viewResource;
	}

}