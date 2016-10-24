package org.anyframe.iam.core.acl.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anyframe.iam.core.acl.ExtBasePermission;
import org.anyframe.iam.core.acl.IViewResourceAccessService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Abstract class that determine whether button will be displayed or not
 * 
 * @author robben.kim
 *
 */
public abstract class AbstractAccessController {

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
	public abstract String[] getParameterValues(Object requestObject, String paramName) throws Exception;

	/**
	 * Write to screen with given resultList and buttonIds 
	 * @param response
	 * 			HttpServletResponse
	 * @param buttonIds
	 * 			String[] array of button ids
	 * @param resultList
	 * 			List
	 * @throws IOException
	 * 			fail to write
	 */
	@SuppressWarnings("unchecked")
	public abstract void outputAccessResult(HttpServletResponse response, String[] buttonIds, List resultList) throws Exception;

	/**
	 * Determine accessable with given viewResoueceIds and permissionList. Then, write to screen the result
	 * @param request
	 * 			HttpServletRequest
	 * @param response
	 * 			HttpServletResponse
	 * @throws Exception
	 * 			fail to check
	 */
	@SuppressWarnings("unchecked")
	public void checkAccessControl(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		// Request와 Response를 받아서 Parameter들을 받은 후 각 UI에 맞는 형태로 Output을 만들어 
		// 화면으로 전송하는 기본 API
		String[] viewResourceNames = null;
		String[] evaledPermissionsString = null;
		String[] buttonIds = null;
		viewResourceNames = (String[])getParameterValues(request, "viewResourceNames");
		evaledPermissionsString = (String[])getParameterValues(request, "permissionList");
		buttonIds = (String[])getParameterValues(request, "buttonIds");
		
		ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		List resultList = new ArrayList();

		for (int i = 0; i < viewResourceNames.length; i++) {
			List requiredPermission = parsePermissionsString(ExtBasePermission.getPermissionMask(evaledPermissionsString[i]) + "");
			boolean result = ((IViewResourceAccessService) context.getBeansOfType(IViewResourceAccessService.class).values().iterator().next()).
								isGranted(viewResourceNames[i],requiredPermission);
			resultList.add(result);
		}
		outputAccessResult(response, buttonIds, resultList);
	}

	/**
	 * Return list of split permission with comma
	 * 
	 * @param evaledPermissionsString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List parsePermissionsString(String evaledPermissionsString) {
		String[] parsePermissionsArr = evaledPermissionsString.replaceAll(" ","").split(",");
		List parsedPermissionsList = new ArrayList();

		for (int i = 0; i < parsePermissionsArr.length; i++) {
			parsedPermissionsList.add(new Integer(Integer
					.parseInt(parsePermissionsArr[i])));
		}

		return parsedPermissionsList;
	}
}
