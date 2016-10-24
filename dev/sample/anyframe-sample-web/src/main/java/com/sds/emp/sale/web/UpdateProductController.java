package com.sds.emp.sale.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * controller class for modify product.
 * @author Heewon Jung
 */
public class UpdateProductController extends AnyframeFormController {

    private ProductService productService = null;

    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ProductVO productVO = new ProductVO();
        bind(request, productVO);
        
        productService.updateProduct(productVO);
        request.setAttribute("prodNo", productVO.getProdNo());
        return new ModelAndView(this.getSuccess_update());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        ArrayList categoryList =
            (ArrayList) categoryService.getDropDownCategoryList();

        request.setAttribute("categoryList", categoryList);

        String prodNo = (String) request.getParameter("prodNo");

        if (prodNo == null) {
            prodNo = (String) request.getAttribute("prodNo");
        }

        ProductVO productVO = productService.getProduct(prodNo);

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("productVO", productVO);
        return new ProductVO();
    }

}
