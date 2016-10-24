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

package anyframe.iam.admin.restrictedtimes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

import anyframe.common.Page;
import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.domain.RestrictedTimes;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * Annotation Restricted Times Controller
 * @author sungryong.kim
 * 
 */
@Controller
@SessionAttributes("restrictedtimes")
public class AnnotationRestrictedTimesController {

	@Resource(name = "restrictedTimesService")
	private RestrictedTimesService restrictedTimesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * move to time restriction list page
	 * @return "restriction/timeslist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/list.do")
	public String list() throws Exception {
		return "/restriction/timeslist";
	}

	/**
	 * make list data of time restriction for grid component
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/listData.do")
	public String listData(HttpSession session, RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		String systemName = (String)session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		
		Page resultPage = restrictedTimesService.getList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * move to adding time restriction page
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return return "/restriction/timedetail/" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/addView.do")
	public String addView(
			HttpSession session, 
			@ModelAttribute("searchVO") RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		String[] systemNames = new String[1];
		systemNames[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemNames);
		model.addAttribute("restrictedtimes", new RestrictedTimes());

		return "/restriction/timedetail";
	}

	/**
	 * Get time restriction data that matches the given time Id
	 * @param timeId Time Id that want to be got
	 * @param model Model object to add attributes
	 * @return return "/restriction/timedetail" page
	 * @throws Exception fail to get data
	 */
	@RequestMapping("/restriction/get.do")
	public String get(@RequestParam(value = "timeId", required = false) String timeId, HttpSession session, Model model) throws Exception {
		
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemName);
		
		if (!StringUtils.isBlank(timeId)) {
			RestrictedTimes rt = restrictedTimesService.get(timeId);
			model.addAttribute("restrictedtimes", rt);
		}
		return "/restriction/timedetail";
	}

	/**
	 * update time restriction data
	 * @param rt RestrictedTimes domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timedetail" or "/restriction/list.do" page
	 * @throws Exception fail to update data
	 */
	@RequestMapping("/restriction/update.do")
	public String update(
			@ModelAttribute("restrictedtimes") RestrictedTimes rt, 
			BindingResult bindingResult,
			HttpSession session,
			SessionStatus status) throws Exception {
		beanValidator.validate(rt, bindingResult);

		if (bindingResult.hasErrors()) {
			return "/restriction/timedetail";
		}
		
		if("".equals(rt.getSystemName()) || rt.getSystemName() == null)
			rt.setSystemName((String)session.getAttribute("systemName"));
		
		restrictedTimesService.update(rt);
		status.setComplete();

		return "forward:/restriction/list.do";
	}

	/**
	 * add time restriction data
	 * @param rt RestrictedTimes domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timedetail" or "/restriction/list.do" page
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/restriction/add.do")
	public String add(
			@ModelAttribute("restrictedtimes") RestrictedTimes rt, 
			BindingResult bindingResult,
			HttpSession session,
			SessionStatus status) throws Exception {
		beanValidator.validate(rt, bindingResult);

		if (bindingResult.hasErrors()) {
			return "/restriction/timedetail";
		}
		
		if("".equals(rt.getSystemName()) || rt.getSystemName() == null)
			rt.setSystemName((String)session.getAttribute("systemName"));

		restrictedTimesService.save(rt);
		status.setComplete();

		return "forward:/restriction/list.do";
	}

	/**
	 * Delete time restriction data that matches the given Time Id
	 * @param timeIds array of time Ids that want to be deleted
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/listData.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/delete.do")
	public String delete(@RequestParam("timeId") String[] timeIds, SessionStatus status) throws Exception {

		restrictedTimesService.delete(timeIds);
		status.setComplete();

		return "forward:/restriction/listData.do";
	}

	/**
	 * Delete time restriction data that matches the given Time Id
	 * @param timeIds array of time Ids that want to be deleted
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/list.do" page
	 * @throws Exception fail to delete data
	 */
	@RequestMapping("/restriction/deleteFromDetail.do")
	public String deleteFromDetail(@RequestParam("timeId") String[] timeIds, SessionStatus status) throws Exception {

		restrictedTimesService.delete(timeIds);
		status.setComplete();

		return "forward:/restriction/list.do";
	}
}
