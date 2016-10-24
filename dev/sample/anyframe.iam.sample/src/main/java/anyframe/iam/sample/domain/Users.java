package anyframe.iam.sample.domain;

import anyframe.core.generic.model.BaseObject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USERS", schema = "PUBLIC")
public class Users extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String userId;
    private String userName;
    private String password;
    private String enabled;
    private String createDate;
    private String modifyDate;

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false, length = 20)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "USER_NAME", nullable = false, length = 50)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "PASSWORD", nullable = false, length = 50)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "ENABLED", length = 1)
    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Column(name = "CREATE_DATE", length = 8)
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

        Users pojo = (Users) o;

        if ((userName != null) ? (!userName.equals(pojo.userName))
                                   : (pojo.userName != null)) {
            return false;
        }

        if ((password != null) ? (!password.equals(pojo.password))
                                   : (pojo.password != null)) {
            return false;
        }

        if ((enabled != null) ? (!enabled.equals(pojo.enabled))
                                  : (pojo.enabled != null)) {
            return false;
        }

        if ((createDate != null) ? (!createDate.equals(pojo.createDate))
                                     : (pojo.createDate != null)) {
            return false;
        }

        if ((modifyDate != null) ? (!modifyDate.equals(pojo.modifyDate))
                                     : (pojo.modifyDate != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ((userName != null) ? userName.hashCode() : 0);
        result = (31 * result) + ((password != null) ? password.hashCode() : 0);
        result = (31 * result) + ((enabled != null) ? enabled.hashCode() : 0);
        result = (31 * result) +
            ((createDate != null) ? createDate.hashCode() : 0);
        result = (31 * result) +
            ((modifyDate != null) ? modifyDate.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("userId").append("='").append(getUserId()).append("', ");
        sb.append("userName").append("='").append(getUserName()).append("', ");
        sb.append("password").append("='").append(getPassword()).append("', ");
        sb.append("enabled").append("='").append(getEnabled()).append("', ");
        sb.append("createDate").append("='").append(getCreateDate())
          .append("', ");
        sb.append("modifyDate").append("='").append(getModifyDate()).append("'");
        sb.append("]");

        return sb.toString();
    }
}
