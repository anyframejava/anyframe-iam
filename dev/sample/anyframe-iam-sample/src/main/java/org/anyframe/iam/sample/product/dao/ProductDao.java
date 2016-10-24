package org.anyframe.iam.sample.product.dao;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.dao.GenericDao;
import org.anyframe.iam.sample.domain.Product;
import org.anyframe.pagination.Page;


/**
 * An interface that provides a data management interface to the Product table.
 */
public interface ProductDao extends GenericDao<Product, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}