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

package org.anyframe.iam.admin.securedresources.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.anyframe.iam.admin.candidatesecuredresources.service.CandidateSecuredResourcesService;
import org.anyframe.iam.admin.common.web.JsonError;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * Annotation Resources Controller
 * @author youngmin.jo
 * 
 */
@Controller
@SessionAttributes("resources")
public class AnnotationResourcesController {

	@Resource(name = "securedResourcesService")
	private SecuredResourcesService securedResourcesService;

	@Resource(name = "candidateSecuredResourcesService")
	private CandidateSecuredResourcesService candidateSecuredResourcesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * make data of resource list
	 * @param searchVO an object that contains search conditions
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 * 
	 */
	@JsonError
	@RequestMapping("/resources/listData.do")
	public String listData(
			HttpSession session,
			ResourceSearchVO searchVO, Model model) throws Exception {

		String systemName = (String)session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		
		Page resultPage = securedResourcesService.getList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";

	}

	/**
	 * move to resource adding page
	 * @param searchVO an object that contains search conditions
	 * @param model an object to add attributes
	 * @return move to "/resources/resourcedetail"
	 * @throws Exception fail to move to the page
	 * 
	 */
	@RequestMapping("/resources/addView.do")
	public String addView(
			HttpSession session,
			@ModelAttribute("searchVO") ResourceSearchVO searchVO, 
			Model model) throws Exception {
		
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		String beanid = candidateSecuredResourcesService.findMethodParam(systemName[0]);
		model.addAttribute("systemNames", systemName);
		model.addAttribute("beanid", beanid);
		model.addAttribute("resources", new SecuredResources());
		return "/resources/resourcedetail";
	}

	/**
	 * move to resource list page
	 * @return move to "/resources/resourcelist"
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/resources/list.do")
	public String list() throws Exception {
		return "/resources/resourcelist";
	}

	/**
	 * Delete the given Resources in grid component
	 * @param resourceIds array of resource Ids that want to be deleted
	 * @param status SessionStatus object to block double submit
	 * @return move to "resources/listData.do"
	 * @throws Exception fail to delete resources
	 */
	@JsonError
	@RequestMapping("/resources/delete.do")
	public String delete(@RequestParam("resourceId") String[] resourceIds, SessionStatus status) throws Exception {

		securedResourcesService.delete(resourceIds);
		status.setComplete();

		return "forward:/resources/listData.do";
	}

	/**
	 * Delete the given resource in detail page
	 * @param resourceIds array of resource Ids that want to be deleted
	 * @param status SessionStatus object to block double submit
	 * @return move to "/resources/list.do"
	 * @throws Exception fail to delete Resource
	 */
	@RequestMapping("/resources/deleteFromDetail.do")
	public String deleteFromDetail(@RequestParam("resourceId") String[] resourceIds, SessionStatus status)
			throws Exception {

		securedResourcesService.delete(resourceIds);
		status.setComplete();

		return "forward:/resources/list.do";
	}

	/**
	 * get a resource that matches the given resource Id
	 * @param resourceId Resource Id
	 * @param model an object to add attributes
	 * @return move to "/resources/resourcedetail"
	 * @throws Exception fail to get a resource
	 */
	@RequestMapping("/resources/get.do")
	public String get(
			HttpSession session,
			@RequestParam(value = "resourceId", required = false) String resourceId, 
			Model model) throws Exception {
		
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemName);

		if (!StringUtils.isBlank(resourceId)) {
			SecuredResources sr = securedResourcesService.get(resourceId);
			model.addAttribute("resources", sr);
		}
		String beanid = candidateSecuredResourcesService.findMethodParam(systemName[0]);
		model.addAttribute("beanid", beanid);
		return "/resources/resourcedetail";
	}

	/**
	 * add Resource data
	 * @param sr SecuredResources domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return move to "resources/list.do"
	 * @throws Exception fail to add resource
	 */
	@RequestMapping("/resources/add.do")
	public String add(@RequestParam(value="skipvalidation", required = false) String skipValidation,
			@ModelAttribute("resources") SecuredResources sr, BindingResult bindingResult,
			HttpSession session,
			SessionStatus status) throws Exception {

		if(!("Y").equals(skipValidation)){
			beanValidator.validate(sr, bindingResult);
			boolean isMatched = candidateSecuredResourcesService
					.checkMatched(sr.getResourcePattern(), sr.getResourceType());
			if (!isMatched) {
				bindingResult.rejectValue("resourcePattern", "errors.resourcepattern", new Object[] { sr
						.getResourcePattern() }, "check resource pattern.");
			}
			if (bindingResult.hasErrors()) {
				return "/resources/resourcedetail";
			}
		}
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		sr.setSystemName(systemName[0]);

		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");

		sr.setCreateDate(currentTime);
		securedResourcesService.save(sr);
		status.setComplete();

		return "forward:/resources/list.do";
	}

	/**
	 * update Resources data
	 * @param sr SecuredResources domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return move to "/resources/list.do"
	 * @throws Exception fail to update data
	 */
	@RequestMapping("/resources/update.do")
	public String update(@RequestParam(value="skipvalidation", required = false) String skipValidation,
			@ModelAttribute("resources") SecuredResources sr, BindingResult bindingResult,
			HttpSession session,
			SessionStatus status) throws Exception {
		
		if(!("Y").equals(skipValidation)){
			beanValidator.validate(sr, bindingResult);
			boolean isMatched = candidateSecuredResourcesService
					.checkMatched(sr.getResourcePattern(), sr.getResourceType());
			if (!isMatched) {
				bindingResult.rejectValue("resourcePattern", "errors.resourcepattern", new Object[] { sr
						.getResourcePattern() }, "check resource pattern.");
			}
	
			if (bindingResult.hasErrors()) {
				return "/resources/resourcedetail";
			}
		}
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		sr.setSystemName(systemName[0]);
		
		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");
		sr.setModifyDate(currentTime);
		SecuredResources gettedsr = securedResourcesService.get(sr.getResourceId());
		sr.setCreateDate(gettedsr.getCreateDate());
		securedResourcesService.update(sr);
		status.setComplete();

		return "forward:/resources/list.do";
	}

}