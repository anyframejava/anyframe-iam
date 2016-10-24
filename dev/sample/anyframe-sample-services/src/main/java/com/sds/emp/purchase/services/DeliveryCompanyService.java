package com.sds.emp.purchase.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

/**
 * This interface allows various components of the
 * emarketplace to record and gather statistics about
 * the DeliveryCompanyService during execution.
 */
public interface DeliveryCompanyService {

    /** Logger object. */
    Log LOGGER = LogFactory.getLog(DeliveryCompanyService.class.getName());
    String ROLE = DeliveryCompanyService.class.getName();

    /**
     * This method is for adding delivery company
     * details into coresponding delivery company vo.
     * @param deliveryCompVO
     *        contains all the information of delivery
     *        company
     * @return String string of delivery compno
     * @throws Exception
     *         if there is any problem in adding
     *         delivery company details
     */
    String addDeliveryCompany(DeliveryCompanyVO deliveryCompVO)
            throws Exception;

    /**
     * This method is for updating delivery company
     * details of the delivery company vo and stores
     * the updated values into coresponding delivery
     * company vo.
     * @param deliveryCompVO
     *        contains all the information of updated
     *        delivery company
     * @throws Exception
     *         if there is any problem in updating
     *         delivery company details
     */
    void updateDeliveryCompany(DeliveryCompanyVO deliveryCompVO)
            throws Exception;

    /**
     * This method is for getting the list of delivery
     * company details and corresponding values are
     * stored in Page object
     * @param searchVO
     *        is used to search the delivery company
     *        details
     * @return page cotains all the information of the
     *         delivery company.
     * @throws Exception
     *         if there is any problem in updating
     *         delivery company details
     */
    Page getDeliveryCompanyList(SearchVO searchVO) throws Exception;

    /**
     * This method is for getting the delivery company
     * details based on delivery company no.
     * @param dlvyCompNo
     *        the base location of the delivery company
     *        information.
     * @return deliveryCompanyVO based on delivery
     *         company no.
     * @throws Exception
     *         if there is any problem in getting the
     *         delivery company details
     */
    DeliveryCompanyVO getDeliveryCompany(String dlvyCompNo) throws Exception;

    /**
     * This method is for getting the list of delivery
     * company details and corresponding values are
     * stored in list object
     * @return Collection conatins list of delivered company
     *         details.
     * @throws Exception
     *         if there is any problem in getting the
     *         delivery company details
     */
    Collection getDropDownDeliveryCompanyList() throws Exception;
}
