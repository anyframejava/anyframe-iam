package anyframe.iam.sample.category.dao.impl;

import anyframe.iam.sample.domain.Category;
import anyframe.iam.sample.category.dao.CategoryDao;

import anyframe.common.util.SearchVO;
import anyframe.common.util.StringUtil;
import anyframe.common.Page;
import anyframe.core.generic.dao.query.GenericDaoQuery;

import org.springframework.util.ClassUtils;

public class CategoryDaoQueryImpl extends GenericDaoQuery<Category, String> implements CategoryDao {

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
