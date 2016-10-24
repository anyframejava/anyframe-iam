package org.anyframe.iam.sample.category.service;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.sample.domain.Category;
import org.anyframe.pagination.Page;

public interface CategoryService extends GenericService<Category, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Category get(String categoryNo) throws Exception;
	void create(Category category) throws Exception;
	void update(Category category) throws Exception;
	void remove(String categoryNo) throws Exception;
}