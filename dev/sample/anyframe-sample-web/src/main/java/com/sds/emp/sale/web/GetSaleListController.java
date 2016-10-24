package com.sds.emp.sale.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.SaleService;
import com.sds.emp.sale.services.SearchVO;

/**
 * controller class for get list of sale.
 * @author Heewon Jung
 */
public class GetSaleListController extends AnyframeFormController {

    private IPropertiesService propertiesService = null;

    private SaleService saleService = null;

    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        SearchVO categorySearchVO = new SearchVO();
        categorySearchVO.setSearchUseYn("A");
        List categoryList =
            (List)categoryService.getCategoryListAll(categorySearchVO);
        request.setAttribute("categoryList", categoryList);

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);
        String userId =
            EmpUtil.null2str(request.getSession().getAttribute("userId"));
        searchVO.setSearchSellerId(userId);
        Page resultPage = saleService.getSaleList(searchVO);

        int pageSize = propertiesService.getInt("PAGE_SIZE");

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("page", resultPage);
        request.setAttribute("pageSize", new Integer(pageSize));
        return new ModelAndView(this.getSuccess_list());
    }

}
