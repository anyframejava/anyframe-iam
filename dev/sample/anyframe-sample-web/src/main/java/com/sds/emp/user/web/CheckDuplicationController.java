package com.sds.emp.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.user.services.UserService;
import com.sds.emp.user.services.UserVO;

/**
 * controller class for check duplicated user's ID.
 * @author Heewon Jung
 */
public class CheckDuplicationController extends AnyframeFormController {

    private UserService userService = null;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        UserVO userVO = new UserVO();
        bind(request, userVO);
        String userId = userVO.getUserId();
        boolean duplicated = userService.checkDuplication(userId);
        
        request.setAttribute("userVO", userVO);
        request.setAttribute("duplicated", Boolean.valueOf(duplicated));
        request.setAttribute("userId", userId);
        return new ModelAndView("/sample/user/checkDuplication.jsp");
    }

}
