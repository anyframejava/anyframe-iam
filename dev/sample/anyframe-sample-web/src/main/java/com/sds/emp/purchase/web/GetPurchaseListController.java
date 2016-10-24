package com.sds.emp.purchase.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.purchase.services.PurchaseService;
import com.sds.emp.purchase.services.SearchVO;

public class GetPurchaseListController extends AnyframeFormController {

    private IPropertiesService propertiesService = null;
    private PurchaseService purchaseService = null;

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    protected ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        String userId =
            SecurityContextHolder.getContext().getAuthentication().getName();
        searchVO.setSearchBuyerId(userId);

        Page resultPage = purchaseService.getPurchaseList(searchVO);

        int pageSize = propertiesService.getInt("PAGE_SIZE");

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("page", resultPage);
        request.setAttribute("pageSize", new Integer(pageSize));
        return new ModelAndView(this.getSuccess_list());
    }

}
