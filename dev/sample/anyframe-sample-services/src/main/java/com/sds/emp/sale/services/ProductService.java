package com.sds.emp.sale.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

public interface ProductService {
    /**
     * LOGGER object
     */

    Log LOGGER = LogFactory.getLog(ProductService.class.getName());

    String ROLE = ProductService.class.getName();

    /**
     * @param categoryNo
     *        This method is used to generate number of
     *        products with specified categoryNo
     * @return int
     * @throws Exception
     *         Exception will raise, when
     *         countProductListByCategory failed
     */
    int countProductListByCategory(String categoryNo) throws Exception;

    /**
     * @param productVO
     *        This method is used to add product with
     *        prodNo, productName, sellerId,
     *        categoryNo,ProductDetail,manufactureDay,asYn,
     *        sellQuantity,sellAmount,and imageFile
     *        details
     * @return String
     * @throws Exception
     *         Exception will raise, when addProduct
     *         failed
     */
    String addProduct(ProductVO productVO) throws Exception;

    /**
     * @param productVO
     *        This updateCategory method is used to
     *        update the product data
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */
    void updateProduct(ProductVO productVO) throws Exception;

    /**
     * @param productNo
     *        This getProduct method is used for to the
     *        Product deatails of specified ProductNo
     * @return ProductVO
     * @throws Exception
     *         Exception will raise, when updateProduct
     *         failed
     */
    ProductVO getProduct(String productNo) throws Exception;
    
    /**
     * This method is used to get search list of
     * product details and are stored into the
     * corresponding search VO
     * @param searchVO
     *        contains search details of Products
     * @throws Exception
     *         If it is unable to get search list of
     *         product details
     * @return Page contains the search list of product
     *         details
     */
    Page getProductList(SearchVO searchVO) throws Exception;
    
}
