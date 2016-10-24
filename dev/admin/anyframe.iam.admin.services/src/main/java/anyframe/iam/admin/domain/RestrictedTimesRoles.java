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
 * RESTRICTED_TIMES_ROLES Domain Object that related with RESTRICTED_TIMES_ROLES
 * table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "RESTRICTED_TIMES_ROLES")
public class RestrictedTimesRoles extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private RestrictedTimesRolesId id;

	private RestrictedTimes restrictedTimes;

	private Roles roles;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "timeId", column = @Column(name = "TIME_ID", nullable = false, length = 10)),
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 50)) })
	public RestrictedTimesRolesId getId() {
		return this.id;
	}

	public void setId(RestrictedTimesRolesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIME_ID", nullable = false, insertable = false, updatable = false)
	public RestrictedTimes getRestrictedTimes() {
		return this.restrictedTimes;
	}

	public void setRestrictedTimes(RestrictedTimes restrictedTimes) {
		this.restrictedTimes = restrictedTimes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		RestrictedTimesRoles pojo = (RestrictedTimesRoles) o;
		
		if((id != null) ? (!id.equals(pojo.id)) : (pojo.id != null)) {
			return false;
		}
		
		if((restrictedTimes != null) ? (!restrictedTimes.equals(pojo.restrictedTimes)) : (pojo.restrictedTimes != null)) {
			return false;
		}
		
		if((roles != null) ? (!roles.equals(pojo.roles)) : (pojo.roles != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((id != null) ? id.hashCode() : 0);
		result = (31 * result) + ((restrictedTimes != null) ? restrictedTimes.hashCode() : 0);
		result = (31 * result) + ((roles != null) ? roles.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");
		sb.append("restrictedTimes").append("='").append(getRestrictedTimes()).append("', ");
		sb.append("roles").append("='").append(getRoles()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
