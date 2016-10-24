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
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import anyframe.core.idgen.IIdGenerationService;
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
 * @author jongpil.park
 * 
 */
@Controller
@SessionAttributes("roles")
public class AnnotationRolesController {

	@Resource(name = "rolesService")
	private RolesService rolesService;

	@Resource(name = "idGenerationServiceRole")
	private IIdGenerationService idGenerationServiceRole;

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
	public String add(HttpSession session, Roles roles, RolesHierarchyId rolesHierarchyId) throws Exception {

		RolesHierarchy rolesHierarchy = new RolesHierarchy();

		String currentDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");

		// parentRole에 입력을 하지만 child Role 에 해당 하는 부분이다. 이부분을 Id Generation
		// Service 를 이용하도록 변경을 해야한다.

		String parentRole = idGenerationServiceRole.getNextStringId();
		rolesHierarchyId.setParentRole(parentRole);

		rolesHierarchy.setId(rolesHierarchyId);
		rolesHierarchy.setCreateDate(currentDate);

		Set<RolesHierarchy> childRole = new HashSet<RolesHierarchy>();

		childRole.add(rolesHierarchy);

		roles.setRolesHierarchiesForChildRole(childRole);

		roles.setRoleId(parentRole);
		roles.setDescription(parentRole + " DESC");
		roles.setCreateDate(currentDate);

		rolesService.save(roles);
		session.setAttribute("CHILDROLE", parentRole);

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
	public String update(HttpSession session, Roles roles) throws Exception {

		if ("".equals(roles.getRoleId())) {
			String roleId = (String) session.getAttribute("CHILDROLE");
			roles.setRoleId(roleId);
			session.removeAttribute("CHILDROLE");
		}

		roles.setDescription(rolesService.get(roles.getRoleId()).getDescription());
		roles.setModifyDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));

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
	public String listData(@RequestParam("id") String id, Model model) throws Exception {

		List<IamTree> list = null;
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();

		if (id.equals("0")) {
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
			}
			else {
				throw new Exception();
			}
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
		model.addAttribute(listNode);

		return "jsonView";
	}
}
