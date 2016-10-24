package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

@Entity
@Table(name = "DATA_UPLOAD")
public class DataUpload extends BaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "FILE_ID", nullable = false, unique = true, length = 10)
	private String fileId;
	
	@Column(name = "PATH", length = 255, nullable = false)
	private String path;
	
	@Column(name = "FILE_NAME", length = 255, nullable = false)
	private String fileName;

	@Column(name = "CREATE_DATE", length = 8, nullable = false)
	private String createDate;
	
	@Column(name = "WORK_DATE", length = 8)
	private String workDate;
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		DataUpload pojo = (DataUpload) o;

		if ((fileId != null) ? (!fileId.equals(pojo.fileId)) : (pojo.fileId != null)) {
			return false;
		}

		if ((path != null) ? (!path.equals(pojo.path)) : (pojo.path != null)) {
			return false;
		}

		if ((fileName != null) ? (!fileName.equals(pojo.fileName)) : (pojo.fileName != null)) {
			return false;
		}

		if ((createDate != null) ? (!createDate.equals(pojo.createDate)) : (pojo.createDate != null)) {
			return false;
		}
		
		if ((workDate != null) ? (!workDate.equals(pojo.workDate)) : (pojo.workDate != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((fileId != null) ? fileId.hashCode() : 0);
		result = (31 * result) + ((path != null) ? path.hashCode() : 0);
		result = (31 * result) + ((fileName != null) ? fileName.hashCode() : 0);
		result = (31 * result) + ((createDate != null) ? createDate.hashCode() : 0);
		result = (31 * result) + ((workDate != null) ? workDate.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("fileId").append("='").append(getFileId()).append("', ");
		sb.append("path").append("='").append(getPath()).append("', ");
		sb.append("fileName").append("='").append(getFileName()).append("', ");
		sb.append("createDate").append("='").append(getCreateDate()).append("', ");
		sb.append("workDate").append("='").append(getWorkDate());

		sb.append("]");

		return sb.toString();
	}

}
