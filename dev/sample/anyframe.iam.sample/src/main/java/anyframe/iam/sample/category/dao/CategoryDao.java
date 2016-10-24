package anyframe.iam.sample.category.dao;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.dao.GenericDao;

import anyframe.iam.sample.domain.Category;

/**
 * An interface that provides a data management interface to the Category table.
 */
public interface CategoryDao extends GenericDao<Category, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}