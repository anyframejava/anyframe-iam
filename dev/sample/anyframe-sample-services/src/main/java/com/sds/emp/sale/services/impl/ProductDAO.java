package com.sds.emp.sale.services.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * This class contains IQueryService to get
 * IQueryService services and IPropertiesService to get
 * IPropertiesService services , addCategory,
 * updateCategory, getCategory, getCategoryList, and
 * getDropDownCategoryList methods to do different
 * functionality on Product.
 * @author gangab
 */

public class ProductDAO {
    /**
     * This queryService is used to get QueryService
     * services
     */
    private IQueryService queryService;

    /** an instance variable for the propertiesService. */
    private IPropertiesService propertiesService;
    
    /**
     * Sets the queryService to use.
     * @param queryService
     *        The queryService to set
     */
    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }
    
    /**
     * Sets the propertiesService to use.
     * @param propertiesService
     *        The propertiesService to set
     */
    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * This method is used to generate number of
     * products with specified categoryNo
     * @param categoryNo
     *        CategoryNo parameter is used to get the
     *        specified categoy number details
     * @return int
     * @throws Exception
     *         Exception will raise, when
     *         countProductListByCategory failed
     */
    public int countProductListByCategory(String categoryNo) throws Exception {

        Collection countCollection =
            queryService.find("countProductListByCategory",
                new Object[] {categoryNo });

        Iterator countItr = countCollection.iterator();
        if (countItr.hasNext()) {
            Map countMap = (Map) countItr.next();
            int count = ((Integer) countMap.get("ttotal")).intValue();
            return count;
        }
        return 0;
    }

    /**
     * This method is used to add product with prodNo,
     * productName, sellerId,
     * categoryNo,ProductDetail,manufactureDay,asYn,
     * sellQuantity,sellAmount,and imageFile details
     * @param productVO
     *        productVO is used to get the detials
     * @throws Exception
     *         Exception will raise, when addProduct
     *         failed
     */
    public void addProduct(ProductVO productVO) throws Exception {
        String prodNo = productVO.getProdNo();
        String prodName = productVO.getProdName();
        String sellerId = productVO.getSellerId();
        String categoryNo = productVO.getCategoryNo();
        String prodDetail = productVO.getProdDetail();
        String manufactureDay = productVO.getManufactureDay();
        String asYn = productVO.getAsYn();
        java.math.BigDecimal sellQuantity = productVO.getSellQuantity();
        java.math.BigDecimal sellAmount = productVO.getSellAmount();
        String imageFile = productVO.getImageFile();

        // This create method creates the row in the
        // table with prodNo,
        // prodName,sellerId, categoryNo, prodDetail,
        // manufactureDay,
        // asYn,sellQuantity, sellAmount, imageFile
        // data

        queryService.create("addProduct", new Object[] {prodNo, prodName,
            sellerId, categoryNo, prodDetail, manufactureDay, asYn,
            sellQuantity, sellAmount, imageFile });

    }

    /**
     * This updateCategory method is used to update the
     * product data
     * @param productVO
     *        productVO is used to get the details
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */

    public void updateProduct(ProductVO productVO) throws Exception {
        String prodNo = productVO.getProdNo();
        String prodName = productVO.getProdName();
        String categoryNo = productVO.getCategoryNo();
        String prodDetail = productVO.getProdDetail();
        String manufactureDay = productVO.getManufactureDay();
        String asYn = productVO.getAsYn();
        java.math.BigDecimal sellQuantity = productVO.getSellQuantity();
        java.math.BigDecimal sellAmount = productVO.getSellAmount();

        // This update method is used for to update the
        // modified product data

        queryService.update("updateProduct", new Object[] {prodName,
            categoryNo, prodDetail, manufactureDay, asYn, sellQuantity,
            sellAmount, prodNo });

    }

    /**
     * This getProduct method is used for to the
     * Product deatails of specified ProductNo
     * @param productNo
     *        productNo is used to get the details of
     *        specified Product Number
     * @return ProductVO productVO contains the
     *         specified product number details
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */
    public ProductVO getProduct(String productNo) throws Exception {

        Collection productCollection =
            queryService.find("getProduct", new Object[] {productNo });

        Iterator productItr = productCollection.iterator();
        if (productItr.hasNext()) {
            return (ProductVO) productItr.next();
        }

        return null;
    }

    /**
     * This method is used to get search list of
     * product details of purchase.
     * @param searchVO
     *        contains the search list of product
     *        details
     * @return Page Contains search list of product
     *         details
     * @throws Exception
     *         If it is unable to get Product Search
     *         details of purchase
     */
    public Page getProductList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = propertiesService.getInt("PAGE_SIZE");
        int pageUnit = propertiesService.getInt("PAGE_UNIT");

        Object[] iVal = new Object[2];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "prodNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "prodName=%" + searchKeyword + "%";
        }

        if (searchVO.getSearchAsYn() != null
            && !"A".equals(searchVO.getSearchAsYn()))
            iVal[1] = "asYn=" + searchVO.getSearchAsYn();

        Map productSearchListMap = null;

        productSearchListMap =
            queryService.findWithRowCount("getProductList", iVal,
                pageIndex, pageSize);

        List resultList = (List) productSearchListMap.get(IQueryService.LIST);
        int totalSize =
            ((Long) productSearchListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);

        return resultPage;
    }
}
