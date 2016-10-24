package com.sds.emp.sale.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.CategoryVO;

/**
 * controller class for create category.
 * @author Heewon Jung
 */
public class AddCategoryController extends AnyframeFormController {

    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CategoryVO categoryVO = new CategoryVO();
        bind(request, categoryVO);
        
        // for test
        String userId = request.getParameter("userId");

        if (userId == null) {
            userId =
                SecurityContextHolder.getContext().getAuthentication()
                    .getName();
        }
        categoryVO.setRegId(userId);
        categoryService.addCategory(categoryVO);

        return new ModelAndView(this.getSuccess_add());

    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        return new CategoryVO();
    }

}
