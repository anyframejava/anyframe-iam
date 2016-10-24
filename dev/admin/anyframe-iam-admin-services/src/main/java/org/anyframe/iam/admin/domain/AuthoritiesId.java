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
 * The primary key class for the Authorities database table.
 * 
 */
@Embeddable
public class AuthoritiesId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String roleId;

	private String subjectId;

	@Column(name = "ROLE_ID", nullable = false, length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "SUBJECT_ID", nullable = false, length = 20)
	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		AuthoritiesId pojo = (AuthoritiesId) o;

		if ((roleId != null) ? (!roleId.equals(pojo.roleId)) : (pojo.roleId != null)) {
			return false;
		}

		if ((subjectId != null) ? (!subjectId.equals(pojo.subjectId)) : (pojo.subjectId != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((roleId != null) ? roleId.hashCode() : 0);
		result = ((subjectId != null) ? subjectId.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("roleId").append("='").append(getRoleId()).append("', ");
		sb.append("subjectId").append("='").append(getSubjectId()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
