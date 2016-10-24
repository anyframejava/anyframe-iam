package anyframe.iam.sample.product.service.impl;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.iam.sample.product.dao.ProductDao;
import anyframe.iam.sample.domain.Product;
import anyframe.iam.sample.product.service.ProductService;
import anyframe.core.generic.service.impl.GenericServiceImpl;

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