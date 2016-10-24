package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

@Entity
@Table(name = "GROUPS")
public class TempGroups extends BaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "groupId", unique = true, nullable = false, length = 20)
	private String groupId;

	@Column(name = "groupName", nullable = false, length = 50)
	private String groupName;

	@Column(name = "parentGroup", length = 20)
	private String parentGroup;

	@Column(name = "createDate", length = 8, updatable = false)
	private String createDate;

	@Column(name = "modifyDate", length = 8)
	private String modifyDate;
	
	public TempGroups(){
		
	}
	
	public String getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(String parentGroup) {
		this.parentGroup = parentGroup;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;	
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TempGroups pojo = (TempGroups) o;

		if ((groupName != null) ? (!groupName.equals(pojo.groupName)) : (pojo.groupName != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((groupName != null) ? groupName.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("groupId").append("='").append(getGroupId()).append("', ");
		sb.append("groupName").append("='").append(getGroupName()).append("', ");
		sb.append("parentGroup").append("='").append(getParentGroup()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("modifyDate").append("='").append(getModifyDate());

		sb.append("]");

		return sb.toString();
	}
}
