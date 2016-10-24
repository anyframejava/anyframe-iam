package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RESTRICTED_TIMES")
public class TempRestrictedTimes implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "compKey", unique = true, nullable = false, length = 210)
	@SuppressWarnings("unused")
	private String compKey;
	
	public String getCompKey() {
		return timeId + roleId;
	}

	public void setCompKey(String compKey) {
		this.compKey = timeId + roleId;
	}

	@Column(name = "timeId", unique = true, nullable = false, length = 10)
	private String timeId;
	
	@Column(name = "timeType", length = 10)
	private String timeType;

	@Column(name = "startDate", length = 8)
	private String startDate;

	@Column(name = "startTime", length = 6)
	private String startTime;

	@Column(name = "endDate", length = 8)
	private String endDate;

	@Column(name = "endTime", length = 6)
	private String endTime;

	@Column(name = "description", length = 6)
	private String description;

	@Column(name = "systemName", length = 15)
	private String systemName;
	
	@Column(name = "roleId", length = 200)
	private String roleId;
	
	@Column(name = "resourceId", length = 10)
	private String resourceId;
	
	@Column(name = "timesExclusionRoles", length = 200)
	private String timesExclusionRoles;
	
	public TempRestrictedTimes() {

	}
	
	public String getTimeId() {
		return this.timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}
	
	public String getTimeType() {
		return this.timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSystemName(){
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getTimesExclusionRoles() {
		return timesExclusionRoles;
	}

	public void setTimesExclusionRoles(String timesExclusionRoles) {
		this.timesExclusionRoles = timesExclusionRoles;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TempRestrictedTimes pojo = (TempRestrictedTimes) o;

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
		sb.append("systemName").append("='").append(getSystemName()).append("', ");
		sb.append("roleId").append("='").append(getRoleId()).append("', ");
		sb.append("resourceId").append("='").append(getResourceId()).append("', ");
		sb.append("timesExclusionRoles").append("='").append(getTimesExclusionRoles());

		sb.append("]");

		return sb.toString();
	}

}
