package com.sds.emp.user.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.user.services.UserService;
import com.sds.emp.user.services.UserVO;

/**
 * controller class for create user.
 * @author Heewon Jung
 */
public class AddUserController extends AnyframeFormController {

    private UserService userService = null;
    private CodeService codeService = null;

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
        
//        MultipartHttpServletRequest multipartRequest =
//            (MultipartHttpServletRequest) request;
//
//        MultipartFile picturefile = multipartRequest.getFile("realImageFile");
//
//        String pictureName = "";
//        String picturefileName = "";
//        String pictureExt = "";
//
//        if (picturefile != null
//            && !picturefile.getOriginalFilename().equals("")) {
//            pictureName = picturefile.getOriginalFilename();
//
//            if (pictureName.toLowerCase().endsWith("jpg")) {
//                pictureExt = "jpg";
//            } else if (pictureName.toLowerCase().endsWith("gif")) {
//                pictureExt = "gif";
//            }
//
//            if (!pictureExt.equals("")) {
//                picturefileName = "." + pictureExt;
//            } else {
//                picturefileName = pictureExt;
//            }
//            String destDir = getServletContext().getRealPath("/upload/");
//            File dirPath = new File(destDir);
//
//            if (!dirPath.exists()) {
//                dirPath.mkdirs();
//            }
//
//            File destination =
//                File.createTempFile("file", picturefileName, dirPath);
//
//            FileCopyUtils.copy(picturefile.getInputStream(), new FileOutputStream(
//                destination));
//            userVO.setImageFile(destination.getAbsolutePath());
//            
//        }
      
        userService.addUser(userVO);
        request.setAttribute("userVO", userVO);
        return new ModelAndView("login.do");
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        ArrayList cellPhoneCodeList = (ArrayList)codeService.getCodeList("A01");
        ArrayList emailCodeList = (ArrayList)codeService.getCodeList("A02");
        request.setAttribute("cellPhoneCodeList", cellPhoneCodeList);
        request.setAttribute("emailCodeList", emailCodeList);
        return new UserVO();
    }
}
