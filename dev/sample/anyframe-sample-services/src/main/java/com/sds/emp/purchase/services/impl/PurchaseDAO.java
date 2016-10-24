package com.sds.emp.purchase.services.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.purchase.services.PurchaseVO;
import com.sds.emp.purchase.services.SearchVO;

/**
 * This is the DAO for purchase Service.
 */

public class PurchaseDAO {

    /** an instance variable for the queryService. */
    private IQueryService queryService;

    /** an instance variable for the propertiesService. */
    private IPropertiesService propertiesService;

    /**
     * Sets the name of the QueryService to use.
     * @param queryService
     *        queryService for this member
     */
    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the name of the PropertiesService to use.
     * @param propertiesService
     *        propertiesService for this member.
     */
    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * This is for getting count list of purchases
     * based on delivery compNo.
     * @param dlvyCompNo
     *        Identifies the purchase list
     * @return int value of purchase list based on
     *         delivery company no
     * @throws Exception
     *         If it is unable to get purchase list by
     *         delivery company
     */
    public int countPurchaseListByDeliveryCompany(String dlvyCompNo)
            throws Exception {

        Map orderListMap =
            queryService.findWithRowCount("getPurchaseListByDeliveryCompany",
                new Object[] {dlvyCompNo });

        int totalSize =
            ((Long) orderListMap.get(IQueryService.COUNT)).intValue();

        return totalSize;
    }

    /**
     * This is for getting purchase details based on
     * Transaction No.
     * @param prodNo
     *        Identifer for getting purchase details
     * @return PurchaseVO Contains all the details of
     *         the purchase
     * @throws Exception
     *         If it is unable to get purchasedetails
     *         based on prodNo
     */
    public PurchaseVO getPurchase(String prodNo) throws Exception {

        Collection purchaseCollection =
            queryService.find("getPurchase", new Object[] {prodNo });

        Iterator purchaseItr = purchaseCollection.iterator();
        if (purchaseItr.hasNext()) {
            return (PurchaseVO) purchaseItr.next();
        }
        return null;
    }

    /**
     * This method is used to add the purchase details
     * into the corresponding purchase VO.
     * @param purchaseVO
     *        Object contains all the details of the
     *        purchase
     * @throws Exception
     *         If it fails to add the data into the
     *         purchase VO
     */
    public void addPurchase(PurchaseVO purchaseVO) throws Exception {
        // String tranNo = purchaseVO.getTranNo();
        String prodNo = purchaseVO.getProdNo();
        String sellerId = purchaseVO.getSellerId();
        String buyerId = purchaseVO.getBuyerId();
        String receiptYn = purchaseVO.getReceiptYn();
        String paymentOption = purchaseVO.getPaymentOption();
        String receiverId = purchaseVO.getReceiverId();
        String receiverPhone = purchaseVO.getReceiverPhone();
        String dlvyExpectDay = purchaseVO.getDlvyExpectDay();
        String dlvyAddr = purchaseVO.getDlvyAddr();
        String dlvyCompNo = purchaseVO.getDlvyCompNo();
        String dlvyRequest = purchaseVO.getDlvyRequest();

        String tranStatusCode = "002";

        queryService.create("addPurchase", new Object[] {prodNo, sellerId,
            buyerId, receiptYn, paymentOption, receiverId, receiverPhone,
            dlvyExpectDay, dlvyAddr, dlvyCompNo, dlvyRequest, tranStatusCode });

        // there is no relationship between purchase
        // and product itself.
        // queryService.update("updateProdTranStatusCode",
        // new Object[] {
        // tranStatusCode, prodNo });

    }

    /**
     * This method is used to update the purchase
     * details.
     * @param purchaseVO
     *        Contains all the details of the purchase
     * @throws Exception
     *         If it fails to update the data from the
     *         purchase VO
     */
    public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
        String receiptYn = purchaseVO.getReceiptYn();
        String paymentOption = purchaseVO.getPaymentOption();
        String receiverId = purchaseVO.getReceiverId();
        String receiverPhone = purchaseVO.getReceiverPhone();
        String dlvyExpectDay = purchaseVO.getDlvyExpectDay();
        String dlvyAddr = purchaseVO.getDlvyAddr();
        String dlvyCompNo = purchaseVO.getDlvyCompNo();
        String dlvyRequest = purchaseVO.getDlvyRequest();
        String prodNo = purchaseVO.getProdNo();

        queryService.update("updatePurchase", new Object[] {receiptYn,
            paymentOption, receiverId, receiverPhone, dlvyExpectDay, dlvyAddr,
            dlvyCompNo, dlvyRequest, prodNo });
    }

    /**
     * This method is used to get the list of purchase
     * details based on searchVO.
     * @param searchVO
     *        contains delivery company no and name as
     *        the major search key words.
     * @return Page Contains list of purchase details
     * @throws Exception
     *         If it fails to get the data from the
     *         list of purchase details
     */
    public Page getPurchaseList(SearchVO searchVO) throws Exception {
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

        if (searchVO.getSearchTranStatusCode() != null
            && !"A".equals(searchVO.getSearchTranStatusCode()))
            iVal[1] = "tranStatusCode=" + searchVO.getSearchTranStatusCode();

        iVal[2] = "buyerId=" + searchVO.getSearchBuyerId();

        Map purchaseListMap =
            queryService.findWithRowCount("getPurchaseList", iVal, pageIndex,
                pageSize);

        List resultList = (List) purchaseListMap.get(IQueryService.LIST);

        int totalSize =
            ((Long) purchaseListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);
        return resultPage;
    }
}
