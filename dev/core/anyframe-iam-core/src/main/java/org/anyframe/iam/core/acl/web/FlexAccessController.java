package org.anyframe.iam.core.acl.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * FlexAccessController class that determine whether button will be displayed or not
 * 
 * @author robben.kim
 *
 */
@Controller
public class FlexAccessController extends AbstractAccessController {

	/**
	 * Determine accessable with given viewResourceIds and permissionList. then write to screen the result
	 * @param request
	 * 			HttpServletRequest
	 * @param response
	 * 			HttpServletResponse
	 * @throws Exception
	 * 			fail to check
	 */
	@RequestMapping("/iam/flexAccessControl.do")
	public void checkAccessControl(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		super.checkAccessControl(request, response);
	}

	/**
	 * Get String[] from HttpServletRequest with given paramName
	 * 
	 * @param request
	 * 			HttpServletRequest
	 * @param paramName
	 * @return
	 * @throws Exception
	 * 			fail to get parameters
	 */
	@Override
	public String[] getParameterValues(Object requestObject, String paramName) 
	 throws Exception {
		return (String[])((HttpServletRequest)requestObject).getParameterValues(paramName);
	}
	
	/**
	 * Write to screen with given resultList and buttonIds as XML form 
	 * @param response
	 * 			HttpServletResponse
	 * @param buttonIds
	 * 			String[] array of button ids
	 * @param resultList
	 * 			List
	 * @throws IOException
	 * 			fail to write
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void outputAccessResult(HttpServletResponse response, String[] buttonIds, List resultList) 
			 throws IOException {
		
		// Flex에서의 UI 제어를 위해 결과값을 <buttonId> true , false <buttonIds> 형태의 XML로 가공한다.
		response.setContentType("application/xml; charset=UTF-8");
		response.setHeader("Pragma", "No-Cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		response.getWriter().println("<iamAccess>\n");

		for (int i = 0; i < resultList.size(); i++) {
			response.getWriter().write("<" +  buttonIds[i] + ">" + resultList.get(i) + "</" + buttonIds[i] + ">\n");
		}

		response.getWriter().println("</iamAccess>");
	}
}
