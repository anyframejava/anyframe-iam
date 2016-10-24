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
import anyframe.iam.admin.domain.RestrictedTimesRoles;
import anyframe.iam.admin.domain.RestrictedTimesRolesId;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * Annotation Time-Role Controller
 * @author sungryong.kim
 * 
 */
@Controller
@SessionAttributes("restrictedtimesroles")
public class AnnotationTimeRoleController {

	@Resource(name = "restrictedTimesService")
	private RestrictedTimesService restrictedTimesService;

	@Resource(name = "restrictedTimesRolesService")
	private RestrictedTimesRolesService restrictedTimesRolesService;

	/**
	 * move to Time mapping page
	 * @return return "/restriction/timesmappinglist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timesmapping/list.do")
	public String listTimesMapping() throws Exception {
		return "/restriction/timesmappinglist";
	}

	/**
	 * move to page of Time-Role mapping list
	 * @return return "/restriction/timerolelist" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timerole/list.do")
	public String listTimeRole() throws Exception {
		return "/restriction/timerolelist";
	}

	/**
	 * make data of Time-Role mapping list
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/restriction/timerole/listData.do")
	public String listData(RestrictedTimesSearchVO searchVO, Model model) throws Exception {

		Page resultPage = restrictedTimesRolesService.getTimeRoleList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * move to adding page of Time-Role mapping data
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeroledetail" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timerole/addView.do")
	public String addView(@ModelAttribute("searchVO") RestrictedTimesSearchVO searchVO, Model model) throws Exception {
		model.addAttribute("restrictedtimes", new RestrictedTimes());
		model.addAttribute("roles", new ArrayList());

		return "/restriction/timeroledetail";
	}

	/**
	 * move to list pop-up menu
	 * @return return "/restriction/timespopup" page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/restriction/timerole/listPopUp.do")
	public String listPopUp() throws Exception {
		return "/restriction/timespopup";
	}

	/**
	 * add Time-Role mapping data
	 * @param timeId Time ID
	 * @param roleIds array of Roles ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timerole/list.do" page
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/restriction/timerole/save.do")
	public String saveTimeRoles(@RequestParam("timeId") String timeId, @RequestParam("roleId") String[] roleIds,
			SessionStatus status) throws Exception {

		ArrayList<RestrictedTimesRoles> list = new ArrayList<RestrictedTimesRoles>();
		for (int i = 0; i < roleIds.length; i++) {
			RestrictedTimesRoles restrictedTimesRoles = new RestrictedTimesRoles();
			RestrictedTimesRolesId id = new RestrictedTimesRolesId();
			id.setTimeId(timeId);
			id.setRoleId(roleIds[i]);
			restrictedTimesRoles.setId(id);
			list.add(restrictedTimesRoles);
		}
		restrictedTimesRolesService.saveTimeRoles(list);
		status.setComplete();

		return "forward:/restriction/timerole/list.do";
	}

	/**
	 * find list of Roles that matches the given Time ID
	 * @param timeId Time ID
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to find data
	 * 
	 */
	@JsonError
	@RequestMapping("/restriction/timerole/findrolebytime.do")
	public String findRoleListByTime(@RequestParam("timeId") String timeId, Model model) throws Exception {

		List resultList = restrictedTimesRolesService.findRoleListByTime(timeId);

		model.addAttribute("roles", resultList);
		return "jsonView";
	}

	/**
	 * Get Time-Role mapping Data that matches the given Time ID
	 * @param timeId Time ID
	 * @param model Model object to add attributes
	 * @return return "/restriction/timeroledetail" page
	 * @throws Exception fail to get data
	 * 
	 */
	@RequestMapping("/restriction/timerole/get.do")
	public String get(@RequestParam(value = "timeId", required = false) String timeId, Model model) throws Exception {
		if (!StringUtils.isBlank(timeId)) {
			RestrictedTimes rt = restrictedTimesService.get(timeId);
			List roleList = restrictedTimesRolesService.findRoleListByTime(timeId);

			model.addAttribute("restrictedtimes", rt);
			model.addAttribute("roles", roleList);
		}
		return "/restriction/timeroledetail";
	}

	/**
	 * Delete Time-Role mapping data that the given timeIds and roleIds
	 * @param timeIds array of Time ID
	 * @param roleIds array of Role ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timerole/listData.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timerole/delete.do")
	public String deleteList(@RequestParam("timeId") String[] timeIds, @RequestParam("roleId") String[] roleIds,
			SessionStatus status) throws Exception {
		List<RestrictedTimesRolesId> idList = new ArrayList<RestrictedTimesRolesId>();

		for (int i = 0; i < timeIds.length; i++) {
			RestrictedTimesRolesId rtsId = new RestrictedTimesRolesId();
			rtsId.setTimeId(timeIds[i]);
			rtsId.setRoleId(roleIds[i]);
			idList.add(rtsId);
		}

		restrictedTimesRolesService.delete(idList);
		status.setComplete();

		return "forward:/restriction/timerole/listData.do";
	}

	/**
	 * Delete Time-Role data that the given timeId
	 * @param timeId Time ID
	 * @param status SessionStatus object to block double submit
	 * @return return "/restriction/timerole/list.do" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/restriction/timerole/deleteFromDetail.do")
	public String deleteDetail(@RequestParam("timeId") String timeId, SessionStatus status) throws Exception {

		restrictedTimesRolesService.removeTimesRolesByTime(timeId);
		status.setComplete();

		return "forward:/restriction/timerole/list.do";
	}
}
