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
 * The primary key class for the VIEW_RESOURCES_MAPPING database table.
 * 
 */
@Embeddable
public class ViewResourcesMappingPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "VIEW_RESOURCE_ID", nullable = false, length = 50)
	private String viewResourceId;

	@Column(name = "REF_ID", nullable = false, length = 50)
	private String refId;

	public ViewResourcesMappingPK() {
	}

	public String getViewResourceId() {
		return this.viewResourceId;
	}

	public void setViewResourceId(String viewResourceId) {
		this.viewResourceId = viewResourceId;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ViewResourcesMappingPK)) {
			return false;
		}
		ViewResourcesMappingPK castOther = (ViewResourcesMappingPK) other;
		return this.viewResourceId.equals(castOther.viewResourceId) && this.refId.equals(castOther.refId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.viewResourceId.hashCode();
		hash = hash * prime + this.refId.hashCode();

		return hash;
	}
}