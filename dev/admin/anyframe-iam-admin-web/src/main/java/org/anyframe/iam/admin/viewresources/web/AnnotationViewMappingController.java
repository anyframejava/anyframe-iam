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

package org.anyframe.iam.admin.viewresources.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.anyframe.iam.admin.common.web.JsonError;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.groups.service.GroupsService;
import org.anyframe.iam.admin.roles.service.RolesService;
import org.anyframe.iam.admin.users.service.UsersService;
import org.anyframe.iam.admin.viewresources.service.ViewMappingService;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.iam.core.acl.ExtBasePermission;
import org.anyframe.iam.core.acl.IViewResourceAccessService;
import org.anyframe.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Annotation View Mapping Controller
 * @author sungryong.kim
 * 
 */
@Controller
public class AnnotationViewMappingController {

	@Resource(name = "groupsService")
	private GroupsService groupsService;

	@Resource(name = "rolesService")
	private RolesService rolesService;

	@Resource(name = "viewMappingService")
	private ViewMappingService viewMappingService;

	@Resource(name = "viewResourceAccessService")
	private IViewResourceAccessService viewResourceAccessService;

	@Resource(name = "usersService")
	private UsersService usersService;

	/**
	 * move to view mapping list page
	 * @return "/viewresources/viewmappinglist"
	 * @throws Exception
	 */
	@RequestMapping("/viewresourcesmapping/list.do")
	public String list() throws Exception {
		return "/viewresources/viewmappinglist";
	}

	/**
	 * make data of view resource mapping list
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/viewresourcesmapping/listData.do")
	public String listData(ViewResourceSearchVO searchVO, Model model, HttpSession session) throws Exception {
		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		Page resultPage = viewMappingService.getList(searchVO);
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());
		return "jsonView";
	}

	/**
	 * delete view resources mapping data
	 * @param viewResourceId array of view resource IDs
	 * @param refId array of reference ID
	 * @param status SessionStatus object to prevent double submit
	 * @return move to "/viewresourcesmapping/listData.do"
	 * @throws Exception fail to delete data
	 */
	@RequestMapping("/viewresourcesmapping/delete.do")
	public String delete(@RequestParam(value = "viewResourceId", required = false) String[] viewResourceId,
			@RequestParam(value = "refId", required = false) String[] refId, SessionStatus status) throws Exception {
		List<ViewResourcesMappingPK> idList = new ArrayList<ViewResourcesMappingPK>();
		
		for (int i = 0; i < viewResourceId.length; i++) {
			ViewResourcesMappingPK vsPK = new ViewResourcesMappingPK();
			vsPK.setRefId(refId[i]);
			vsPK.setViewResourceId(viewResourceId[i]);
			idList.add(vsPK);
		}
		viewMappingService.delete(idList);
		return "forward:/viewresourcesmapping/list.do";
	}

