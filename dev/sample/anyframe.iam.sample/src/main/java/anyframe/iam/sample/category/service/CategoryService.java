package anyframe.iam.sample.category.service;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.sample.domain.Category;

public interface CategoryService extends GenericService<Category, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Category get(String categoryNo) throws Exception;
	void create(Category category) throws Exception;
	void update(Category category) throws Exception;
	void remove(String categoryNo) throws Exception;
}