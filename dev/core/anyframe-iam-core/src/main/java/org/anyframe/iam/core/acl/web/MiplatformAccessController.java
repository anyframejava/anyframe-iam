package org.anyframe.iam.core.acl.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anyframe.iam.core.acl.ExtBasePermission;
import org.anyframe.iam.core.acl.IViewResourceAccessService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.VariableList;

/**
 * MiplatformAccessController class that determine whether buttons will be displayed or not
 * 
 * @author youngmin.jo
 */
@Controller
public class MiplatformAccessController extends AbstractAccessController {
	private int defaultEncodeMethod = PlatformRequest.XML;
	private String defaultCharset = "UTF-8";

	/**
	 * Determine accessable with given viewResourceIds and permissionList.
	 * Them write to screen the result
	 * @param request
	 * 			HttpServletRequest
	 * @param response
	 * 			HttpServletResponse
	 * @throws Exception
	 * 			fail to check
	 */
	@RequestMapping("/mipAccessControl.do")
	public void checkAccessControl(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		// Request와 Response를 받아서 Parameter들을 받은 후 각 UI에 맞는 형태로 Output을 만들어 
		// 화면으로 전송하는 기본 API
		PlatformRequest platformRequest = new PlatformRequest(request, defaultCharset);			
		platformRequest.receiveData();
		
		String[] viewResourceIds = null;
		String[] evaledPermissionsString = null;
		String[] buttonIds = null;
		viewResourceIds = (String[])getParameterValues(platformRequest, "viewResourceIds");
		evaledPermissionsString = (String[])getParameterValues(platformRequest, "permissionList");
		buttonIds = (String[])getParameterValues(platformRequest, "buttonIds");
		
		ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		List resultList = new ArrayList();

		for (int i = 0; i < viewResourceIds.length; i++) {
			List requiredPermission = parsePermissionsString(ExtBasePermission.getPermissionMask(evaledPermissionsString[i]) + "");
			boolean result = ((IViewResourceAccessService) context.getBeansOfType(IViewResourceAccessService.class).values().iterator().next()).
								isGranted(viewResourceIds[i],requiredPermission);
			resultList.add(result);
		}
		outputAccessResult(response, buttonIds, resultList);
	}

	/**
	 * Get String[] from HttpServletRequest with given paramName
	 * 
	 * @param request
	 * 			HttpServletRequest
	 * @param paramName
	 * @throws Exception
	 * 			fail to get parameters
	 */
	@Override	
	public String[] getParameterValues(Object requestObject,
			String paramName) throws Exception {
		String[] resultArray = null;
		try {

			Dataset accessDataset = ((PlatformRequest)requestObject).getDatasetList().get("dsIamAccess");		
			int rowCount = accessDataset.getRowCount();
			resultArray = new String[rowCount];
	
			for (int i = 0; i < rowCount; i++)
				resultArray[i] = accessDataset.getColumnAsString(i, paramName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultArray;
	}

	/**
	 * Write to screen with given resultList and buttonIds as DatasetList
	 * @param response
	 * 			HttpServletResponse
	 * @param buttonIds
	 * 			String[] array of button Ids
	 * @param resultList
	 * 			List
	 * @throws IOException
	 * 			fail to write
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void outputAccessResult(HttpServletResponse response,
			String[] buttonIds, List resultList) throws IOException {
		DatasetList outDl = null;
		VariableList outVl = new VariableList();
		PlatformResponse platformResponse = new PlatformResponse(response,
				defaultEncodeMethod, defaultCharset);

		try {
			Dataset dsIamAccess = new Dataset("dsIamResult", defaultCharset);
			dsIamAccess.addColumn("buttonIds", ColumnInfo.COLTYPE_STRING, 256);
			dsIamAccess.addColumn("visible", ColumnInfo.COLTYPE_STRING, 100);

			for (int i = 0; i < resultList.size(); i++) {
				dsIamAccess.appendRow();
				dsIamAccess.setColumn(i, "buttonIds", buttonIds[i]);
				dsIamAccess.setColumn(i, "visible", resultList.get(i).toString());

			}
			outDl = new DatasetList();
			outDl.addDataset("dsIamResult", dsIamAccess);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			platformResponse.sendData(outVl, outDl);
		}
	}
}
