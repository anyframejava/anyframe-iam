package com.sds.emp.sale.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;
import anyframe.core.idgen.IIdGenerationService;

import com.sds.emp.common.EmpException;
import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.CategoryVO;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.SearchVO;

/**
 * This CategoryServiceImpl is implementing from
 * CategoryService, this class contains
 * CategoryDAO,ProductService,IIdGenerationService
 * setter methods and addCategory,
 * updateCategory,getCategoryList,getDropDownCategoryList,
 * and getCategory method
 * @author gangab
 */

public class CategoryServiceImpl implements CategoryService,
        ApplicationContextAware {

    /**
     * categoryDAO is used to access the CategoryDAO
     * methods addCategory, updateCategory,
     * getCategory,getCategoryList and
     * getDropDownCategoryList
     */

    private CategoryDAO categoryDAO;

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    /**
     * productService is used to get the ProductService
     * services
     */
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * IIdGenerationService Object is used to get
     * idGenerationService services
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
     * @param changeMap
     *        This processAll method is used to insert,
     *        update, delete all of changed data list
     *        at once
     * @throws EmpException
     *         EmpException will be raise, when
     *         processAll failed
     */

    public void processAll(HashMap changeMap) throws Exception {

        ArrayList deleteList = (ArrayList) changeMap.get("delete");
        for (int i = 0; i < deleteList.size(); i++) {
            CategoryVO categoryVO = (CategoryVO) deleteList.get(i);
            removeCategory(categoryVO);
        }

        ArrayList updateList = (ArrayList) changeMap.get("update");
        for (int i = 0; i < updateList.size(); i++) {
            CategoryVO categoryVO = (CategoryVO) updateList.get(i);
            updateCategory(categoryVO);
        }

        ArrayList insertList = (ArrayList) changeMap.get("insert");
        for (int i = 0; i < insertList.size(); i++) {
            CategoryVO categoryVO = (CategoryVO) insertList.get(i);
            addCategory(categoryVO);
        }
    }

    /**
     * This addCategory method is used to add the new
     * category to the database category table with
     * categoryVO parameter
     * @param categoryVO
     *        categoryVO object contains new category
     *        fields.
     * @return String
     * @throws Exception
     *         Exception will raise,if addCategory fail
     */

    public String addCategory(CategoryVO categoryVO) throws Exception {
        // This statement returns the automatically
        // generated categoryNo
        // using idGenerationService.getNextStringId
        // method

        String categoryNo = idGenerationService.getNextStringId();

        // This statement sets the automatically
        // generated number

        categoryVO.setCategoryNo(categoryNo);

        // This addCategory method added newly created
        // categoryVO data in
        // the database CATEGORY table

        categoryDAO.addCategory(categoryVO);
        return categoryNo;
    }

    /**
     * This method is used to update the Category
     * information with categoryVO
     * @param categoryVO
     *        categoryVO objects contains updated
     *        category details
     * @throws Exception
     *         Exception will be raise, updateCategory
     *         method fails
     */

    public void updateCategory(CategoryVO categoryVO) throws Exception {

        // countProductListByCategory returns number of
        // products
        // belongs to specigied category Number

        if ("N".equals(categoryVO.getUseYn())
            && productService.countProductListByCategory(categoryVO
                .getCategoryNo()) > 0) {

            throw new EmpException(messageSource.getMessage(
                "error.sale.category.update.if", new String[] {categoryVO
                    .getCategoryNo() }, Locale.getDefault()), null);
        }

        // This update method is used for to update the
        // modified category
        // data

        categoryDAO.updateCategory(categoryVO);
    }

    /**
     * This method is used to remove the Category
     * information with categoryVO
     * @param categoryVO
     *        categoryVO objects contains deleted
     *        category details
     * @throws Exception
     *         Exception will be raise, deleteCategory
     *         method fails
     */

    public void removeCategory(CategoryVO categoryVO) throws Exception {

        // countProductListByCategory returns number of
        // products
        // belongs to specigied category Number

        if (productService.countProductListByCategory(categoryVO
            .getCategoryNo()) > 0) {

            throw new EmpException(messageSource.getMessage(
                "error.sale.category.delete.if", new String[] {categoryVO
                    .getCategoryNo() }, Locale.getDefault()), null);
        }

        // This remove method is used for to remove the
        // deleted category
        // data

        categoryDAO.removeCategory(categoryVO);
    }

    /**
     * This getCategoryList is used to generate list
     * @param searchVO
     *        using SearchVO object list generated by
     *        getCategoryList method
     * @return Page page contains the CategoryList
     * @throws Exception
     *         Exception will raise, when
     *         getCategoryList fail
     */
    public Page getCategoryList(SearchVO searchVO) throws Exception {
        return categoryDAO.getCategoryList(searchVO);
    }

    /**
     * This getCategoryListAll is used to generate list
     * @param searchVO
     *        using SearchVO object list generated by
     *        getCategoryListAll method
     * @return Collection list contains the CategoryList
     * @throws EmpException
     *         EmpException will raise, when
     *         getCategoryList fail
     */
    public Collection getCategoryListAll(SearchVO searchVO) throws Exception {
        // This statement is used to check debug is
        // enabled or not
        // This getCategoryListAll method is used for
        // to get the
        // categorylist data

        return categoryDAO.getCategoryListAll(searchVO);
    }

    /**
     * This getDropDownCategoryList method is used to
     * generate list based on
     * categoryDAO.getDropDownCategoryList method
     * @return Collection Collection contains the Category List
     * @throws EmpException
     *         EmpException will raise, when
     *         getDropDownCategoryList fail
     */

    public Collection getDropDownCategoryList() throws Exception {
        return categoryDAO.getDropDownCategoryList();
    }

    /**
     * This getCategory method is used for to the
     * Category deatails of specified categoryNo
     * @param categoryNo
     *        Used to get category details
     * @return CategoryVO Contains all the values of
     *         category
     * @throws EmpException
     *         EmpException will raise, when
     *         getCategory fail
     */
    public CategoryVO getCategory(String categoryNo) throws Exception {
        CategoryVO categoryVO = categoryDAO.getCategory(categoryNo);

        if (categoryVO == null) {

            throw new EmpException(messageSource.getMessage(
                "error.sale.category.get.notexists",
                new String[] {categoryNo }, Locale.getDefault()), null);

        }

        return categoryVO;
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
