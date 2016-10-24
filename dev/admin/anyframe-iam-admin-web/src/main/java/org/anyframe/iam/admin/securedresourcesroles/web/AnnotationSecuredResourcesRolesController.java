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

package org.anyframe.iam.admin.securedresourcesroles.web;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.anyframe.iam.admin.common.web.JsonError;
import org.anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import org.anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Annotation Secured_Resources_Roles Mapping Controller
 * @author youngmin.jo
 * 
 */
@Controller
public class AnnotationSecuredResourcesRolesController {

	@Resource(name = "securedResourcesService")
	private SecuredResourcesService securedResourcesService;

	@Resource(name = "securedResourcesRolesService")
	private SecuredResourcesRolesService securedResourcesRolesService;

	@Resource(name = "contextProperties")
	private Properties contextProperties;

	/**
	 * Add Resource-Role mapping data
	 * @param resourceIds array of resources Ids
	 * @param roleId Role Id
	 * @return jsonView
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/securedresourcesroles/add.do")
	public String add(@RequestParam("resourceId") String[] resourceIds, @RequestParam("roleId") String roleId)
			throws Exception {

		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, roleId);

		return "forward:/resources/list.do";
	}

	/**
	 * Delete Resource-Role mapping data
	 * @param resourceIds array of Resource Ids
	 * @param roleId Role Id
	 * @return move to "resources/list.do"
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/securedresourcesroles/delete.do")
	public String delete(@RequestParam("resourceIds") String[] resourceIds, @RequestParam("roleId") String roleId)
			throws Exception {

		securedResourcesRolesService.deleteSecuredResourceRoles(resourceIds, roleId);
		return "forward:/resources/list.do";
	}

	/**
	 * move to role-resource mapping page
	 * @param model
	 * 				Model object to add attributes
	 * @return move to "role/roleresourcemapping"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/securedresourcesroles/addView.do")
	public String addView(Model model) throws Exception {
		model.addAttribute("isOracle", contextProperties.getProperty("hibernate.dialect", "undefined").contains("Oracle"));
		return "/roles/roleresourcemapping";
	}

	/**
	 * move to pop-up menu
	 * @return move to "/role/resourcelist"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/securedresourcesroles/listPopUp.do")
	public String listPopUp() throws Exception {
		return "/roles/resourcelist";
	}

	/**
	 * make data of Resource list
	 * @param searchVO an object that contains search conditions
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/securedresourcesroles/listData.do")
	public String listData(HttpSession session, ResourceSearchVO searchVO, Model model) throws Exception {
		String systemName = (String) session.getAttribute("systemName");
		
		searchVO.setSystemName(systemName);
		Page resultPage = securedResourcesService.getMappedList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * make list of resources that matches the given role Id and all its child
	 * roles
	 * @param searchVO an object that contains search conditions
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make list
	 * 
	 */
	@JsonError
	@RequestMapping("/securedresourcesroles/listDataWithLevel.do")
	public String listDataWithLevel(HttpSession session, ResourceSearchVO searchVO, Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		Page resultPage = securedResourcesService.getListwithLevel(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * find list of resources that matches the given search condition and does
	 * not exist in Secured_Resourcess_Roles table
	 * @param searchVO an object that contains search conditions
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make list
	 */
	@JsonError
	@RequestMapping("/securedresourcesroles/listDataUnmapped.do")
	public String listDataUnmapped(HttpSession session, ResourceSearchVO searchVO, Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);

		Page resultPage = securedResourcesService.getUnmappedList(searchVO);
		
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}
	
	@JsonError
	@RequestMapping("/securedresourcesroles/listDataUnmappedAnyRole.do")
	public String listDataUnmappedAnyRole(HttpSession session, ResourceSearchVO searchVO, Model model) throws Exception{
		
		Page resultPage = securedResourcesService.getUnmappedAnyRoleList(searchVO);
		
		model.addAttribute("page", resultPage.getCurrentPage());
		model.addAttribute("total", resultPage.getMaxPage());
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());
		
		return "jsonView";
	}

}
