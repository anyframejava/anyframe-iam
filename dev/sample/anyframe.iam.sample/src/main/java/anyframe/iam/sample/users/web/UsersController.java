package anyframe.iam.sample.users.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import anyframe.iam.sample.users.service.UsersService;
import anyframe.iam.sample.domain.Users;
 
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.web.servlet.ModelAndView;
import anyframe.web.springmvc.controller.AnyframeFormController;
import anyframe.common.util.StringUtil;
import anyframe.common.util.SearchVO;
import anyframe.common.Page;

public class UsersController extends AnyframeFormController {
    private UsersService usersService;

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }
    
    /**
     * display add users form.
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView addView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setAttribute("users", new Users());  
    	        	
        return new ModelAndView(this.getSuccess_addView());                   
    }

    /**
     * add users
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView add(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Users users = new Users();   
        bind(request, users);        
       
        usersService.create(users);
        
        return new ModelAndView(this.getSuccess_add());        
    }

    /**
     * get a users detail.
     * @param request
     * @param response
     * @return 
     * @throws Exception
     */
    public ModelAndView get(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
 		
       String userId = request.getParameter("userId");

       if (!StringUtils.isBlank(userId)) {
       		Users gettedUsers = usersService.get(new String(userId));        	
        	request.setAttribute("users", gettedUsers);        	
       }
        
       return new ModelAndView(this.getSuccess_get());
    }

    /**
     * update users
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
        Users users = new Users();        
        bind(request, users);

        usersService.update(users);
            	
        return new ModelAndView(this.getSuccess_update());
    }

    /**
     * display users list
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        String pageParam = (new ParamEncoder("usersList").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        String pageParamValue = request.getParameter(pageParam);
        int pageIndex = StringUtil.isNotEmpty(pageParamValue) ? (Integer.parseInt(pageParamValue)) : 1;       
        searchVO.setPageIndex(pageIndex);
        
        Page resultPage = usersService.getPagingList(searchVO);

        request.setAttribute("search", searchVO); 
        request.setAttribute("usersList", resultPage.getList());
        request.setAttribute("size", resultPage.getTotalCount());
        request.setAttribute("pagesize", resultPage.getPagesize());
        request.setAttribute("pageunit", resultPage.getPageunit());
        
        return new ModelAndView(this.getSuccess_list());        
    }

    /**
     * delete users
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView delete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	                       
        Users users = new Users();
        bind(request, users);           
        usersService.remove(users.getUserId());
		
        return new ModelAndView(this.getSuccess_delete());
    }         


}
