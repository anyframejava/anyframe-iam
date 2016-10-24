package com.sds.emp.sale.services.impl;

import java.util.Collection;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;

import com.sds.emp.common.EmpException;
import com.sds.emp.sale.services.SaleService;
import com.sds.emp.sale.services.SaleVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * This SaleServiceImpl is implementing from
 * SaleService, this class contains SaleDAOyDAO setter
 * method and updateTranStatusCode,getSaleList and
 * getSale methods
 * @author gangab
 */

public class SaleServiceImpl implements SaleService, ApplicationContextAware {

    /**
     * messageSource is used to call getMessage method
     */
    private MessageSource messageSource;

    /**
     * saleDAO is used to access the saleDAO methods
     * updateTranStatusCode, getSaleList and getSale
     */

    private SaleDAO saleDAO;

    public void setSaleDAO(SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
    }

    /**
     * This updateTranStatusCode method is used to
     * update the TranStatusCode
     * @param saleVO
     *        used to update transaction status code
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public void updateTranStatusCode(SaleVO saleVO) throws Exception {
        // This updateTranStatusCode method is used
        // for to update the
        // modified TranStatusCode
        saleDAO.updateTranStatusCode(saleVO);

    }

    /**
     * This getSaleList method is used to get Sale List
     * with searchVO
     * @param searchVO
     *        This getSaleList method is used to get
     *        Sale List with searchVO
     * @return Page Contains list of sales details
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public Page getSaleList(SearchVO searchVO) throws Exception {
        return saleDAO.getSaleList(searchVO);
    }

    /**
     * This getSaleListAll method is used to get Sale
     * List with searchVO
     * @param searchVO
     *        This getSaleList method is used to get
     *        Sale List with searchVO
     * @return List Contains list of sales details
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public Collection getSaleListAll(SearchVO searchVO) throws Exception {
        return saleDAO.getSaleListAll(searchVO);
    }

    /**
     * This getSale method is used for to the Category
     * deatails of specified product Number
     * @param prodNo
     *        Used to get sales details
     * @return SaleVO contains sales setails
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public SaleVO getSale(String prodNo) throws Exception {
        // This getSale method is used to get the
        // sale details into the
        // SaleVO object with Product Number
        SaleVO saleVO = saleDAO.getSale(prodNo);

        if (saleVO == null) {
            throw new EmpException(messageSource.getMessage(
                "error.sale.sales.get.sale.notexists", new String[] {}, Locale
                    .getDefault()), null);
        }
        return saleVO;
    }
    
    /**
     * This getAjaxSaleListAll method is used to get Sale List
     * with searchVO
     * @param searchVO
     *        This getSaleList method is used to get
     *        Sale List with searchVO
     * @return Collection Contains list of sales details
     * @throws Exception
     *         Exception will raise, when
     *         updateTranStatusCode failed
     */
    public Collection getAjaxSaleListAll(SearchVO searchVO) throws Exception {
        return saleDAO.getAjaxSaleListAll(searchVO);
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
