package org.anyframe.iam.sample.category.service.impl;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.sample.category.dao.CategoryDao;
import org.anyframe.iam.sample.category.service.CategoryService;
import org.anyframe.iam.sample.domain.Category;
import org.anyframe.pagination.Page;

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