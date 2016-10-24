package org.anyframe.iam.sample.category.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anyframe.datatype.SearchVO;
import org.anyframe.iam.sample.category.service.CategoryService;
import org.anyframe.iam.sample.domain.Category;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.controller.AnyframeFormController;

public class CategoryController extends AnyframeFormController {
    private CategoryService categoryService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    /**
     * display add category form.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setAttribute("category", new Category());  
    	        	
        return new ModelAndView(this.getSuccess_addView());                   
    }

    /**
     * add category
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView add(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Category category = new Category();   
        bind(request, category);        
       
        categoryService.create(category);
        
        return new ModelAndView(this.getSuccess_add());        
    }

    /**
     * get a category detail.
     * @param request
     * @param response
     * @return 
     * @throws Exception
     */
    public ModelAndView get(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
 		
       String categoryNo = request.getParameter("categoryNo");

       if (!StringUtils.isBlank(categoryNo)) {
       		Category gettedCategory = categoryService.get(new String(categoryNo));        	
        	request.setAttribute("category", gettedCategory);        	
       }
        
       return new ModelAndView(this.getSuccess_get());
    }

    /**
     * update category
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
        Category category = new Category();        
        bind(request, category);

        categoryService.update(category);
            	
        return new ModelAndView(this.getSuccess_update());
    }

    /**
     * display category list
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        String pageParam = (new ParamEncoder("categoryList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        String pageParamValue = request.getParameter(pageParam);
        int pageIndex = StringUtil.isNotEmpty(pageParamValue) ? (Integer.parseInt(pageParamValue)) : 1;       
        searchVO.setPageIndex(pageIndex);
        
        Page resultPage = categoryService.getPagingList(searchVO);

        request.setAttribute("search", searchVO); 
        request.setAttribute("categoryList", resultPage.getList());
        request.setAttribute("size", resultPage.getTotalCount());
        request.setAttribute("pagesize", resultPage.getPagesize());
        request.setAttribute("pageunit", resultPage.getPageunit());
        
        return new ModelAndView(this.getSuccess_list());        
    }

    /**
     * delete category
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	                       
        Category category = new Category();
        bind(request, category);           
        categoryService.remove(category.getCategoryNo());
		
        return new ModelAndView(this.getSuccess_delete());
    }         


}
