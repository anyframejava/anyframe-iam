package org.anyframe.iam.sample.category.dao.impl;


import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.dao.query.GenericQueryDao;
import org.anyframe.iam.sample.category.dao.CategoryDao;
import org.anyframe.iam.sample.domain.Category;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.anyframe.util.properties.PropertiesService;
import org.springframework.util.ClassUtils;

public class CategoryDaoQueryImpl extends GenericQueryDao<Category, String> implements CategoryDao {
	private PropertiesService propertiesService;
	
    public PropertiesService getPropertiesService() {
		return propertiesService;
	}

	public void setPropertiesService(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	public CategoryDaoQueryImpl() {
        super(Category.class);
    }
    
	public Page getPagingList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
        int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");

        String searchCondition = StringUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = StringUtil.null2str(searchVO.getSearchKeyword());
        String isNumeric = StringUtil.isNumeric(searchKeyword) ? "true" : "false";
        
        Object[] args = new Object[4];		            
        args[0] = "condition=" + searchCondition;
        args[1] = "keywordStr=%" + searchKeyword + "%";
        args[2] = "keywordNum=" + searchKeyword + "";
        args[3] = "isNumeric=" + isNumeric;

        return this.findListWithPaging(ClassUtils.getShortName(getPersistentClass()), args, pageIndex, pageSize, pageUnit);
	}    
}
