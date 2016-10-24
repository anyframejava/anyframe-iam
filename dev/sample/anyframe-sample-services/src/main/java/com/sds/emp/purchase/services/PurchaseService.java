package com.sds.emp.purchase.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

public interface PurchaseService {

    /** Logger object. */
    Log LOGGER = LogFactory.getLog(PurchaseService.class.getName());

    String ROLE = PurchaseService.class.getName();

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
    int countPurchaseListByDeliveryCompany(String dlvyCompNo)
            throws Exception;

    /**
     * This method is used to add the purchase details
     * into the corresponding purchase VO.
     * @param purchaseVO
     *        Object contains all the details of the
     *        purchase
     * @return String value of purchase list based on
     *         TranNo
     * @throws Exception
     *         If it fails to add the data into the
     *         purchase VO
     */
    String addPurchase(PurchaseVO purchaseVO) throws Exception;

    /**
     * This method is used to update the purchase
     * details.
     * @param purchaseVO
     *        Contains all the details of the purchase
     * @throws Exception
     *         If it fails to update the data from the
     *         purchase VO
     */
    void updatePurchase(PurchaseVO purchaseVO) throws Exception;

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
    Page getPurchaseList(SearchVO searchVO) throws Exception;

    /**
     * This is for getting purchase details based on
     * Transaction No.
     * @param tranNo
     *        Identifer for getting purchase details
     * @return PurchaseVO Contains all the details of
     *         the purchase
     * @throws Exception
     *         If it is unable to get purchasedetails
     *         based on tranNo
     */
    PurchaseVO getPurchase(String prodNo) throws Exception;

}
