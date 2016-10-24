package com.sds.emp.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.user.services.SearchVO;
import com.sds.emp.user.services.UserService;
import com.sds.emp.user.services.UserVO;

public class UpdateUserController extends AnyframeFormController {

    private UserService userService = null;

    private CodeService codeService = null;

    private String role = null;

    public void setRole(String flag) {
        this.role = flag;
    }

    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        UserVO userVO = new UserVO();
        bind(request, userVO);
        userService.updateUser(userVO);

        return new ModelAndView(this.getSuccess_update(), "userVO", userVO);

    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        //ArrayList cellPhoneCodeList = (ArrayList)codeService.getCodeList("A01");
        //ArrayList emailCodeList = (ArrayList)codeService.getCodeList("A02");
        String userId = request.getParameter("userId");

        if (userId == null) {
            userId =
                SecurityContextHolder.getContext().getAuthentication()
                    .getName();
        }
        UserVO userVO = userService.getUser(userId);

        SearchVO searchVO = new SearchVO();
        bind(request, searchVO);

        if (request.getParameter("pageIndex") == null)
            searchVO.setPageIndex((new Integer(1)).intValue());
        else
            searchVO.setPageIndex((new Integer(request
                .getParameter("pageIndex")).intValue()));
        
        request.setAttribute("searchVO", searchVO);
        //request.setAttribute("cellPhoneCodeList", cellPhoneCodeList);
        //request.setAttribute("emailCodeList", emailCodeList);
        request.setAttribute("userVO", userVO);
        request.setAttribute("flag", role);
        return new UserVO();
    }
}
