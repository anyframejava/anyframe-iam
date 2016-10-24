package com.sds.emp.purchase.services.impl;

import java.util.Collection;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;
import anyframe.core.idgen.IIdGenerationService;

import com.sds.emp.common.EmpException;
import com.sds.emp.purchase.services.DeliveryCompanyService;
import com.sds.emp.purchase.services.DeliveryCompanyVO;
import com.sds.emp.purchase.services.SearchVO;

/**
 * This is an implementation class for
 * DeliveryCompanyService. This DeliveryCompanyService
 * will be used by emarketplace to know more about
 * Delivery Company details.
 */

public class DeliveryCompanyServiceImpl implements DeliveryCompanyService,
        ApplicationContextAware {

    /** an instance variable for the deliveryCompanyDAO. */

    private DeliveryCompanyDAO deliveryCompanyDAO;

    /**
     * an instance variable for the
     * idGenerationService.
     */
    private IIdGenerationService idGenerationService;

    /**
     * Used by <code>LOGGER</code> and for creating
     * Exception message.
     */
    private MessageSource messageSource;

    /**
     * Sets the idGenerationService to use.
     * @param idGenerationService
     *        Generates unique id
     */
    public void setIdGenerationService(IIdGenerationService idGenerationService) {
        this.idGenerationService = idGenerationService;
    }

    /**
     * Sets the deliveryCompanyDAO to use.
     * @param deliveryCompanyDAO
     *        DAO for delivery company service
     */
    public void setDeliveryCompanyDAO(DeliveryCompanyDAO deliveryCompanyDAO) {
        this.deliveryCompanyDAO = deliveryCompanyDAO;
    }

    /**
     * This method is used to add the delivery company
     * details into the corresponding deliveryCompnay
     * VO
     * @param deliveryCompanyVO
     *        contains all the details of delivery
     *        company.
     * @return String representation of delvyCompNo
     *         never null;
     * @throws Exception
     *         If it is unable to add the data to
     *         delivery company
     * @see com.sds.emp.purchase.services.DeliveryCompanyService#addDeliveryCompany(com.sds.emp.
     *      purchase.services.DeliveryCompanyVO)
     */

    public String addDeliveryCompany(DeliveryCompanyVO deliveryCompanyVO)
            throws Exception {

        /**
         * If the delivery comapny number is null then
         * it will generate the delvyCompNo from the
         * idgeneration service If the delycompno is
         * out of range the it will throws an exception
         */
        String delvyCompNo = idGenerationService.getNextStringId();
        deliveryCompanyVO.setDlvyCompNo(delvyCompNo);
        deliveryCompanyDAO.addDeliveryCompany(deliveryCompanyVO);
        return delvyCompNo;
    }

    /**
     * This method is used to update the delivery
     * company details into the corresponding
     * deliveryCompnay VO
     * @param deliveryCompanyVO
     *        contains all the details of delivery
     *        company.
     * @throws Exception
     *         If it is unable to update the delivery
     *         company data
     * @see com.sds.emp.purchase.services.DeliveryCompanyService#addDeliveryCompany(com.sds.emp.
     *      purchase.services.DeliveryCompanyVO)
     */

    public void updateDeliveryCompany(DeliveryCompanyVO deliveryCompanyVO)
            throws Exception {
        deliveryCompanyDAO.updateDeliveryCompany(deliveryCompanyVO);
    }

    /**
     * This method is used to get the delivery company
     * details into the corresponding deliveryCompnay
     * VO.
     * @param searchVO
     *        contains search details of delivery
     *        company.
     * @return <code>Page</code> Object.Page contains
     *         the search list of delivery company
     *         details
     * @throws Exception
     *         If it is unable to get the list of
     *         delivery company data
     * @see com.sds.emp.purchase.services.DeliveryCompanyService#getDeliveryCompanyList(com.sds.
     *      emp.purchase.services.SearchVO)
     */
    public Page getDeliveryCompanyList(SearchVO searchVO) throws Exception {
        return deliveryCompanyDAO.getDeliveryCompanyList(searchVO);
    }

    /**
     * This method is used to get the delivery company
     * details based on delivery company No.
     * @param delvyCompNo
     *        Used to get the delivery company details
     * @return DeliveryCompanyVO contains delivery
     *         company details
     * @throws Exception
     *         If it is unable to get the list of
     *         delivery company data
     * @see com.sds.emp.purchase.services.DeliveryCompanyService#getDeliveryCompany(java.lang.String)
     */
    public DeliveryCompanyVO getDeliveryCompany(String delvyCompNo)
            throws Exception {
        DeliveryCompanyVO deliveryCompanyVO =
            deliveryCompanyDAO.getDeliveryCompany(delvyCompNo);

        if (deliveryCompanyVO == null) {
            throw new EmpException(messageSource.getMessage(
                "error.deliverycompanyserviceimpl.getdeliverycompany",
                new String[] {delvyCompNo }, Locale.getDefault()), null);

        }
        return deliveryCompanyVO;
    }

    /**
     * This method is used to get the delivery company
     * details based on delivery company No.
     * @return Collection contains list of delivery company
     *         details
     * @throws Exception
     *         If it is unable to get the list of
     *         delivery company data
     * @see com.sds.emp.purchase.services.DeliveryCompanyService#getDropDownDeliveryCompanyList()
     */
    public Collection getDropDownDeliveryCompanyList() throws Exception {
        return deliveryCompanyDAO.getDropDownDeliveryCompanyList();

    }

    /**
     * This method is used to get the product details
     * based on product No
     * @param applicationContext
     *        <code>ApplicationContext</code> object.
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        messageSource =
            (MessageSource) applicationContext.getBean("messageSource");
    }
}
