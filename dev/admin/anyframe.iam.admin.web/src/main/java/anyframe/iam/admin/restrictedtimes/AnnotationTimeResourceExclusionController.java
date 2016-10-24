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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import anyframe.common.Page;
import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.domain.RestrictedTimes;
import anyframe.iam.admin.domain.TimesResourcesExclusion;
import anyframe.iam.admin.domain.TimesResourcesExclusionId;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * Annotation Time Resource Exclusion Controller
 * @author sungryong.kim
 * 
 */
@Controller
@SessionAttributes("timesresourcesexclusion")
public class AnnotationTimeResourceExclusionController {

	@Resource(name = "restrictedTimesService")
	private RestrictedTimesService restrictedTimesService;

	@Resource(name = "timesResourcesExclusionService")
	private TimesResourcesExclusionService timesResourcesExclusionService;

	/**
	 * move to page of Time-Exclusion list
	 * @return return "/restriction/timeexclusionlist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timeexclusion/list.do")
	public String listTimeRole() throws Exception {
		return "/restriction/timeexclusionlist";
	}

	/**
	 * make data of Time-Exclusion list
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/timeexclusion/listData.do")
	public String listData(RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		Page resultPage = timesResourcesExclusionService.getTimeExclusionList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * move to adding Time-Exclusion page
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeexclusiondetail" page
	 * @throws Exception fail to move to the page
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/restriction/timeexclusion/addView.do")
	public String addView(@ModelAttribute("searchVO") RestrictedTimesSearchVO searchVO, Model model) throws Exception {
		TimesResourcesExclusion timeResourcesExclusion = new TimesResourcesExclusion();
		timeResourcesExclusion.setId(new TimesResourcesExclusionId());
		timeResourcesExclusion.setRestrictedTimes(new RestrictedTimes());

		model.addAttribute("timesresourcesexclusion", timeResourcesExclusion);
		model.addAttribute("roles", new ArrayList());

		return "/restriction/timeexclusiondetail";
	}

	/**
	 * Get Time-Exclusion Data that matches the given Time ID and Resource ID
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeexclusiondetail" page
	 * @throws Exception fail to get data
	 * 
	 */
	@RequestMapping("/restriction/timeexclusion/get.do")
	public String get(@RequestParam(value = "timeId", required = true) String timeId,
			@RequestParam(value = "resourceId", required = true) String resourceId, Model model) throws Exception {
		if (!StringUtils.isBlank(timeId) && !StringUtils.isBlank(resourceId)) {

			RestrictedTimes rt = restrictedTimesService.get(timeId);
			TimesResourcesExclusion timeResourcesExclusion = new TimesResourcesExclusion();
			TimesResourcesExclusionId timeResourcesExclusionId = new TimesResourcesExclusionId();
			timeResourcesExclusion.setRestrictedTimes(rt);
			timeResourcesExclusionId.setTimeId(timeId);
			timeResourcesExclusionId.setResourceId(resourceId);
			timeResourcesExclusion.setId(timeResourcesExclusionId);

			@SuppressWarnings("unchecked")
			List roleList = timesResourcesExclusionService.findRoleListByTimeResource(timeId, resourceId);

			model.addAttribute("timesresourcesexclusion", timeResourcesExclusion);
			model.addAttribute("roles", roleList);
		}
		return "/restriction/timeexclusiondetail";
	}

	/**
	 * move to list pop-up menu
	 * @return return "/restriction/timeexclusionpopup" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timeexclusion/listPopUp.do")
	public String listPopUp() throws Exception {
		return "/restriction/timeexclusionpopup";
	}

	/**
	 * find list of Roles that matches the given Time ID and Resources ID
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to find data
	 * 
	 */
	@JsonError
	@RequestMapping("/restriction/timeexclusion/findrolebytimeresource.do")
	public String findRoleListByTimeResource(@RequestParam("timeId") String timeId,
			@RequestParam("resourceId") String resourceId, Model model) throws Exception {
		
		@SuppressWarnings("unchecked")
		List resultList = timesResourcesExclusionService.findRoleListByTimeResource(timeId, resourceId);

		model.addAttribute("roles", resultList);
		return "jsonView";
	}

	/**
	 * add Time-Exclusion data
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @param roleIds array of Roles ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeexclusion/list.do" page
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/restriction/timeexclusion/save.do")
	public String saveTimeExclusion(@RequestParam("id.timeId") String timeId,
			@RequestParam("id.resourceId") String resourceId, @RequestParam("roleId") String[] roleIds,
			SessionStatus status) throws Exception {
		ArrayList<TimesResourcesExclusion> list = new ArrayList<TimesResourcesExclusion>();
		for (int i = 0; i < roleIds.length; i++) {
			TimesResourcesExclusion timesResourcesExclusion = new TimesResourcesExclusion();
			TimesResourcesExclusionId id = new TimesResourcesExclusionId();
			id.setTimeId(timeId);
			id.setResourceId(resourceId);
			id.setRoleId(roleIds[i]);
			timesResourcesExclusion.setId(id);
			list.add(timesResourcesExclusion);
		}
		timesResourcesExclusionService.saveTimeExclusion(list);
		status.setComplete();

		return "forward:/restriction/timeexclusion/list.do";
	}

	/**
	 * Delete Time-Exclusion data that the given timeIds, resourcesIds and
	 * roleIds
	 * @param timeIds array of Time ID
	 * @param resourceIds array of Resources ID
	 * @param roleIds array of Role ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeexclusion/listData.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timeexclusion/delete.do")
	public String deleteList(@RequestParam("timeId") String[] timeIds,
			@RequestParam("resourceId") String[] resourceIds, @RequestParam("roleId") String[] roleIds,
			SessionStatus status) throws Exception {
		List<TimesResourcesExclusionId> idList = new ArrayList<TimesResourcesExclusionId>();

		for (int i = 0; i < timeIds.length; i++) {
			TimesResourcesExclusionId rtsId = new TimesResourcesExclusionId();
			rtsId.setTimeId(timeIds[i]);
			rtsId.setResourceId(resourceIds[i]);
			rtsId.setRoleId(roleIds[i]);
			idList.add(rtsId);
		}

		timesResourcesExclusionService.delete(idList);
		status.setComplete();

		return "forward:/restriction/timeexclusion/listData.do";
	}

	/**
	 * Delete Time-Exclusion data that the given timeId and resourcesId
	 * @param timeId Time ID
	 * @param resourceId Resources ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeexclusion/list.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timeexclusion/deleteFromDetail.do")
	public String deleteFromDetail(@RequestParam("id.timeId") String timeId,
			@RequestParam("id.resourceId") String resourceId, SessionStatus status) throws Exception {

		timesResourcesExclusionService.removeTimesExclusionByTimeResource(timeId, resourceId);
		status.setComplete();

		return "forward:/restriction/timeexclusion/list.do";
	}
}
