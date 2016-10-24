package com.sds.emp.purchase.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.purchase.services.DeliveryCompanyService;
import com.sds.emp.purchase.services.PurchaseService;
import com.sds.emp.purchase.services.PurchaseVO;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

public class AddPurchaseController extends AnyframeFormController {

    private PurchaseService purchaseService = null;

    private ProductService productService = null;
    private CodeService codeService = null;
    private DeliveryCompanyService deliveryCompanyService = null;

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

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
        PurchaseVO purchaseVO = new PurchaseVO();
        bind(request, purchaseVO);
        String userId =
            SecurityContextHolder.getContext().getAuthentication().getName();
        purchaseVO.setBuyerId(userId);

        purchaseService.addPurchase(purchaseVO);
        return new ModelAndView(this.getSuccess_add());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);
        
        ArrayList paymentOptionList = (ArrayList)codeService.getCodeList("A04");

        ArrayList deliveryCompanyList =
            (ArrayList) deliveryCompanyService.getDropDownDeliveryCompanyList();
        
        String prodNo = request.getParameter("prodNo");
        ProductVO productVO =
            productService.getProduct(prodNo);

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("productVO", productVO);
        request.setAttribute("paymentOptionList", paymentOptionList);
        request.setAttribute("deliveryCompanyList", deliveryCompanyList);
        return new PurchaseVO();
    }

}
