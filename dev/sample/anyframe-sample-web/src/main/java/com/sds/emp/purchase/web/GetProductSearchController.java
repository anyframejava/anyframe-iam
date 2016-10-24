package com.sds.emp.purchase.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.purchase.services.DeliveryCompanyService;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

public class GetProductSearchController extends AnyframeFormController {

    private ProductService productService = null;
    private CodeService codeService = null;
    private DeliveryCompanyService deliveryCompanyService = null;

    public void setProductService(
            ProductService productService) {
        this.productService = productService;
    }

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public void setDeliveryCompanyService(
            DeliveryCompanyService deliveryCompanyService) {
        this.deliveryCompanyService = deliveryCompanyService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ArrayList paymentOptionList = (ArrayList)codeService.getCodeList("A04");

        ArrayList deliveryCompanyList =
            (ArrayList) deliveryCompanyService.getDropDownDeliveryCompanyList();

        ProductVO productVO = new ProductVO();
        bind(request, productVO);
        
        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);
        
        productVO = productService.getProduct(productVO.getProdNo());

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("productVO", productVO);
        request.setAttribute("paymentOptionList", paymentOptionList);
        request.setAttribute("deliveryCompanyList", deliveryCompanyList);
        
        return new ModelAndView(this.getSuccess_get());
    }

}
