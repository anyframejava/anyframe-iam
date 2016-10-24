package org.anyframe.iam.core.acl.web;

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

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataSetList;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformRequest;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;
import com.tobesoft.xplatform.tx.PlatformType;

@Controller
public class XPlatformAccessController extends AbstractAccessController{
	private String defaultEncodeMethod = PlatformType.CONTENT_TYPE_XML; // 기본 XML
	private String defaultCharset = PlatformType.DEFAULT_CHAR_SET;
	
	@RequestMapping("/xpAccessControl.do")
	@SuppressWarnings("unchecked")
	public void checkAccessControl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpPlatformRequest httpPlatformRequest = new HttpPlatformRequest(request, defaultEncodeMethod, defaultCharset);
		httpPlatformRequest.receiveData();
		
		String[] viewResourceNames = null;
		String[] evaledPermissionsString = null;
		String[] buttonIds = null;
		
		viewResourceNames = (String[])getParameterValues(httpPlatformRequest, "viewResourceNames");
		evaledPermissionsString = (String[])getParameterValues(httpPlatformRequest, "permissionList");
		buttonIds = (String[])getParameterValues(httpPlatformRequest, "buttonIds");
		
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
	
	@Override
	public String[] getParameterValues(Object requestObject, String paramName) throws Exception{
		String[] resultArray = null;
		try{
			PlatformData inPlatformData = ((HttpPlatformRequest) requestObject).getData();
			DataSet accessDataSet = inPlatformData.getDataSetList().get("dsIamAccess");
			int rowCount = accessDataSet.getRowCount();
			resultArray = new String[rowCount];
			
			for(int i = 0 ; i < rowCount ; i++)
				resultArray[i] = accessDataSet.getString(i, paramName);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return resultArray; 
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void outputAccessResult(HttpServletResponse response, String[] buttonIds, List resultList) throws Exception{
		DataSetList outDl = null;
		VariableList outVl = new VariableList();
		HttpPlatformResponse platformResponse = new HttpPlatformResponse(response, defaultEncodeMethod, defaultCharset);
		
		try{
			DataSet dsIamAccess = new DataSet("dsIamResult", defaultCharset);
			dsIamAccess.addColumn("buttonIds", ColumnInfo.COLTYPE_STRING, 256);
			dsIamAccess.addColumn("visible", ColumnInfo.COLTYPE_STRING, 100);
			
			for(int i = 0 ; i < resultList.size() ; i++){
				dsIamAccess.newRow();
				dsIamAccess.set(i, "buttonIds", buttonIds[i]);
				dsIamAccess.set(i, "visible", resultList.get(i).toString());
			}
			outDl = new DataSetList();
			outDl.add(dsIamAccess);
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			PlatformData outPlatformData = new PlatformData();
			outPlatformData.setDataSetList(outDl);
			outPlatformData.setVariableList(outVl);
			platformResponse.setData(outPlatformData);
			platformResponse.sendData();
		}
	}
}
