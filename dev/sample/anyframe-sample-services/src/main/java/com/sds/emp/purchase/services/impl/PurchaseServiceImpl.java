package com.sds.emp.purchase.services.impl;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;

import com.sds.emp.common.EmpException;
import com.sds.emp.purchase.services.PurchaseService;
import com.sds.emp.purchase.services.PurchaseVO;
import com.sds.emp.purchase.services.SearchVO;

/**
 * This is an implementation class for PurchaseService.
 * This PurchaseService will be used by emarketplace to
 * know more about purchase details.
 */
public class PurchaseServiceImpl implements PurchaseService,
        ApplicationContextAware {

    /** an instance variable for the purchaseDAO. */
    private PurchaseDAO purchaseDAO;

    /**
     * Used by <code>LOGGER</code> and for creating
     * Exception message.
     */
    private MessageSource messageSource;

    /**
     * Sets the setPurchaseDAO to use.
     * @param purchaseDAO
     *        DAO for purchase service
     */
    public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
        this.purchaseDAO = purchaseDAO;
    }

    /**
     * This method is used to count purchase list using
     * delivery companyNo.
     * @param dlvyCompNo
     *        Used to get the delivery company details
     * @return String representation of delvyCompNo
     *         never null;
     * @throws Exception
     *         If it is unable to get the purchase
     *         details
     * @see com.sds.emp.purchase.services.PurchaseService#countPurchaseListByDeliveryCompany(java.lang.String)
     */
    public int countPurchaseListByDeliveryCompany(String dlvyCompNo)
            throws Exception {
        int purchaseCount =
            purchaseDAO.countPurchaseListByDeliveryCompany(dlvyCompNo);
        return purchaseCount;
    }

    /**
     * This method is used to add purchasedetails into
     * corresponding VO.
     * @param purchaseVO
     *        Contains all the details of the purchase *
     * @return String representation of addPurchase
     * @throws Exception
     *         If it is unable to add the purchase
     *         details
     * @see com.sds.emp.purchase.services.PurchaseService#addPurchase(com.sds.emp.purchase.
     *      services.PurchaseVO)
     */
    public String addPurchase(PurchaseVO purchaseVO) throws Exception {
        purchaseDAO.addPurchase(purchaseVO);
        return purchaseVO.getProdNo();
    }

    /**
     * This method is used to update purchasedetails .
     * @param purchaseVO
     *        Contains all updated details of the
     *        purchase
     * @throws Exception
     *         If it is unable to update the purchase
     *         details
     * @see com.sds.emp.purchase.services.PurchaseService#updatePurchase(com.sds.emp.purchase.services.PurchaseVO)
     */
    public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
        purchaseDAO.updatePurchase(purchaseVO);
    }

    /**
     * This method is used to get list of
     * purchasedetails .
     * @param searchVO
     *        Contains searched list of the purchase
     * @return Page contains the search list of purchse
     *         details
     * @throws Exception
     *         If it is unable to get list of purchase
     *         details
     * @see com.sds.emp.purchase.services.PurchaseService#getPurchaseList(com.sds.emp.purchase.services.SearchVO)
     */
    public Page getPurchaseList(SearchVO searchVO) throws Exception {
        return purchaseDAO.getPurchaseList(searchVO);
    }

    /**
     * This method is used to get purchase details
     * based on delivery company No.
     * @param prodNo
     *        Used to get the purchase details
     * @return PurchaseVO contains purchase details
     * @throws Exception
     *         If it is unable to get the purchase
     *         details
     * @see com.sds.emp.purchase.services.PurchaseService#getPurchase(java.lang.String)
     */
    public PurchaseVO getPurchase(String prodNo) throws Exception {

        PurchaseVO purchaseVO = purchaseDAO.getPurchase(prodNo);

        if (purchaseVO == null) {
            throw new EmpException((messageSource.getMessage(
                "error.purchaseserviceimpl.getpurchase", new String[] {prodNo }, Locale
                    .getDefault())), null);
        }
        return purchaseVO;

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
