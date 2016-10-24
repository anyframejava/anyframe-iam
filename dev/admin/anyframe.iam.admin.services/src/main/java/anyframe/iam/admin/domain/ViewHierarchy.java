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

@Entity
@Table(name = "VIEW_HIERARCHY")
public class ViewHierarchy extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private ViewHierarchyId id;

	private ViewResource viewByChildView;

	private ViewResource viewByParentView;

	private String createDate;

	private String modifyDate;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "parentView", column = @Column(name = "PARENT_VIEW", nullable = false, length = 50)),
			@AttributeOverride(name = "childView", column = @Column(name = "CHILD_VIEW", nullable = false, length = 50)) })
	public ViewHierarchyId getId() {
		return this.id;
	}

	public void setId(ViewHierarchyId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHILD_VIEW", nullable = false, insertable = false, updatable = false)
	public ViewResource getViewByChildView() {
		return this.viewByChildView;
	}

	public void setViewByChildView(ViewResource viewByChildView) {
		this.viewByChildView = viewByChildView;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_VIEW", nullable = false, insertable = false, updatable = false)
	public ViewResource getViewByParentView() {
		return this.viewByParentView;
	}

	public void setViewByParentView(ViewResource viewByParentView) {
		this.viewByParentView = viewByParentView;
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

		ViewHierarchy pojo = (ViewHierarchy) o;

		if ((viewByChildView != null) ? (!viewByChildView
				.equals(pojo.viewByChildView)) : (pojo.viewByChildView != null)) {
			return false;
		}

		if ((viewByParentView != null) ? (!viewByParentView
				.equals(pojo.viewByParentView))
				: (pojo.viewByParentView != null)) {
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
		result = (31 * result)
				+ ((viewByChildView != null) ? viewByChildView.hashCode() : 0);
		result = (31 * result)
				+ ((viewByParentView != null) ? viewByParentView.hashCode() : 0);
		result = (31 * result)
				+ ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result)
				+ ((modifyDate != null) ? modifyDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");

		sb.append("viewByChildView").append("='").append(getViewByChildView())
				.append("', ");
		sb.append("viewByParentView").append("='")
				.append(getViewByParentView()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append(
				"', ");
		sb.append("modifyDate").append("='").append(getModifyDate())
				.append("'");
		sb.append("]");

		return sb.toString();
	}

}
