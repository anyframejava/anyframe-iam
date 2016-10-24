package org.anyframe.iam.sample.category.dao;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.dao.GenericDao;
import org.anyframe.iam.sample.domain.Category;
import org.anyframe.pagination.Page;


/**
 * An interface that provides a data management interface to the Category table.
 */
public interface CategoryDao extends GenericDao<Category, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}