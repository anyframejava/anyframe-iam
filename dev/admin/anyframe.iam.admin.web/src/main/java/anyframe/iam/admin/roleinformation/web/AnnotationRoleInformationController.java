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

package anyframe.iam.admin.roleinformation.web;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.roles.service.RolesService;

/**
 * an annotation controller that control Role information in Tab UI
 * @author youngmin.jo
 * 
 */
@Controller
@SessionAttributes("roles")
public class AnnotationRoleInformationController {

	@Resource(name = "rolesService")
	private RolesService rolesService;

	/**
	 * get Roles information that matches the given role Id
	 * @param roleId role Id
	 * @param model an object to add attributes
	 * @return move to "/roles/roleinfo"
	 * @throws Exception fail to get information
	 */
	@RequestMapping("/roleinformation/get.do")
	public String get(@RequestParam(value = "roleId", required = false) String roleId, Model model) throws Exception {
		Roles roles;

		if (!StringUtils.isBlank(roleId)) {
			roles = rolesService.get(roleId);
		} else {
			roles = new Roles();
		}

		model.addAttribute("roles", roles);

		return "/roles/roleinfo";
	}

}
