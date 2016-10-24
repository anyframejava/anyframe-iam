package anyframe.iam.sample.domain;

import anyframe.core.generic.model.BaseObject;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "CATEGORY", schema = "PUBLIC")
public class Category extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String categoryNo;
    private String categoryName;
    private String categoryDesc;
    private String useYn;
    private String regId;
    private Date regDate;
    private String modifyId;
    private Date modifyDate;
    private Set<Product> products = new HashSet<Product>(0);

    @Id
    @Column(name = "CATEGORY_NO", unique = true, nullable = false, length = 16)
    public String getCategoryNo() {
        return this.categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    @Column(name = "CATEGORY_NAME", nullable = false, length = 50)
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "CATEGORY_DESC", length = 100)
    public String getCategoryDesc() {
        return this.categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Column(name = "USE_YN", length = 1)
    public String getUseYn() {
        return this.useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Column(name = "REG_ID", length = 20)
    public String getRegId() {
        return this.regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DATE", length = 0)
    public Date getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Column(name = "MODIFY_ID", length = 20)
    public String getModifyId() {
        return this.modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFY_DATE", length = 0)
    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Category pojo = (Category) o;

        if ((categoryName != null) ? (!categoryName.equals(pojo.categoryName))
                                       : (pojo.categoryName != null)) {
            return false;
        }

        if ((categoryDesc != null) ? (!categoryDesc.equals(pojo.categoryDesc))
                                       : (pojo.categoryDesc != null)) {
            return false;
        }

        if ((useYn != null) ? (!useYn.equals(pojo.useYn)) : (pojo.useYn != null)) {
            return false;
        }

        if ((regId != null) ? (!regId.equals(pojo.regId)) : (pojo.regId != null)) {
            return false;
        }

        if ((regDate != null) ? (!regDate.equals(pojo.regDate))
                                  : (pojo.regDate != null)) {
            return false;
        }

        if ((modifyId != null) ? (!modifyId.equals(pojo.modifyId))
                                   : (pojo.modifyId != null)) {
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
        result = ((categoryName != null) ? categoryName.hashCode() : 0);
        result = (31 * result) +
            ((categoryDesc != null) ? categoryDesc.hashCode() : 0);
        result = (31 * result) + ((useYn != null) ? useYn.hashCode() : 0);
        result = (31 * result) + ((regId != null) ? regId.hashCode() : 0);
        result = (31 * result) + ((regDate != null) ? regDate.hashCode() : 0);
        result = (31 * result) + ((modifyId != null) ? modifyId.hashCode() : 0);
        result = (31 * result) +
            ((modifyDate != null) ? modifyDate.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("categoryNo").append("='").append(getCategoryNo())
          .append("', ");
        sb.append("categoryName").append("='").append(getCategoryName())
          .append("', ");
        sb.append("categoryDesc").append("='").append(getCategoryDesc())
          .append("', ");
        sb.append("useYn").append("='").append(getUseYn()).append("', ");
        sb.append("regId").append("='").append(getRegId()).append("', ");
        sb.append("regDate").append("='").append(getRegDate()).append("', ");
        sb.append("modifyId").append("='").append(getModifyId()).append("', ");
        sb.append("modifyDate").append("='").append(getModifyDate())
          .append("', ");

        sb.append("]");

        return sb.toString();
    }
}
