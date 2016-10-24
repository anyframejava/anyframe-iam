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
 * RESTRICTED_TIMES Domain Object that related with RESTRICTED_TIMES table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "RESTRICTED_TIMES")
public class RestrictedTimes extends BaseObject implements Serializable {
	private String timeId;

	private String timeType;

	private String startDate;

	private String startTime;

	private String endDate;

	private String endTime;

	private String description;

	private Set<RestrictedTimesRoles> restrictedTimesRoleses = new HashSet<RestrictedTimesRoles>(0);

	private Set<RestrictedTimesResources> restrictedTimesResourceses = new HashSet<RestrictedTimesResources>(0);

	public RestrictedTimes() {

	}

	public RestrictedTimes(String timeId, String timeType, String startDate, String startTime, String endDate,
			String endTime, String description) {
		this.timeId = timeId;
		this.timeType = timeType;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.description = description;
	}

	@Id
	@Column(name = "TIME_ID", unique = true, nullable = false, length = 10)
	public String getTimeId() {
		return this.timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	@Column(name = "TIME_TYPE", length = 10)
	public String getTimeType() {
		return this.timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	@Column(name = "START_DATE", length = 8)
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name = "START_TIME", length = 6)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_DATE", length = 8)
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name = "END_TIME", length = 6)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "DESCRIPTION", length = 6)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restrictedTimes")
	public Set<RestrictedTimesRoles> getRestrictedTimesRoleses() {
		return this.restrictedTimesRoleses;
	}

	public void setRestrictedTimesRoleses(Set<RestrictedTimesRoles> restrictedTimesRoleses) {
		this.restrictedTimesRoleses = restrictedTimesRoleses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restrictedTimes")
	public Set<RestrictedTimesResources> getRestrictedTimesResourceses() {
		return this.restrictedTimesResourceses;
	}

	public void setRestrictedTimesResourceses(Set<RestrictedTimesResources> restrictedTimesResourceses) {
		this.restrictedTimesResourceses = restrictedTimesResourceses;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		RestrictedTimes pojo = (RestrictedTimes) o;

		if ((timeType != null) ? (!timeType.equals(pojo.timeType)) : (pojo.timeType != null)) {
			return false;
		}

		if ((startDate != null) ? (!startDate.equals(pojo.startDate)) : (pojo.startDate != null)) {
			return false;
		}

		if ((startTime != null) ? (!startTime.equals(pojo.startTime)) : (pojo.startTime != null)) {
			return false;
		}

		if ((endDate != null) ? (!endDate.equals(pojo.endDate)) : (pojo.endDate != null)) {
			return false;
		}

		if ((endTime != null) ? (!endTime.equals(pojo.endTime)) : (pojo.endTime != null)) {
			return false;
		}

		if ((description != null) ? (!description.equals(pojo.description)) : (pojo.description != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((timeType != null) ? timeType.hashCode() : 0);
		result = (31 * result) + ((startDate != null) ? startDate.hashCode() : 0);
		result = (31 * result) + ((startTime != null) ? startTime.hashCode() : 0);
		result = (31 * result) + ((endDate != null) ? endDate.hashCode() : 0);
		result = (31 * result) + ((endTime != null) ? endTime.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("timeId").append("='").append(getTimeId()).append("', ");
		sb.append("timeType").append("='").append(getTimeType()).append("', ");
		sb.append("startDate").append("='").append(getStartDate()).append("', ");
		sb.append("startTime").append("='").append(getStartTime()).append("', ");
		sb.append("endDate").append("='").append(getEndDate()).append("', ");
		sb.append("endTime").append("='").append(getEndTime()).append("', ");
		sb.append("description").append("='").append(getDescription()).append("', ");

		sb.append("]");

		return sb.toString();
	}
}
