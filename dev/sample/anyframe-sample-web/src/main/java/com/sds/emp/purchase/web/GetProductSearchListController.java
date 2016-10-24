package com.sds.emp.purchase.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.SearchVO;

public class GetProductSearchListController extends AnyframeFormController {

    private IPropertiesService propertiesService = null;
    private ProductService productService = null;

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public void setProductService(
            ProductService productService) {
        this.productService = productService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        Page resultPage = productService.getProductList(searchVO);
        int pageSize = propertiesService.getInt("PAGE_SIZE");

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("page", resultPage);
        request.setAttribute("pageSize", new Integer(pageSize));

        return new ModelAndView(this.getSuccess_list());
    }

}
