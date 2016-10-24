package com.sds.emp.sale.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

public interface SaleService {
    Log LOGGER = LogFactory.getLog(SaleService.class.getName());

    String ROLE = SaleService.class.getName();

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

    void updateTranStatusCode(SaleVO saleVO) throws Exception;

    /**
     * @param searchVO
     *        This getSaleList method is used to get
     *        list of Sale with propertiesService
     *        Services
     * @return page
     * @throws Exception
     *         Exception will raise, when
     *         getSaleList is failed
     */
    Page getSaleList(SearchVO searchVO) throws Exception;

    /**
     * @param searchVO
     *        This getSaleListAll method is used to get
     *        list of Sale
     * @return List
     * @throws Exception
     *         Exception will raise, when
     *         getSaleList is failed
     */
    Collection getSaleListAll(SearchVO searchVO) throws Exception;

    /**
     * @param prodNo
     *        This getSale method is used for to the
     *        Category deatails of specified product
     *        Number
     * @return SaleVO
     * @throws Exception
     *         Exception will raise, when getSale
     *         failed
     */
    SaleVO getSale(String prodNo) throws Exception;
    
    /**
     * @param searchVO
     *        This getSaleList method is used to get
     *        list of Sale with propertiesService
     *        Services
     * @return Collection
     * @throws Exception
     *         Exception will raise, when
     *         getSaleList is failed
     */
    Collection getAjaxSaleListAll(SearchVO searchVO) throws Exception;
}
