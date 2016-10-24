/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package anyframe.iam.core.taglibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import anyframe.iam.core.acl.IViewResourceAccessService;

/**
 * 로그인한 사용자에 대한 view resource 퍼미션 맵핑 정보를 기반으로 조건으로 설정된 view resource 와 required
 * permission 을 만족하는지 체크하여 JSP 화면에 Tag Body 를 포함할지(버튼 영역을 그릴지)를 처리한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ViewResourceTag extends TagSupport {

	protected static final Log logger = LogFactory.getLog(ViewResourceTag.class);

	private ApplicationContext context;

	private IViewResourceAccessService viewResourceAccessService;

	private String viewResourceId;

	private String hasPermission = "";

	public String getViewResourceId() {
		return viewResourceId;
	}

	public void setViewResourceId(String viewResourceId) {
		this.viewResourceId = viewResourceId;
	}

	public String getHasPermission() {
		return hasPermission;
	}

	/**
	 * 체크할 required permission 을 bit mask (숫자) 로 설정한다. ex.) READ-1 WRITE-2
	 * READ,WRITE-3 CREATE-4 .. 체크하고자 하는 permissions 정보가 다수인 경우
	 * hasPermission="3,16" 과 같이 ,로 구분하여 입력하면 된다. 또한
	 * hasPermission="${permissions} 와 같이 EL 표현식을 지원한다.
	 * 
	 * @param hasPermission
	 */
	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	/**
	 * viewResourceAccessService 를 사용하여 사용자에 대해 현재 view resource 에 대한 지시된
	 * permission 을 만족하는지 확인하여 Tag Body 에 대한 INCLUDE/SKIP 을 결정한다. <br/>
	 */
	public int doStartTag() throws JspException {
		initializeIfRequired();

		try {

			final String evaledPermissionsString = ExpressionEvaluationUtils.evaluateString("hasPermission",
					hasPermission, pageContext);

			List requiredPermissionList = parsePermissionsString(evaledPermissionsString);

			boolean result = viewResourceAccessService.isGranted(viewResourceId, requiredPermissionList);

			return result ? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;

		}
		catch (Exception e) {
			return Tag.SKIP_BODY;
		}

	}

	/**
	 * , 로 구분된 permission 들을 분리하여 List 형태로 되돌려준다.
	 * 
	 * @param evaledPermissionsString
	 * @return
	 */
	protected List parsePermissionsString(String evaledPermissionsString) {
		String[] parsePermissionsArr = evaledPermissionsString.replaceAll(" ", "").split(",");
		List parsePermissionsList = new ArrayList();

		for (int i = 0; i < parsePermissionsArr.length; i++) {
			parsePermissionsList.add(new Integer(Integer.parseInt(parsePermissionsArr[i])));
		}

		return parsePermissionsList;
	}

	/**
	 * Spring 의 context 를 찾는다.
	 * 
	 * @return
	 */
	protected ApplicationContext getContext() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	}

	/**
	 * 태그의 초기화 로직으로 Spring context 를 구해 viewResourceAccessService bean 을 사용 가능토록
	 * 준비한다.
	 * 
	 * @throws JspException
	 */
	protected void initializeIfRequired() throws JspException {
		if (context != null) {
			return;
		}

		Map beanMap = getContext().getBeansOfType(IViewResourceAccessService.class);
		if (beanMap.size() > 0) {
			viewResourceAccessService = (IViewResourceAccessService) beanMap.values().iterator().next();
		}
		else {
			throw new JspException("viewResourceAccessService bean was not found in application context");
		}
	}

}
