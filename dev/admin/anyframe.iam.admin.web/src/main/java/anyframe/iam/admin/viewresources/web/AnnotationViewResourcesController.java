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

package anyframe.iam.admin.viewresources.web;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

/**
 * Annotation ViewResources Controller
 * @author sungryong.kim
 * 
 */
@Controller
public class AnnotationViewResourcesController {

	@Resource(name = "viewResourcesService")
	private ViewResourcesService viewResourcesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * move to view resources List page
	 * @return move to view resources list page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/viewresources/list.do")
	public String list() throws Exception {
		return "/viewresources/viewresourcelist";
	}

	/**
	 * make data of view resource list
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/viewresources/listData.do")
	public String listData(ViewResourceSearchVO searchVO, Model model) throws Exception {
		Page resultPage = viewResourcesService.getList(searchVO);
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * get a ViewResources object that matches the given ID
	 * @param viewResourceId View Resources ID
	 * @param model Model object to add attributes
	 * @return move to "/viewresources/viewresourcedetail"
	 * @throws Exception faii to get data
	 */
	@RequestMapping("/viewresources/get.do")
	public String get(@RequestParam(value = "viewResourceId", required = false) String viewResourceId, Model model)
			throws Exception {
		if (!StringUtils.isBlank(viewResourceId)) {
			ViewResource gettedVR = viewResourcesService.get(viewResourceId);

			model.addAttribute("viewResources", gettedVR);
		}
		return "/viewresources/viewresourcedetail";
	}

	/**
	 * delete view resources data
	 * @param viewResourceIds array of view resource IDs
	 * @param status SessionStatus object to prevent double submit
	 * @return move to "/viewresources/listData.do"
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/viewresources/delete.do")
	public String delete(@RequestParam("viewResourceId") String[] viewResourceIds, SessionStatus status)
			throws Exception {

		viewResourcesService.delete(viewResourceIds);
		status.setComplete();

		return "forward:/viewresources/listData.do";
	}

	/**
	 * move to view resources adding page
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return move to "/viewresources/viewresourcedetail"
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/viewresources/addView.do")
	public String addView(@ModelAttribute("searchVO") ViewResourceSearchVO searchVO, Model model) throws Exception {

		model.addAttribute("viewResources", new ViewResource());
		return "/viewresources/viewresourcedetail";
	}

	/**
	 * delete view resources data
	 * @param resourceIds array of view resources IDs
	 * @param status SessionStatus object to block double submit
	 * @return move to "/viewresources/list.do"
	 * @throws Exception fail to delete data
	 */
	@RequestMapping("/viewresources/deleteFromDetail.do")
	public String deleteFromDetail(@RequestParam("viewResourceId") String[] resourceIds, SessionStatus status)
			throws Exception {

		viewResourcesService.delete(resourceIds);
		return "forward:/viewresources/list.do";
	}

	/**
	 * add View Resource data
	 * @param vr ViewResource domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return move to "/viewresources/viewresourcesdetail"
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/viewresources/add.do")
	public String add(@ModelAttribute("viewResources") ViewResource vr, BindingResult bindingResult,
			SessionStatus status) throws Exception {
		beanValidator.validate(vr, bindingResult);

		if (bindingResult.hasErrors()) {
			return "/viewresources/viewresourcesdetail";
		}

		viewResourcesService.save(vr);
		status.setComplete();
		return "forward:/viewresources/list.do";
	}

	/**
	 * update ViewResource data
	 * @param vr ViewResource domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return move to "viewresources/viewresourcesdetail"
	 * @throws Exception fail to update data
	 */
	@RequestMapping("/viewresources/update.do")
	public String update(@ModelAttribute("viewResources") ViewResource vr, BindingResult bindingResult,
			SessionStatus status) throws Exception {
		beanValidator.validate(vr, bindingResult);

		if (bindingResult.hasErrors()) {
			return "viewresources/viewresourcesdetail";
		}

		viewResourcesService.update(vr);
		status.setComplete();
		return "forward:/viewresources/list.do";
	}

	/**
	 * move to ViewResouece Id checking page
	 * @param viewResourceId ViewResouece ID
	 * @param model Model object to add attributes
	 * @return move to "viewresources/checkid"
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/viewresources/checkid.do")
	public String checkId(@RequestParam(value = "viewResourceId") String viewResourceId, Model model) throws Exception {
		viewResourceId = StringUtil.null2str(viewResourceId);
		model.addAttribute("viewResourceId", viewResourceId);
		return "/viewresources/checkid";
	}

	/**
	 * check ViewResource ID if it is duplicated
	 * @param viewResourceId ViewResource ID that want to be checked
	 * @param model Model object to add attributes
	 * @return move to "viewresources/checkid"
	 * @throws Exception fail to check Id
	 */
	@RequestMapping("/viewresources/duplicationconfirm.do")
	public String duplicationConfirm(@RequestParam(value = "viewResourceId", required = true) String viewResourceId,
			Model model) throws Exception {
		String newId = new String(viewResourceId.getBytes("8859_1"), "utf-8");
		if (!StringUtils.isBlank(newId)) {
			boolean exist = viewResourcesService.exists(newId);
			model.addAttribute("viewResourceId", viewResourceId);
			model.addAttribute("exist", exist);
		}
		else
			model.addAttribute("exist", true);
		return "/viewresources/checkid";
	}
}
