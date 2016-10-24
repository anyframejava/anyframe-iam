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

package org.anyframe.iam.admin.users.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.anyframe.iam.admin.common.web.JsonError;
import org.anyframe.iam.admin.domain.Authorities;
import org.anyframe.iam.admin.domain.AuthoritiesId;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.GroupsUsers;
import org.anyframe.iam.admin.domain.GroupsUsersId;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.Users;
import org.anyframe.iam.admin.groups.service.GroupsService;
import org.anyframe.iam.admin.roles.service.RolesService;
import org.anyframe.iam.admin.users.service.UsersService;
import org.anyframe.iam.admin.vo.UserSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.DateUtil;
import org.anyframe.util.StringUtil;
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
 * Annotation Users Controller
 * @author jongpil.park
 * 
 */
@Controller
@SessionAttributes("users")
public class AnnotationUsersController {

	@Resource(name = "usersService")
	private UsersService usersService;

	@Resource(name = "rolesService")
	private RolesService rolesService;

	@Resource(name = "groupsService")
	private GroupsService groupsService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * move to user adding page
	 * @param groupId group Id
	 * @param searchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return move to user adding page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/users/addView.do")
	public String addView(@RequestParam(value = "groupId", required = false) String groupId,
			@ModelAttribute("searchVO") UserSearchVO searchVO, Model model) throws Exception {

		model.addAttribute("users", new Users());

		if (!StringUtils.isBlank(groupId)) {
			Groups gettedGroups = groupsService.get(groupId);
			model.addAttribute("groups", gettedGroups);
		}
		else {
			model.addAttribute("groups", new Groups());
		}

		model.addAttribute("roles", new ArrayList<Roles>());

		return "/users/userdetail";
	}

	/**
	 * add Users data
	 * @param users Users domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param groupId group Id
	 * @param roleIds array of Role Ids
	 * @param status SessionStatus object to block double submit
	 * @return move to "/userdetail/list.do?" page
	 * @throws Exception fail to add data
	 */
	@RequestMapping("/users/add.do")
	public String add(@ModelAttribute("users") Users users, BindingResult bindingResult, GroupsUsersId groupsUsersId,
			@RequestParam("groupId") String groupId,
			@RequestParam(value = "roleId", required = false) String[] roleId, SessionStatus status) throws Exception {

		beanValidator.validate(users, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/users/userdetail";
		}
		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");

		users.setPassword(users.getUserId());
		users.setCreateDate(currentTime);

		GroupsUsers groupUsers = new GroupsUsers();
//		GroupsUsersId groupsUsersId = new GroupsUsersId();
//
//		groupsUsersId.setUserId(users.getUserId());
//		groupsUsersId.setGroupId(groupId);

		groupUsers.setId(groupsUsersId);
		groupUsers.setUsers(users);
		groupUsers.setCreateDate(currentTime);

		Set<GroupsUsers> groups = new HashSet<GroupsUsers>();

		groups.add(groupUsers);

		users.setGroupsUserses(groups);
		
		bindingResult.setNestedPath("groupsUserses[0].id");
		beanValidator.validate(users.getGroupsUserses().iterator().next().getId(), bindingResult);
		bindingResult.setNestedPath(null);
		if (bindingResult.hasErrors()) {
			return "/users/userdetail";
		}

		Authorities[] authorities = null;

		if (roleId != null) {
			authorities = new Authorities[roleId.length];
			for (int i = 0; i < roleId.length; i++) {
				authorities[i] = new Authorities();

				AuthoritiesId authoritiesId = new AuthoritiesId();

				authoritiesId.setRoleId(roleId[i]);
				authoritiesId.setSubjectId(users.getUserId());

				authorities[i].setId(authoritiesId);
				authorities[i].setType("U");
				authorities[i].setCreateDate(currentTime);
			}
		}

		usersService.save(users, authorities);

		status.setComplete();

		return "forward:/userdetail/list.do?&groupId=" + groupId;
	}

	/**
	 * get a users that matches the given user id
	 * @param userId user Id
	 * @param model Model object to add attributes
	 * @return move to "/users/userdetail" page
	 * @throws Exception fail to get data
	 */
	@RequestMapping("/users/get.do")
	public String get(@RequestParam(value = "userId", required = false) String userId, Model model) throws Exception {
		String newUserId = new String(userId.getBytes("8859_1"), "utf-8");
		if (!StringUtils.isBlank(newUserId)) {
			Users users = usersService.get(newUserId);

			model.addAttribute("users", users);
			
			Set<GroupsUsers> setGroupsUsers = users.getGroupsUserses();
			
			Iterator<GroupsUsers> itrGroupsUsers = setGroupsUsers.iterator();
			
			if(itrGroupsUsers.hasNext()) {
				model.addAttribute("groups", itrGroupsUsers.next().getGroups());
			}
			else {
				model.addAttribute("groups", new Groups());
			}

			List<Roles> roles = rolesService.findRoles(newUserId);
			
			if (roles.size() > 0) {
				model.addAttribute("roles", roles);
			}
			else {
				model.addAttribute("roles", new ArrayList<Roles>());
			}
		}

		return "/users/userdetail";
	}

