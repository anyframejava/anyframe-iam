package org.anyframe.iam.sample.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "PRODUCT", schema = "PUBLIC")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String prodNo;
    private Category category;
    private String prodName;
    private String sellerId;
    private String prodDetail;
    private String manufactureDay;
    private String asYn;
    private Long sellQuantity;
    private Long sellAmount;
    private String imageFile;
    private Date regDate;

    @Id
    @Column(name = "PROD_NO", unique = true, nullable = false, length = 16)
    public String getProdNo() {
        return this.prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_NO")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "PROD_NAME", nullable = false, length = 100)
    public String getProdName() {
        return this.prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Column(name = "SELLER_ID", length = 20)
    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Column(name = "PROD_DETAIL", length = 200)
    public String getProdDetail() {
        return this.prodDetail;
    }

    public void setProdDetail(String prodDetail) {
        this.prodDetail = prodDetail;
    }

    @Column(name = "MANUFACTURE_DAY", length = 8)
    public String getManufactureDay() {
        return this.manufactureDay;
    }

    public void setManufactureDay(String manufactureDay) {
        this.manufactureDay = manufactureDay;
    }

    @Column(name = "AS_YN", length = 1)
    public String getAsYn() {
        return this.asYn;
    }

    public void setAsYn(String asYn) {
        this.asYn = asYn;
    }

    @Column(name = "SELL_QUANTITY", precision = 5, scale = 0)
    public Long getSellQuantity() {
        return this.sellQuantity;
    }

    public void setSellQuantity(Long sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    @Column(name = "SELL_AMOUNT", precision = 10, scale = 0)
    public Long getSellAmount() {
        return this.sellAmount;
    }

    public void setSellAmount(Long sellAmount) {
        this.sellAmount = sellAmount;
    }

    @Column(name = "IMAGE_FILE", length = 100)
    public String getImageFile() {
        return this.imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DATE", length = 0)
    public Date getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        Product pojo = (Product) o;

        if ((prodName != null) ? (!prodName.equals(pojo.prodName))
                                   : (pojo.prodName != null)) {
            return false;
        }

        if ((sellerId != null) ? (!sellerId.equals(pojo.sellerId))
                                   : (pojo.sellerId != null)) {
            return false;
        }

        if ((prodDetail != null) ? (!prodDetail.equals(pojo.prodDetail))
                                     : (pojo.prodDetail != null)) {
            return false;
        }

        if ((manufactureDay != null)
                ? (!manufactureDay.equals(pojo.manufactureDay))
                    : (pojo.manufactureDay != null)) {
            return false;
        }

        if ((asYn != null) ? (!asYn.equals(pojo.asYn)) : (pojo.asYn != null)) {
            return false;
        }

        if ((sellQuantity != null) ? (!sellQuantity.equals(pojo.sellQuantity))
                                       : (pojo.sellQuantity != null)) {
            return false;
        }

        if ((sellAmount != null) ? (!sellAmount.equals(pojo.sellAmount))
                                     : (pojo.sellAmount != null)) {
            return false;
        }

        if ((imageFile != null) ? (!imageFile.equals(pojo.imageFile))
                                    : (pojo.imageFile != null)) {
            return false;
        }

        if ((regDate != null) ? (!regDate.equals(pojo.regDate))
                                  : (pojo.regDate != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (31 * result) + ((prodName != null) ? prodName.hashCode() : 0);
        result = (31 * result) + ((sellerId != null) ? sellerId.hashCode() : 0);
        result = (31 * result) +
            ((prodDetail != null) ? prodDetail.hashCode() : 0);
        result = (31 * result) +
            ((manufactureDay != null) ? manufactureDay.hashCode() : 0);
        result = (31 * result) + ((asYn != null) ? asYn.hashCode() : 0);
        result = (31 * result) +
            ((sellQuantity != null) ? sellQuantity.hashCode() : 0);
        result = (31 * result) +
            ((sellAmount != null) ? sellAmount.hashCode() : 0);
        result = (31 * result) +
            ((imageFile != null) ? imageFile.hashCode() : 0);
        result = (31 * result) + ((regDate != null) ? regDate.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());

        sb.append(" [");
        sb.append("prodNo").append("='").append(getProdNo()).append("', ");

        sb.append("prodName").append("='").append(getProdName()).append("', ");
        sb.append("sellerId").append("='").append(getSellerId()).append("', ");
        sb.append("prodDetail").append("='").append(getProdDetail())
          .append("', ");
        sb.append("manufactureDay").append("='").append(getManufactureDay())
          .append("', ");
        sb.append("asYn").append("='").append(getAsYn()).append("', ");
        sb.append("sellQuantity").append("='").append(getSellQuantity())
          .append("', ");
        sb.append("sellAmount").append("='").append(getSellAmount())
          .append("', ");
        sb.append("imageFile").append("='").append(getImageFile()).append("', ");
        sb.append("regDate").append("='").append(getRegDate()).append("'");
        sb.append("]");

        return sb.toString();
    }
}
