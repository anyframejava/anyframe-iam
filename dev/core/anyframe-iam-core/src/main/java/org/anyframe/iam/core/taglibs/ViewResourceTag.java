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
package org.anyframe.iam.core.taglibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.anyframe.iam.core.acl.IViewResourceAccessService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * This class check required permission with view resource based on permission mapping information of view resource
 * about currently log-in user. If user have permission, draw Tag body in given JSP page.
 *  
 * @author Byunghun Woo
 * 
 * view resource id 기반으로 동작하던 iam:access 태그를 view name 기반으로 수정
 * 그에 따른 쿼리문 수정
 * edited by youngmin.jo
 */
public class ViewResourceTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	protected static final Log logger = LogFactory.getLog(ViewResourceTag.class);

	private ApplicationContext context;

	private IViewResourceAccessService viewResourceAccessService;

	private String viewName;

	private String hasPermission = "";

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getHasPermission() {
		return hasPermission;
	}

	/**
	 * Setting required permission to bit mask. ex) READ-1 WRITE-2,
	 * If permission information is not one, 
	 * then separate information with comma such as 'hasPermission="3,16"'
	 * This method also support EL expression. ex) hasPermission="${permissions}"
	 * 
	 * @param hasPermission
	 */
	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	/**
	 * Check if user has permission with current view resource by using viewResourceAccessService.
	 * INCLUDE if user has permission, SKIP doesn't have.
	 */
	public int doStartTag() throws JspException {
		initializeIfRequired();

		try {

			final String evaledPermissionsString = ExpressionEvaluationUtils.evaluateString("hasPermission",
					hasPermission, pageContext);

			List requiredPermissionList = parsePermissionsString(evaledPermissionsString);

			boolean result = viewResourceAccessService.isGranted(viewName, requiredPermissionList);

			return result ? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;

		}
		catch (Exception e) {
			return Tag.SKIP_BODY;
		}

	}

	/**
	 * Return list of split permission with comma 
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
	 * find context of Spring
	 * 
	 * @return
	 */
	protected ApplicationContext getContext() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	}

	/**
	 * As initialize logic of tag, get Spring context to use viewResourceAccessService bean.
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
