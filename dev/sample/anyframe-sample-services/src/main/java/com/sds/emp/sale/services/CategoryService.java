package com.sds.emp.sale.services;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

import com.sds.emp.purchase.services.DeliveryCompanyService;

public interface CategoryService {
    String ROLE = CategoryService.class.getName();

    /** Logger object. */
    Log LOGGER = LogFactory.getLog(DeliveryCompanyService.class.getName());

    /**
     * @param changeMap
     *        This processAll method is used to insert,
     *        update, delete all of changed data list
     *        at once
     * @throws Exception
     *         Exception will be raise, when
     *         processAll failed
     */
    void processAll(HashMap changeMap) throws Exception;

    /**
     * @param categoryVO
     *        This addCategory is used to add new
     *        Category with
     *        categoryNo,categoryName,categoryDesc,useYn,regId
     * @return String
     * @throws Exception
     *         Exception will raise, when
     *         addCategory method failed
     */
    String addCategory(CategoryVO categoryVO) throws Exception;

    /**
     * @param categoryVO
     *        This updateCategory method is used to
     *        update the category data
     * @throws Exception
     *         Exception will be raise, when
     *         updateCategory failed
     */
    void updateCategory(CategoryVO categoryVO) throws Exception;

    /**
     * @param categoryVO
     *        This removeCategory method is used to
     *        remove the category data
     * @throws Exception
     *         Exception will be raise, when
     *         removeCategory failed
     */
    void removeCategory(CategoryVO categoryVO) throws Exception;

    /**
     * @param searchVO
     *        This getCategoryList method is used to
     *        get list of Category with
     *        propertiesService Services
     * @return page
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryList failed
     */
    Page getCategoryList(SearchVO searchVO) throws Exception;

    /**
     * @param searchVO
     *        This getCategoryListAll method is used to
     *        get list of Categories by Search
     *        Condition
     * @return Collection
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryList failed
     */
    Collection getCategoryListAll(SearchVO searchVO) throws Exception;

    /**
     * This getDropDownCategoryList method is used to
     * generate list based on category id or category
     * name.
     * @return Collection
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryList failed
     */
    Collection getDropDownCategoryList() throws Exception;

    /**
     * @param categoryNo
     *        This getCategory method is used to the
     *        Category deatails of specified categoryNo
     * @return CategoryVO
     * @throws Exception
     *         Exception will raised, when
     *         getCategory failed
     */
    CategoryVO getCategory(String categoryNo) throws Exception;
}