	/**
	 * get a ViewResources Mapping data that matches the given ID
	 * @param viewResourceId View Resources ID
	 * @param model Model object to add attributes
	 * @return move to "/viewresources/viewmappingdetail"
	 * @throws Exception fail to get data
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewresourcesmapping/get.do")
	public String get(@RequestParam(value = "viewResourceId", required = false) String viewResourceId, Model model)
			throws Exception {

		
		List<ViewResourcesMapping> list = viewMappingService.get(viewResourceId);

		List<Groups> grouplist = groupsService.getList();
		List<Roles> rolelist = rolesService.getList();
		List permissionlist = viewResourceAccessService.getRegisteredPermissionList();

		model.addAttribute("grouplist", grouplist);
		model.addAttribute("rolelist", rolelist);
		model.addAttribute("permissionlist", permissionlist);
		model.addAttribute("mappinglist", list);
		model.addAttribute("viewResourceId", viewResourceId);

		List<String> masklist = new ArrayList<String>();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				ViewResourcesMapping mapping = list.get(i);

				DecimalFormat formatter = new DecimalFormat("00000000000000000000000000000000");
				// DecimalFormat formatter = new DecimalFormat("00000");

				int mask = mapping.getMask().intValue();
				int value = Integer.parseInt(Integer.toBinaryString(mask));
				masklist.add(formatter.format(value));
			}

			model.addAttribute("masklist", masklist);
		}

		return "/viewresources/viewmappingdetail";
	}

	/**
	 * find user that matches the given user name
	 * @param userName user Name
	 * @param index index
	 * @param model Model object to add attributes
	 * @return move to "/viewresources/findusersbynamepopup" page
	 * @throws Exception fail to find user list
	 */
	@RequestMapping("/viewresourcesmapping/finduser.do")
	public String findUserByName(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "index") int index, Model model) throws Exception {

		model.addAttribute("userName", userName);
		model.addAttribute("index", index);

		return "/viewresources/findusersbynamepopup";
	}

	/**
	 * make data of user list that matches the given user name
	 * @param userName User name
	 * @param index index
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/viewresourcesmapping/finduserlistdata.do")
	public String findUserListDataByName(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "index") int index, Model model) throws Exception {

		@SuppressWarnings("unchecked")
		List list = usersService.findUserByName(userName);

		model.addAttribute("records", list.size());
		model.addAttribute("rows", list);

		return "jsonView";
	}

	/**
	 * save View Resouece Mapping data
	 * @param request HttpServletRequest
	 * @param viewResourceId view resouece ID
	 * @param roleid array of role id
	 * @param refType type of reference id
	 * @return move to "/viewresourcesmapping/list.do" page
	 * @throws Exception fail to save data
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/viewresourcesmapping/save.do")
	public String save(HttpServletRequest request, @RequestParam(value = "viewResourceId") String viewResourceId,
			@RequestParam(value = "roleid") String[] roleid, @RequestParam(value = "refType") String[] refType)
			throws Exception {

		int idx = roleid.length;
		ViewResourcesMapping[] viewResourcesMapping = new ViewResourcesMapping[idx];
		ViewResourcesMappingPK[] viewResourcesMappingPK = new ViewResourcesMappingPK[idx];

		List permissionlist = viewResourceAccessService.getRegisteredPermissionList();

		String[][] permission = new String[permissionlist.size()][idx];

		int[] mask = new int[idx];
		for (int i = 0; i < idx; i++) {
			mask[i] = 0;
		}

		for (int i = 0; i < permissionlist.size(); i++) {
			Map map = (Map) permissionlist.get(i);

			String permissionName = map.get("permissionName").toString().toLowerCase();
			permission[i] = request.getParameterValues(permissionName);
		}

		for (int i = 0; i < idx; i++) {
			for (int j = 0; j < permissionlist.size(); j++) {
				mask[i] = mask[i] + Integer.parseInt(permission[j][i]);
			}

			viewResourcesMapping[i] = new ViewResourcesMapping();
			viewResourcesMappingPK[i] = new ViewResourcesMappingPK();

			viewResourcesMappingPK[i].setViewResourceId(viewResourceId);
			viewResourcesMappingPK[i].setRefId(roleid[i]);

			viewResourcesMapping[i].setId(viewResourcesMappingPK[i]);

			viewResourcesMapping[i].setMask(mask[i]);

			String permissions = ExtBasePermission.getPermissionNames(ExtBasePermission.buildFromMask(mask[i]));

			viewResourcesMapping[i].setPermissions(permissions);
			viewResourcesMapping[i].setRefType(refType[i]);
		}

		viewMappingService.save(viewResourcesMapping);

		return "forward:/viewresourcesmapping/list.do";
	}

	/**
	 * move to pop-up page of view resource list
	 * @return move to "/viewresources/viewresourcepopup"
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/viewresourcesmapping/viewresourcelist.do")
	public String viewresourcelist() throws Exception {
		return "/viewresources/viewresourcepopup";
	}
}
