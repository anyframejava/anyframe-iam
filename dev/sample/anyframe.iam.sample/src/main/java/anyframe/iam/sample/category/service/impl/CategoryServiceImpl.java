package anyframe.iam.sample.category.service.impl;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.iam.sample.category.dao.CategoryDao;
import anyframe.iam.sample.domain.Category;
import anyframe.iam.sample.category.service.CategoryService;
import anyframe.core.generic.service.impl.GenericServiceImpl;

public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements CategoryService {
    CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        super(categoryDao);
        this.categoryDao = categoryDao;
    }
    
	public Page getPagingList(SearchVO searchVO) throws Exception {
		return this.categoryDao.getPagingList(searchVO);
	}        
}