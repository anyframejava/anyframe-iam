package org.anyframe.iam.sample.product.service.impl;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.sample.domain.Product;
import org.anyframe.iam.sample.product.dao.ProductDao;
import org.anyframe.iam.sample.product.service.ProductService;
import org.anyframe.pagination.Page;

public class ProductServiceImpl extends GenericServiceImpl<Product, String> implements ProductService {
    ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        super(productDao);
        this.productDao = productDao;
    }
    
	public Page getPagingList(SearchVO searchVO) throws Exception {
		return this.productDao.getPagingList(searchVO);
	}        
}