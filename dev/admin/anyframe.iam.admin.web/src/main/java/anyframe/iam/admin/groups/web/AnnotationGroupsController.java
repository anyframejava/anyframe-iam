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

package anyframe.iam.admin.groups.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.domain.Attributes;
import anyframe.iam.admin.domain.Data;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.JSTreeNode;
import anyframe.iam.admin.groups.service.GroupsService;

/**
 * Annotation Groups Controller
 * @author jongpil.park
 * 
 */

@Controller
@SessionAttributes("groups")
public class AnnotationGroupsController {

	@Resource(name = "groupsService")
	private GroupsService groupsService;

	@Autowired
	private org.springmodules.validation.commons.DefaultBeanValidator beanValidator;

	/**
	 * move to 'add groups' form
	 * @param model an object to contain attribute
	 * @return move to "/user/groupdetail/"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/groups/addView.do")
	public String addView(Model model) throws Exception {

		model.addAttribute("groups", new Groups());
		return "/users/groupdetail";
	}

	/**
	 * add groups
	 * @param session an object to set attributes
	 * @param groups an object that want to be saved
	 * @param groupsHierarchyId groups-hierarchy id that contains information
	 * about parent and child node
	 * @return move to "/userdetail/list.do"
	 * @throws fail to add groups data
	 */
	@JsonError
	@RequestMapping("/groups/add.do")
	// success 시 view 처리 추가 필요
	public String add(HttpSession session, Groups groups, GroupsHierarchyId groupsHierarchyId) throws Exception {

		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		groupsHierarchy.setId(groupsHierarchyId);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		groups.setGroupsHierarchiesForChildGroup(childGroup);

		Groups group = groupsService.save(groups);
		session.setAttribute("GROUPID", group.getGroupId());

		return "forward:/userdetail/list.do";
	}

	/**
	 * get a row of group that matches the given group Id
	 * @param groupId group Id
	 * @param model an object to add attributes
	 * @return move to "/users/groupdetail"
	 * @throws Exception fail to get a row
	 */
	@RequestMapping("/groups/get.do")
	public String get(@RequestParam(value = "groupId", required = false) String groupId, Model model) throws Exception {

		if (!StringUtils.isBlank(groupId)) {
			Groups gettedGroups = groupsService.get(groupId);
			model.addAttribute("groups", gettedGroups);
		}
		else {
			model.addAttribute("groups", new Groups());
		}

		return "/users/groupdetail";
	}

	/**
	 * update a row of group that matches the given group Id
	 * @param groups domain Groups object that want to be updated
	 * @param model an object to add attributes
	 * @return move to "/userdetail/list.do"
	 * @throws Exception fail to update
	 * 
	 */
	@JsonError
	@RequestMapping("/groups/update.do")
	// success 시 view 처리 추가 필요
	public String update(HttpSession session, Groups groups) throws Exception {

		groups.setModifyDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));

		if ("".equals(groups.getGroupId())) {
			String groupId = (String) session.getAttribute("GROUPID");
			groups.setGroupId(groupId);
			session.removeAttribute("GROUPID");
		}

		groupsService.update(groups);

		return "forward:/userdetail/list.do";
	}

	/**
	 * update a row of group that matches the given group id from Groups Tab UI
	 * @param groups domain Groups object that want to be updated
	 * @param bindingResult an object to check input data with validation rules
	 * @param status SessionStatus object to block double submit
	 * @return move to "/groups/get.do?"
	 * @throws Exception fail to update
	 */
	@RequestMapping("/groups/updateFromTab.do")
	public String updateFromTab(@ModelAttribute("groups") Groups groups, BindingResult bindingResult,
			SessionStatus status) throws Exception {
		beanValidator.validate(groups, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/users/groupdetail";
		}
		groups.setModifyDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));

		groupsService.update(groups);
		status.setComplete();

		return "forward:/groups/get.do?";
	}

	/**
	 * move to group list view
	 * @param model an object to add attributes
	 * @return move to "/groups/groupslist"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/groups/list.do")
	public String list(Model model) throws Exception {

		return "/groups/grouplist";
	}

	/**
	 * delete the selected group
	 * @param groups an object that want to be deleted
	 * @return jsonView
	 * @throws Exception fail to delete
	 * 
	 */
	@JsonError
	@RequestMapping("/groups/delete")
	public String delete(Groups groups) throws Exception {
		groupsService.remove(groups.getGroupId());

		return "jsonView";
	}

	/**
	 * delete groups in tab UI
	 * @param groups an object that want to be deleted
	 * @return move to "/users/groupdetail"
	 * @throws Exception fail to delete
	 */
	@RequestMapping("/groups/deleteinTabUI")
	public String deleteinTabUI(Groups groups) throws Exception {
		groupsService.remove(groups.getGroupId());

		return "/users/groupdetail";
	}

	/**
	 * make data of group list
	 * @param id group Id, if id is '0' it represents root node
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/groups/listData.do")
	public String listData(@RequestParam("id") String id, Model model) throws Exception {

		List<IamTree> list = null;

		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

		if (id.equals("0")) {
			list = groupsService.getRootNodeOfGroups();
			if (list.size() > 0) {
				IamTree rootNode = list.get(0);

				node = new JSTreeNode();
				data = new Data();
				attribute = new Attributes();

				data.setTitle(rootNode.getTitle());
				attribute.setId(rootNode.getId());

				node.setAttributes(attribute);
				node.setData(data);
				node.setState(rootNode.getState());

				listNode.add(node);
			}
			else {
				throw new Exception();
			}
		}
		else {
			list = groupsService.getGroupTree(id);

			for (int i = 0; i < list.size(); i++) {
				IamTree tree = list.get(i);

				node = new JSTreeNode();
				data = new Data();
				attribute = new Attributes();

				data.setTitle(tree.getTitle());
				attribute.setId(tree.getId());

				node.setAttributes(attribute);
				node.setData(data);
				node.setState(tree.getState());

				listNode.add(node);
			}
		}

		model.addAttribute(listNode);

		return "jsonView";

	}
}
