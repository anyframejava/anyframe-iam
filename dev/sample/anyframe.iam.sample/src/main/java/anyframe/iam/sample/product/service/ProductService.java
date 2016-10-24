package anyframe.iam.sample.product.service;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.sample.domain.Product;

public interface ProductService extends GenericService<Product, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Product get(String productNo) throws Exception;
	void create(Product product) throws Exception;
	void update(Product product) throws Exception;
	void remove(String productNo) throws Exception;
}