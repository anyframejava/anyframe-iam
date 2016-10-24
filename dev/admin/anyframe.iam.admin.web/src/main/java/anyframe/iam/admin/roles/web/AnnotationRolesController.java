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

package anyframe.iam.admin.roles.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.JSTreeNode;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;
import anyframe.iam.admin.roles.service.RolesService;

/**
 * Annotation Roles Controller
 * @author Jongpil Park
 * 
 */
@Controller
@SessionAttributes("roles")
public class AnnotationRolesController {

	@Resource(name = "rolesService")
	private RolesService rolesService;

	@Resource(name = "idGenerationServiceRole")
	private IIdGenerationService idGenerationServiceRole;

	private ArrayList<JSTreeNode> makeRootNode() throws Exception {

		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

		list = rolesService.getRootNodeOfRoles();
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
	 * move to add roles form
	 * @param model an object to add attributes
	 * @return move to "role/roledetail"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/roles/addView.do")
	public String addView(Model model) throws Exception {

		model.addAttribute("roles", new Roles());

		return "/roles/roledetail";
	}

	/**
	 * add role data
	 * @param session HTTPSession
	 * @param roles Roles domain object that want to be saved
	 * @param rolesHierarchyId RolesHierarchy domain object that contains tree
	 * hierarchy information
	 * @return move to "/roles/list.do"
	 * @throws Exception fail to add
	 */
	@JsonError
	@RequestMapping("/roles/add.do")
	public String add(Roles roles, RolesHierarchyId rolesHierarchyId) throws Exception {

		RolesHierarchy rolesHierarchy = new RolesHierarchy();
		rolesHierarchy.setId(rolesHierarchyId);

		Set<RolesHierarchy> parentRole = new HashSet<RolesHierarchy>();

		parentRole.add(rolesHierarchy);

		roles.setRolesHierarchiesForParentRole(parentRole);

		rolesService.save(roles);

		return "forward:/roles/list.do";
	}

	/**
	 * move to role detail page
	 * @return move to "roles/roledetail"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/roles/get.do")
	public String get() throws Exception {

		return "/roles/roledetail";
	}

	/**
	 * update role data
	 * @param session HTTPSession
	 * @param roles Roles domain object that want to be updated
	 * @return move to "/roles/list.do"
	 * @throws Exception fail to update data
	 */
	@JsonError
	@RequestMapping("/roles/update.do")
	public String update(Roles roles) throws Exception {

		if ("".equals(roles.getDescription())) {
			roles.setDescription(rolesService.get(roles.getRoleId()).getDescription());
		}

		rolesService.update(roles);

		return "forward:/roles/list.do";
	}

	/**
	 * move to role list page
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return move to "/roles/rolelist"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/roles/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/roles/rolelist";
	}

	/**
	 * delete role data
	 * @param roles Roles domail object that want to be deleted
	 * @return jsonView
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/roles/delete.do")
	public String delete(Roles roles) throws Exception {

		rolesService.remove(roles.getRoleId());

		return "jsonView";
	}

	/**
	 * make data of role list
	 * @param id role Id, if id is '0' it represents root node
	 * @param model an object to add attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/roles/listData.do")
	public String listData(@RequestParam("id") String id,
			@RequestParam(value = "roleName", required = false) String roleName,
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
			else {
				list = rolesService.getRoleTree(id);

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
		else if ("Y".equals(searchClickYn)) {
			String roleId = rolesService.getRoleIdByRoleName(roleName);

			if (!"".equals(roleId)) {
				List<String> ids = rolesService.getParentsRoleIds(roleId);
				int size = ids.size();

				if (size == 0) {
					listNode = makeRootNode();
				}
				else {
					@SuppressWarnings("unchecked")
					ArrayList<JSTreeNode>[] childNode = new ArrayList[size];

					for (int i = 0; i < size; i++) {
						list = rolesService.getRoleTree(ids.get(i));
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

					Roles rootNode = rolesService.get(ids.get(size - 1));

					node = new JSTreeNode();
					data = new Data();
					attribute = new Attributes();

					data.setTitle(rootNode.getRoleName());
					attribute.setId(rootNode.getRoleId());

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

	@RequestMapping("/roles/getRoleNameList.do")
	public void getGroupNameList(@RequestParam(value = "q", required = false) String keyword,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		keyword = new String(keyword.getBytes("8859_1"), "utf-8");
		String resultList = rolesService.getRoleNameList(keyword);
		response.getOutputStream().print(new String(resultList.getBytes("utf-8"), "8859_1"));
	}

	@RequestMapping("/roles/getRoleId.do")
	public String getRoleId(Model model) throws Exception {
		String roleId = idGenerationServiceRole.getNextStringId();

		model.addAttribute("roleId", roleId);

		return "jsonView";
	}
}
