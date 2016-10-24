package com.sds.emp.purchase.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.purchase.services.DeliveryCompanyService;
import com.sds.emp.purchase.services.PurchaseService;
import com.sds.emp.purchase.services.PurchaseVO;
import com.sds.emp.purchase.services.SearchVO;

public class GetPurchaseController extends AnyframeFormController {

    private PurchaseService purchaseService = null;
    private CodeService codeService = null;
    private DeliveryCompanyService deliveryCompanyService = null;

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
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
        
        PurchaseVO purchaseVO = new PurchaseVO();
        bind(request, purchaseVO);
        
        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);
        
        purchaseVO = purchaseService.getPurchase(purchaseVO.getProdNo());

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("purchaseVO", purchaseVO);
        request.setAttribute("paymentOptionList", paymentOptionList);
        request.setAttribute("deliveryCompanyList", deliveryCompanyList);

        return new ModelAndView(this.getSuccess_get());

    }
}
