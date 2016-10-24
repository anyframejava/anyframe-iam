package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

@Entity
@Table(name = "ROLES")
public class TempRoles  extends BaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "roleId", unique = true, nullable = false, length = 50)
	private String roleId;

	@Column(name = "roleName", length = 50)
	private String roleName;
	
	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "createDate", length = 8, updatable = false)
	private String createDate;

	@Column(name = "modifyDate", length = 8)
	private String modifyDate;

	@Column(name = "parentRoles", length = 50)
	private String parentRoles;
	
	public TempRoles(){
		
	}

	public String getParentRoles() {
		return parentRoles;
	}

	public void setParentRoles(String parentRoles) {
		this.parentRoles = parentRoles;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		TempRoles pojo = (TempRoles) o;

		if ((roleName != null) ? (!roleName.equals(pojo.roleName)) : (pojo.roleName != null)) {
			return false;
		}

		if ((description != null) ? (!description.equals(pojo.description)) : (pojo.description != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((roleName != null) ? roleName.hashCode() : 0);
		result = (31 * result) + ((description != null) ? description.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("roleId").append("='").append(getRoleId()).append("', ");
		sb.append("roleName").append("='").append(getRoleName()).append("', ");
		sb.append("description").append("='").append(getDescription()).append("', ");
		sb.append("parentRole").append("='").append(getParentRoles());

		sb.append("]");

		return sb.toString();
	}
}
