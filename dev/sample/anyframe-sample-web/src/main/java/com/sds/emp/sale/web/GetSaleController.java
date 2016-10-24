package com.sds.emp.sale.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.SaleService;
import com.sds.emp.sale.services.SaleVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * controller class for get detail of sale.
 * @author Heewon Jung
 */
public class GetSaleController extends AnyframeFormController {

    private SaleService saleService = null;

    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SaleVO saleVO = new SaleVO();
        bind(request, saleVO);

        saleVO = saleService.getSale(saleVO.getProdNo());

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("saleVO", saleVO);
        return new ModelAndView(this.getSuccess_get());
    }

}
