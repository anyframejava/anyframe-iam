package com.sds.emp.sale.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.sale.services.SaleVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * This DAO class contains IQueryService to get
 * IQueryService services and IPropertiesService to get
 * IPropertiesService services ,
 * updateTranStatusCode,getSale and getSaleList methods
 * for different functionality on Sale
 * @author gangab
 */

public class SaleDAO {
    /**
     * This queryService is used to get QueryService
     * services
     */

    private IQueryService queryService;

    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * This propertiesService is used to get
     * PropertiesService services
     */

    private IPropertiesService propertiesService;

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * This updateTranStatusCode method is used to
     * update the TranStatusCode
     * @param saleVO
     *        saleVO is used to access setter and
     *        getter methods of SaleVO
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public void updateTranStatusCode(SaleVO saleVO) throws Exception {
        String tranStatusCode = saleVO.getTranStatusCode();
        String prodNo = saleVO.getProdNo();
        // This update method is used for to update the
        // modified TranStatusCode
        // data

        // queryService.update("updateProductTranStatusCode",
        // new Object[] {
        // tranStatusCode, prodNo });

        queryService.update("updateTransactionTranStatusCode", new Object[] {
            tranStatusCode, prodNo });

    }

    /**
     * This getSale method is used for to the Category
     * deatails of specified product Number
     * @param prodNo
     *        Which is used to get sales details
     * @return SaleVO Object contains sales details
     * @throws Exception
     *         Exception will raise, when getSale
     *         failed
     */

    public SaleVO getSale(String prodNo) throws Exception {
        // This find method is used to get the sale
        // with specified Product
        // Number

        Collection saleCollection =
            queryService.find("getSale", new Object[] {prodNo });

        Iterator saleItr = saleCollection.iterator();
        if (saleItr.hasNext()) {
            return (SaleVO) saleItr.next();
        }

        return null;
    }

    /**
     * This getSaleList method is used to get list of
     * Sale with propertiesService
     * @param searchVO
     *        Used to get list of sales values
     * @return page Contanls list of sales details
     * @throws Exception
     *         Exception will raise, when getSaleList
     *         is failed
     */
    public Page getSaleList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = propertiesService.getInt("PAGE_SIZE");
        int pageUnit = propertiesService.getInt("PAGE_UNIT");

        Object[] iVal = new Object[3];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "prodNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "prodName=%" + searchKeyword + "%";
        }

        if (searchVO.getSearchSellerId() != null
            && !"".equals(searchVO.getSearchSellerId()))
            iVal[1] = "sellerId=" + searchVO.getSearchSellerId();

        if (searchVO.getSearchCategoryNo() != null
            && !"".equals(searchVO.getSearchCategoryNo())
            && !"A".equals(searchVO.getSearchCategoryNo()))
            iVal[2] = "categoryNo=" + searchVO.getSearchCategoryNo();

        Map saleListMap =
            queryService.findWithRowCount("getSaleList", iVal, pageIndex,
                pageSize);

        List resultList = (List) saleListMap.get(IQueryService.LIST);
        int totalSize =
            ((Long) saleListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);

        return resultPage;
    }

    /**
     * This getSaleListAll method is used to get list
     * of Sale
     * @param searchVO
     *        Used to get list of sales values
     * @return Collection Contanls list of sales details
     * @throws Exception
     *         Exception will raise, when getSaleList
     *         is failed
     */
    public Collection getSaleListAll(SearchVO searchVO) throws Exception {

        Object[] iVal = new Object[3];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "prodNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "prodName=%" + searchKeyword + "%";
        }

        if (searchVO.getSearchCategoryNo() != null
            && !"".equals(searchVO.getSearchCategoryNo()))
            iVal[1] = "categoryNo=" + searchVO.getSearchCategoryNo();

        if (searchVO.getSearchSellerId() != null
            && !"".equals(searchVO.getSearchSellerId()))
            iVal[2] = "sellerId=" + searchVO.getSearchSellerId();

        Collection saleList = queryService.find("getSaleList", iVal);

        return saleList;
    }

    /**
     * This getAjaxSaleListAll method is used to get list of Sale.
     * 
     * @param searchVO
     *            Used to get list of sales values
     * @return List Contanls list of sales details
     * @throws Exception
     *             Exception will raise, when getSaleList is failed
     */
    public Collection getAjaxSaleListAll(SearchVO searchVO) throws Exception {
            String searchCondition = EmpUtil
                            .null2str(searchVO.getSearchCondition());
            String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

            Object[] args = new Object[4];

            if (("".equals(searchCondition) || "0".equals(searchCondition)))
                    args[0] = "prodNo=%" + searchKeyword + "%";
            else
                    args[0] = "prodName=%" + searchKeyword + "%";

            if (searchVO.getSearchCategoryNo() != null
                            && !"".equals(searchVO.getSearchCategoryNo())
                            && !"A".equals(searchVO.getSearchCategoryNo())) {
                    args[1] = "categoryNo=" + searchVO.getSearchCategoryNo();
            }

            args[2] = "sortColumn=" + searchVO.getSortColumn();
            args[3] = "sortDirection=" + searchVO.getSortDirection();

            return queryService.find("getAjaxSaleListAll", args);
    }
}
