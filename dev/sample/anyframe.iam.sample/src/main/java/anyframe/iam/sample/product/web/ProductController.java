package anyframe.iam.sample.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import anyframe.iam.sample.product.service.ProductService;
import anyframe.iam.sample.domain.Product;
 
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.web.servlet.ModelAndView;
import anyframe.web.springmvc.controller.AnyframeFormController;
import anyframe.common.util.StringUtil;
import anyframe.common.util.SearchVO;
import anyframe.common.Page;

public class ProductController extends AnyframeFormController {
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * display add product form.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setAttribute("product", new Product());  
    	        	
        return new ModelAndView(this.getSuccess_addView());                   
    }

    /**
     * add product
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView add(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Product product = new Product();   
        bind(request, product);        
       
        productService.create(product);
        
        return new ModelAndView(this.getSuccess_add());        
    }

    /**
     * get a product detail.
     * @param request
     * @param response
     * @return 
     * @throws Exception
     */
    public ModelAndView get(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
 		
       String prodNo = request.getParameter("prodNo");

       if (!StringUtils.isBlank(prodNo)) {
       		Product gettedProduct = productService.get(new String(prodNo));        	
        	request.setAttribute("product", gettedProduct);        	
       }
        
       return new ModelAndView(this.getSuccess_get());
    }

    /**
     * update product
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
        Product product = new Product();        
        bind(request, product);

        productService.update(product);
            	
        return new ModelAndView(this.getSuccess_update());
    }

    /**
     * display product list
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        String pageParam = (new ParamEncoder("productList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        String pageParamValue = request.getParameter(pageParam);
        int pageIndex = StringUtil.isNotEmpty(pageParamValue) ? (Integer.parseInt(pageParamValue)) : 1;       
        searchVO.setPageIndex(pageIndex);
        
        Page resultPage = productService.getPagingList(searchVO);

        request.setAttribute("search", searchVO); 
        request.setAttribute("productList", resultPage.getList());
        request.setAttribute("size", resultPage.getTotalCount());
        request.setAttribute("pagesize", resultPage.getPagesize());
        request.setAttribute("pageunit", resultPage.getPageunit());
        
        return new ModelAndView(this.getSuccess_list());        
    }

    /**
     * delete product
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	                       
        Product product = new Product();
        bind(request, product);           
        productService.remove(product.getProdNo());
		
        return new ModelAndView(this.getSuccess_delete());
    }         


}
