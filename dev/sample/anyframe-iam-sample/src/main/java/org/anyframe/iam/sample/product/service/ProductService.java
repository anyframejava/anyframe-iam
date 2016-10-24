package org.anyframe.iam.sample.product.service;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.sample.domain.Product;
import org.anyframe.pagination.Page;

public interface ProductService extends GenericService<Product, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Product get(String productNo) throws Exception;
	void create(Product product) throws Exception;
	void update(Product product) throws Exception;
	void remove(String productNo) throws Exception;
}