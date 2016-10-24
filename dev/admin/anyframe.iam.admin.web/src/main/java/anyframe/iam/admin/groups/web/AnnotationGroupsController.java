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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.common.IAMException;
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
 * @author Jongpil Park
 * 
 */

@Controller
@SessionAttributes("groups")
public class AnnotationGroupsController {

	@Resource(name = "groupsService")
	private GroupsService groupsService;

	@Resource(name = "idGenerationServiceGroup")
	private IIdGenerationService idGenerationServiceGroup;

	/**
	 * find root node of Groups hierarchy
	 * @return ArrayList<JSTreeNode> Root node
	 * @throws Exception
	 * 				fail to find root node
	 */
	private ArrayList<JSTreeNode> makeRootNode() throws Exception {

		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

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

			return listNode;
		}
		else {
			throw new IAMException("Root node is not exist");
		}
	}

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
	public String add(HttpSession session, Groups groups, GroupsHierarchyId groupsHierarchyId) throws Exception {

		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		groupsHierarchy.setId(groupsHierarchyId);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		groups.setGroupsHierarchiesForChildGroup(childGroup);

		groupsService.save(groups);

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
	public String update(Groups groups) throws Exception {

		groupsService.update(groups);

		return "forward:/userdetail/list.do";
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
	 * make data of group list
	 * @param id group Id, if id is '0' it represents root node
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/groups/listData.do")
	public String listData(@RequestParam("id") String id,
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "searchClickYn") String searchClickYn, Model model) throws Exception {
		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

		if ("N".equals(searchClickYn)) {
			if (id.equals("0")) { // root node
				listNode = makeRootNode();
			}
			else { // open branch
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
		}
		else if ("Y".equals(searchClickYn)) { // search
			String groupId = groupsService.getGroupIdByGroupName(groupName);

			if (!"".equals(groupId)) {	// when the given group name exist
				List<String> ids = groupsService.getParentsGroupIds(groupId);

				int size = ids.size();

				if (size == 0) {
					listNode = makeRootNode();
				}
				else {
					@SuppressWarnings("unchecked")
					ArrayList<JSTreeNode>[] childNode = new ArrayList[size];

					for (int i = 0; i < size; i++) {
						list = groupsService.getGroupTree(ids.get(i));
						childNode[i] = new ArrayList<JSTreeNode>();

						for (int j = 0; j < list.size(); j++) {
							IamTree tree = list.get(j);

							node = new JSTreeNode();
							data = new Data();
							attribute = new Attributes();

							data.setTitle(tree.getTitle());
							attribute.setId(tree.getId());

							node.setAttributes(attribute);
							node.setData(data);
							node.setState(tree.getState());

							if (i != 0) {
								if (tree.getId().equals(ids.get(i - 1))) {
									node.setState("open");
									node.setChildren(childNode[i - 1]);
								}
							}
							childNode[i].add(node);
						}
					}

					Groups rootNode = groupsService.get(ids.get(size - 1));

					node = new JSTreeNode();
					data = new Data();
					attribute = new Attributes();

					data.setTitle(rootNode.getGroupName());
					attribute.setId(rootNode.getGroupId());

					node.setAttributes(attribute);
					node.setData(data);
					node.setState("open");
					node.setChildren(childNode[size - 1]);

					listNode.add(node);
				}
			}
			else {
				listNode = makeRootNode();
			}
		}

		model.addAttribute(listNode);

		return "jsonView";
	}

	/**
	 * find list of group name that matches the given keyword
	 * @param keyword keyword of group name that want to be find
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param model Model object to contain attributes
	 * @throws Exception
	 * 				fail to find list of group name
	 */
	@RequestMapping("/groups/getGroupNameList.do")
	public void getGroupNameList(@RequestParam(value = "q", required = false) String keyword,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		keyword = new String(keyword.getBytes("8859_1"), "utf-8");
		String resultList = groupsService.getGroupNameList(keyword);
		response.getOutputStream().print(new String(resultList.getBytes("utf-8"), "8859_1"));
	}

	@RequestMapping("/groups/getGroupId.do")
	public String getGroupId(Model model) throws Exception {
		String groupId = idGenerationServiceGroup.getNextStringId();

		model.addAttribute("groupId", groupId);

		return "jsonView";
	}
}
