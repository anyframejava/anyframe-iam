package com.sds.emp.sale.services.impl;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;
import anyframe.core.idgen.IIdGenerationService;

import com.sds.emp.common.EmpException;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * This ProductServiceImpl is implementing from
 * ProductService, this class contains
 * ProductDAO,IIdGenerationService setter methods and
 * countProductListByCategory,addProduct, updateProduct
 * and getProduct method
 * @author gangab
 */

public class ProductServiceImpl implements ProductService,
        ApplicationContextAware {

    /**
     * This setter method is used to set the
     * categoryDAO object
     * @param productDAO
     *        ProductDAO methods are calling using
     *        productDAO object productDAO methods are
     *        used to add , update product , to get
     *        product and countProductListByCategory
     */
    private ProductDAO productDAO;

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * idGenerationService automatically generate id
     */
    private IIdGenerationService idGenerationService;

    public void setIdGenerationService(IIdGenerationService idGenerationService) {
        this.idGenerationService = idGenerationService;
    }

    /**
     * messageSource is used to call getMessage method
     */

    private MessageSource messageSource;

    /**
     * This method is used to generate number of
     * products with specified categoryNo
     * @param categoryNo
     *        category number is used to get the
     *        specified category Number
     * @return int Contains the count value category
     * @throws Exception
     *         Exception will raise, when
     *         countProductListByCategory failed
     */
    public int countProductListByCategory(String categoryNo) throws Exception {
        int count = productDAO.countProductListByCategory(categoryNo);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(new Integer(count).toString());
        }
        return count;
    }

    /**
     * This method is used to add product with prodNo,
     * productName, sellerId,
     * categoryNo,ProductDetail,manufactureDay,asYn,
     * sellQuantity,sellAmount,and imageFile details
     * productVO is used to set the Product No
     * @param productVO
     *        productVO contains entered data by user
     * @return String Returns String value of product
     *         number
     * @throws Exception
     *         Exception will raised, when addProduct
     *         failed
     */
    public String addProduct(ProductVO productVO) throws Exception {
        // This statement returns the automatically
        // generated productNo
        // using
        // idGenerationService.getNextStringId
        // method

        String prodNo = idGenerationService.getNextStringId();
        productVO.setProdNo(prodNo);

        // This addProduct method added newly
        // created ProductVO data in the
        // database PRODUCT table

        productDAO.addProduct(productVO);

        return prodNo;
    }

    /**
     * This method is used to update the productVO
     * information with productVO. productVO is used to
     * call the getProdName()
     * @param productVO
     *        productVO contains user entered data
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */
    public void updateProduct(ProductVO productVO) throws Exception {
        // This updateProduct method is used for to
        // update the modified
        // product data with ProductVO
        productDAO.updateProduct(productVO);
    }

    /**
     * This getProduct method is used for to the
     * Product deatails of specified ProductNo ProdNo
     * parameter is used to pass product Number
     * @param prodNo
     *        Product number is used to get the
     *        specified product details
     * @return ProductVO productVO contains details of
     *         specified product number
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */
    public ProductVO getProduct(String prodNo) throws Exception {
        ProductVO productVO = productDAO.getProduct(prodNo);

        if (productVO == null) {
            throw new EmpException(messageSource.getMessage(
                "error.sale.product.get.notexists", new String[] {prodNo }, Locale.getDefault()), null);
        }
        return productVO;

    }
    
    public Page getProductList(SearchVO searchVO) throws Exception {
        return productDAO.getProductList(searchVO);
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
