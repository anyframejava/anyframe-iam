package com.sds.emp.sale.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.SaleService;
import com.sds.emp.sale.services.SaleVO;

/**
 * controller class for modify transtatus code.
 * @author Heewon Jung
 */
public class UpdateTranStatusCodeController extends AnyframeFormController {

    private SaleService saleService = null;

    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SaleVO saleVO = new SaleVO();
        bind(request, saleVO);
        
        String prodNo = request.getParameter("prodNo");
        saleVO.setProdNo(prodNo);
        saleVO.setTranStatusCode("003");
        saleService.updateTranStatusCode(saleVO);
        return new ModelAndView(this.getSuccess_update());
    }

}
