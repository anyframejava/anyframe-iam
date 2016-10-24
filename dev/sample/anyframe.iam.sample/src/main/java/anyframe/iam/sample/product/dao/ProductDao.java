package anyframe.iam.sample.product.dao;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.dao.GenericDao;

import anyframe.iam.sample.domain.Product;

/**
 * An interface that provides a data management interface to the Product table.
 */
public interface ProductDao extends GenericDao<Product, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}