package com.sds.emp.sale.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.CategoryVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * controller class for modify category.
 * @author Heewon Jung
 */
public class UpdateCategoryController extends AnyframeFormController {

    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        this.getLogger().debug("\n \n ##########process Call#############");
        CategoryVO categoryVO = new CategoryVO();
        bind(request, categoryVO);
        categoryService.updateCategory(categoryVO);
        return new ModelAndView(this.getSuccess_update());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        this.getLogger().debug("\n \n ##########formBakingObject Call#############");

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        CategoryVO categoryVO = categoryService.getCategory(request.getParameter("categoryNo"));

        request.setAttribute("searchVO", searchVO);
        request.setAttribute("categoryVO", categoryVO);
        return new CategoryVO();
    }
}
