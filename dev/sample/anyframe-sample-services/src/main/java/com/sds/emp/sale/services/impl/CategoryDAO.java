package com.sds.emp.sale.services.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.sale.services.CategoryVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * This DAO class contains IQueryService to get
 * IQueryService services and IPropertiesService to get
 * IPropertiesService services , addCategory,
 * updateCategory,getCategory,getCategoryList, and
 * getDropDownCategoryList methods for different
 * functionality on Category
 * @author gangab
 */
public class CategoryDAO {
    /**
     * This queryService is used to get QueryService
     * services
     */
    private IQueryService queryService;

    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * This propertiesService is used to get
     * PropertiesService services
     */
    private IPropertiesService propertiesService;

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * This addCategory is used to add new Category to
     * the database with
     * categoryNo,categoryName,categoryDesc,useYn,regId
     * @param categoryVO
     *        categoyVO parameter is used to get the
     *        category details
     * @throws Exception
     *         Exception will raise, when addCategory
     *         method failed
     */
    public void addCategory(CategoryVO categoryVO) throws Exception {

        // category Number is automatically generated

        String categoryNo = categoryVO.getCategoryNo();
        String categoryName = categoryVO.getCategoryName();
        String categoryDesc = categoryVO.getCategoryDesc();
        String useYn = categoryVO.getUseYn();
        String regId = categoryVO.getRegId();

        // This create method creates the row in the
        // table with
        // categoryNo,categoryName,categoryDesc, useYn,
        // regId, regId date

        queryService.create("addCategory", new Object[] {categoryNo,
            categoryName, categoryDesc, useYn, regId, regId });

    }

    /**
     * This updateCategory method is used to update the
     * category data
     * @param categoryVO
     *        categoryVO is used to get the category
     *        details
     * @throws Exception
     *         Exception will be raise, when
     *         updateCategory failed
     */
    public void updateCategory(CategoryVO categoryVO) throws Exception {
        String categoryNo = categoryVO.getCategoryNo();
        String categoryName = categoryVO.getCategoryName();
        String categoryDesc = categoryVO.getCategoryDesc();
        String useYn = categoryVO.getUseYn();
        String modifyId = categoryVO.getModifyId();

        // This update method is used for to update the
        // modified category data
        queryService.update("updateCategory", new Object[] {categoryName,
            categoryDesc, useYn, modifyId, categoryNo });

    }

    /**
     * This removeCategory method is used to remove the
     * category data
     * @param categoryVO
     *        categoryVO is used to get the category
     *        details
     * @throws Exception
     *         Exception will be raise, when
     *         removeCategory failed
     */
    public void removeCategory(CategoryVO categoryVO) throws Exception {
        String categoryNo = categoryVO.getCategoryNo();

        queryService.update("removeCategory", new Object[] {categoryNo });

    }

    /**
     * This getCategory method is used to the Category
     * deatails of specified categoryNo
     * @param categoryNo
     *        categoryNo is used to get the details of
     *        specified category Number
     * @return CategoryVO CategoryVO is contains
     *         category details
     * @throws Exception
     *         Exception will raised, when getCategory
     *         failed
     */
    public CategoryVO getCategory(String categoryNo) throws Exception {

        // find method is used to check and get
        // Category details of specified
        // CategoryNo

        Collection categoryCollection =
            queryService.find("getCategory", new Object[] {categoryNo });

        Iterator categoryItr = categoryCollection.iterator();
        if (categoryItr.hasNext()) {
            return (CategoryVO) categoryItr.next();
        }

        return null;
    }

    /**
     * This getCategoryList method is used to get list
     * of Category with propertiesService Services
     * @param searchVO
     *        searchVO is used to get the detials
     * @return page page contains the CategoryList
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryList failed
     */
    public Page getCategoryList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = propertiesService.getInt("PAGE_SIZE");
        int pageUnit = propertiesService.getInt("PAGE_UNIT");

        Object[] iVal = new Object[2];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "categoryNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "categoryName=%" + searchKeyword + "%";
        }

        if (searchVO.getSearchUseYn() != null
            && !"A".equals(searchVO.getSearchUseYn()))
            iVal[1] = "useYn=" + searchVO.getSearchUseYn();

        Map categoryListMap = null;

        categoryListMap =
            queryService.findWithRowCount("getCategoryList", iVal, pageIndex,
                pageSize);

        List resultList = (List) categoryListMap.get(IQueryService.LIST);
        int totalSize =
            ((Long) categoryListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);

        return resultPage;
    }

    /**
     * This getCategoryListAll method is used to get
     * list of All Categories by search Conditions
     * @param searchVO
     *        searchVO is used to get the detials
     * @return Collection list contains the CategoryList
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryListAll failed
     */
    public Collection getCategoryListAll(SearchVO searchVO) throws Exception {
        Object[] iVal = new Object[2];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "categoryNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "categoryName=%" + searchKeyword + "%";
        }

        if (searchVO.getSearchUseYn() != null
            && !"A".equals(searchVO.getSearchUseYn()))
            iVal[1] = "useYn=" + searchVO.getSearchUseYn();

        return queryService.find("getCategoryList", iVal);
    }

    /**
     * This getDropDownCategoryList method is used to
     * generate list based on category id or category
     * name.
     * @return Collection Drop down list of category values
     * @throws Exception
     *         Exception will raised, when
     *         getCategoryList failed
     */

    public Collection getDropDownCategoryList() throws Exception {

        return queryService.find("getDropDownCategoryList",
            new Object[] {});

    }
}
