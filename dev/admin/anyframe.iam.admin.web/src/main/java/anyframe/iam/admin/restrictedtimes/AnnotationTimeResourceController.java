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
import javax.servlet.http.HttpSession;

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
import anyframe.iam.admin.domain.RestrictedTimesResources;
import anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * Annotation Time-Resources controller
 * @author sungryong.kim
 * 
 */
@Controller
@SessionAttributes("restrictedtimesresources")
public class AnnotationTimeResourceController {

	@Resource(name = "restrictedTimesService")
	private RestrictedTimesService restrictedTimesService;

	@Resource(name = "restrictedTimesResourcesService")
	private RestrictedTimesResourcesService restrictedTimesResourcesService;

	/**
	 * move to time-resource mapping list page
	 * @return return "/restriction/timeresourcelist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timeresource/list.do")
	public String listTimeRole() throws Exception {
		return "/restriction/timeresourcelist";
	}

	/**
	 * make list of time-resource mapping data
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/timeresource/listData.do")
	public String listData(HttpSession session, RestrictedTimesSearchVO searchVO, Model model) throws Exception {
		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		
		Page resultPage = restrictedTimesResourcesService.getTimeResourceList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * move to time-resources adding page
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeresourcedetail" page
	 * @throws Exception fail to move to the page
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/restriction/timeresource/addView.do")
	public String addView(
			HttpSession session,
			@ModelAttribute("searchVO") RestrictedTimesSearchVO searchVO, Model model) throws Exception {
		String[] systemName = new String[1];
		
		systemName[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemName);
		model.addAttribute("restrictedtimes", new RestrictedTimes());
		model.addAttribute("roles", new ArrayList());

		return "/restriction/timeresourcedetail";
	}

	/**
	 * Get Time-Resource mapping data that matches the given Time id
	 * @param timeId Time Id
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeresourcedetail" page
	 * @throws Exception fail to get data
	 */
	@RequestMapping("/restriction/timeresource/get.do")
	public String get(
			HttpSession session,
			@RequestParam(value = "timeId", required = false) String timeId, Model model) throws Exception {
		
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemName);
		
		if (!StringUtils.isBlank(timeId)) {
			RestrictedTimes rt = restrictedTimesService.get(timeId);
			@SuppressWarnings("unchecked")
			List roleList = restrictedTimesResourcesService.findRoleListByTime(timeId);

			model.addAttribute("restrictedtimes", rt);
			model.addAttribute("roles", roleList);
		}
		return "/restriction/timeresourcedetail";
	}

	/**
	 * move to pop-up menu
	 * @return return "/restriction/timespopup" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timeresource/listPopUp.do")
	public String listPopUp() throws Exception {
		return "/restriction/timespopup";
	}

	/**
	 * make list of resource data that matches the given time Id
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/timeresource/listResourceData.do")
	public String listResourceData(
			HttpSession session, 
			RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		
		Page resultPage = restrictedTimesResourcesService.findResourceListByTime(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * move to opo-up menu that present list of resources
	 * @return return "/restriction/resourcelist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timeresource/listResourcePopUp.do")
	public String listResourcePopUp() throws Exception {
		return "/restriction/resourcelist";
	}

	/**
	 * make list of resource data that does not match the given Time Id
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/timeresource/listUnmappedResourceData.do")
	public String listUnmappedResourceData(
			HttpSession session,
			RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		
		Page resultPage = restrictedTimesResourcesService.findUnmappedResourceListByTime(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}
	
	/**
	 * add Time-Resources mapping data
	 * @param timeId Time Id
	 * @param resourceIds array of resource Ids
     * @param searchKeyword searchKeyword
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeresource/listResourceData.do" page
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/restriction/timeresource/add.do")
	public String addTimeResources(@RequestParam("timeId") String timeId,
			@RequestParam("resourceId") String[] resourceIds, @RequestParam("searchKeyword") String searchKeyword, SessionStatus status) throws Exception {

		ArrayList<RestrictedTimesResources> list = new ArrayList<RestrictedTimesResources>();
		for (int i = 0; i < resourceIds.length; i++) {
			RestrictedTimesResources restrictedTimesResources = new RestrictedTimesResources();
			RestrictedTimesResourcesId id = new RestrictedTimesResourcesId();
			id.setTimeId(timeId);
			id.setResourceId(resourceIds[i]);
			restrictedTimesResources.setId(id);
			list.add(restrictedTimesResources);
		}
		restrictedTimesResourcesService.addTimeResources(list);
		status.setComplete();

		return "forward:/restriction/timeresource/listResourceData.do";
	}

	/**
	 * delete Time-Resources mapping data
	 * @param timeIds array of time ids
	 * @param resourceIds array of resource Ids
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeresource/listData.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timeresource/delete.do")
	public String deleteResource(@RequestParam("timeIds") String[] timeIds,
			@RequestParam("resourceIds") String[] resourceIds, SessionStatus status) throws Exception {
		List<RestrictedTimesResourcesId> idList = new ArrayList<RestrictedTimesResourcesId>();

		for (int i = 0; i < resourceIds.length; i++) {
			RestrictedTimesResourcesId rtsId = new RestrictedTimesResourcesId();
			rtsId.setTimeId(timeIds[i]);
			rtsId.setResourceId(resourceIds[i]);
			idList.add(rtsId);
		}

		restrictedTimesResourcesService.delete(idList);
		status.setComplete();

		return "forward:/restriction/timeresource/listData.do";
	}

	/**
	 * delete Time-Resources mapping data that matches the given time Id
	 * @param timeIds Time ids
	 * @param resourceIds array of resource Ids
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timeresource/listResourceData.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timeresource/deleteResourceFromDetail.do")
	public String deleteResourceFromDetail(@RequestParam("timeId") String timeId,
			@RequestParam("resourceIds") String[] resourceIds, SessionStatus status) throws Exception {
		List<RestrictedTimesResourcesId> idList = new ArrayList<RestrictedTimesResourcesId>();

		for (int i = 0; i < resourceIds.length; i++) {
			RestrictedTimesResourcesId rtsId = new RestrictedTimesResourcesId();
			rtsId.setTimeId(timeId);
			rtsId.setResourceId(resourceIds[i]);
			idList.add(rtsId);
		}

		restrictedTimesResourcesService.delete(idList);
		status.setComplete();

		return "forward:/restriction/timeresource/listResourceData.do?searchKeyword=" + timeId;
	}
}
