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

import anyframe.core.generic.model.BaseObject;

/**
 * Authorities Domain Object that related with Authorities table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "AUTHORITIES")
public class Authorities extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private AuthoritiesId id;

	private Roles roles;

	private String type;

	private String createDate;

	private String modifyDate;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 50)),
			@AttributeOverride(name = "subjectId", column = @Column(name = "SUBJECT_ID", nullable = false, length = 20)) })
	public AuthoritiesId getId() {
		return this.id;
	}

	public void setId(AuthoritiesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@Column(name = "TYPE", nullable = false, length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Authorities pojo = (Authorities) o;

		if ((type != null) ? (!type.equals(pojo.type)) : (pojo.type != null)) {
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
		result = (31 * result) + ((type != null) ? type.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");

		sb.append("type").append("='").append(getType()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
