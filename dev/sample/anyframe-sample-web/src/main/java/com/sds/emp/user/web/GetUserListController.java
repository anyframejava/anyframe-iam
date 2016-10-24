package com.sds.emp.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.user.services.SearchVO;
import com.sds.emp.user.services.UserService;

public class GetUserListController extends AnyframeFormController {

    private UserService userService = null;
    private IPropertiesService propertiesService = null;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);
        
        Page resultPage = userService.getUserList(searchVO);

        int pageSize = propertiesService.getInt("PAGE_SIZE");

        request.setAttribute("page", resultPage);
        request.setAttribute("pageSize", new Integer(pageSize));
        request.setAttribute("searchVO", searchVO);

        return new ModelAndView(this.getSuccess_list());
    }

}