	/**
	 * update Users data
	 * @param users Users domain object
	 * @param bindingResult an object to check input data with validation rules
	 * @param groupId group Id
	 * @param roleIds array of Role Ids
	 * @param status SessionStatus object to block double submit
	 * @return move to "/userdetail/list.do?" page
	 * @throws Exception fail to update data
	 */
	@RequestMapping("/users/update.do")
	public String update(@ModelAttribute("users") Users users, BindingResult bindingResult,
			@RequestParam("groupId") String groupId,
			@RequestParam(value = "roleId", required = false) String[] roleId, SessionStatus status) throws Exception {

		beanValidator.validate(users, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/users/userdetail";
		}
		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");
		
		Users gettedUsers = usersService.get(users.getUserId());
		users.setPassword(gettedUsers.getPassword());
		users.setModifyDate(currentTime);

		Authorities[] authorities = null;

		//		
		// roleId 값이 있을 경우에만 수행 한다.
		//		 
		if (roleId != null) {
			authorities = new Authorities[roleId.length];

			for (int i = 0; i < roleId.length; i++) {
				authorities[i] = new Authorities();
				AuthoritiesId authoritiesId = new AuthoritiesId();

				authoritiesId.setRoleId(roleId[i]);
				authoritiesId.setSubjectId(users.getUserId());

				authorities[i].setId(authoritiesId);
				authorities[i].setType("U");
				authorities[i].setModifyDate(currentTime);
			}
		}

		//		
		// 새로운 GroupsUsers 값 세팅
		//		 
		GroupsUsers newGroupUsers = new GroupsUsers();
		GroupsUsersId newGroupsUsersId = new GroupsUsersId();
		
		newGroupsUsersId.setGroupId(groupId);
		newGroupsUsersId.setUserId(users.getUserId());
		
		newGroupUsers.setId(newGroupsUsersId);
		newGroupUsers.setCreateDate(currentTime);
		
		Set<GroupsUsers> set = new HashSet<GroupsUsers>();
		set.add(newGroupUsers);
		
		users.setGroupsUserses(set);

		//		
		// 현재의 GroupsUsers 값을 가져온다.
		//		 
		GroupsUsers currentGroupUsers = null;
		GroupsUsersId currentGroupsUsersId = null;
		
		Set<GroupsUsers> setGroupsUsers1 = gettedUsers.getGroupsUserses();
		
		Iterator<GroupsUsers> itrGroupsUsers1 = setGroupsUsers1.iterator();
		
		if(itrGroupsUsers1.hasNext()) {
			GroupsUsers groupsUsers1 = itrGroupsUsers1.next();
			Groups groups1 = groupsUsers1.getGroups();
			
			Set<GroupsUsers> setGroupsUsers2 = groups1.getGroupsUserses();
			Iterator<GroupsUsers> itrGroupsUsers2 = setGroupsUsers2.iterator();
			
			if(itrGroupsUsers2.hasNext()) {
				GroupsUsers groupsUsers2 = itrGroupsUsers2.next();
				GroupsUsersId groupsUsersId = groupsUsers2.getId();
				
				currentGroupUsers = new GroupsUsers();
				currentGroupsUsersId = new GroupsUsersId();
				
				currentGroupsUsersId.setGroupId(groupsUsersId.getGroupId());
				currentGroupsUsersId.setUserId(users.getUserId());
				
				currentGroupUsers.setId(currentGroupsUsersId);
			}
		}
		

		usersService.update(users, newGroupUsers, currentGroupUsers, authorities);
		
		return "forward:/userdetail/list.do?&groupId=" + groupId;
	}

	/**
	 * move to user list page
	 * @return move to user list page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/users/list.do")
	public String list() throws Exception {
		return "/users/userlist";
	}

	/**
	 * delete Users data
	 * @param userId array of user Ids
	 * @param groupId Group Id
	 * @param status SessionStatus object to prevent double submit
	 * @return move to "/users/listData.do?" page
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/users/delete.do")
	public String delete(@RequestParam("userId") String[] userId,
			@RequestParam(value = "groupId", required = false) String groupId, SessionStatus status) throws Exception {

		groupId = StringUtil.null2str(groupId);
		usersService.delete(userId);
		status.setComplete();

		return "forward:/users/listData.do?&groupId=" + groupId;
	}

	/**
	 * delete Users data
	 * @param userId array of user Ids
	 * @param groupId Group Id
	 * @param status SessionStatus object to block double submit
	 * @return move to "/userdetail/list.do?/" page
	 * @throws Exception fail to delete data
	 */
	@RequestMapping("/users/deleteFromDetail.do")
	public String deleteFromDetail(@RequestParam("userId") String[] userId,
			@RequestParam(value = "groupId", required = false) String groupId, SessionStatus status) throws Exception {

		groupId = StringUtil.null2str(groupId);
		usersService.delete(userId);
		status.setComplete();

		return "forward:/userdetail/list.do?&groupId=" + groupId;
	}

	/**
	 * make data of users list
	 * @param userSearchVO an object that contains search conditions
	 * @param model Model object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/users/listData.do")
	public String listData(UserSearchVO userSearchVO, Model model) throws Exception {

		Page resultPage = usersService.getList(userSearchVO);
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

}
