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

package anyframe.iam.admin.rolegroupmapping.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.iam.admin.authorities.service.AuthoritiesService;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.vo.AuthoritySearchVO;

/**
 * an annotation controller that related with Role-Group mapping
 * @author youngmin.jo
 * 
 */
@Controller
public class AnnotationRoleGroupMappingController {

	@Resource(name = "authoritiesService")
	private AuthoritiesService authoritiesService;

	/**
	 * move to Role-Group mapping page with selected Role Id
	 * @param roleId Role Id
	 * @param model an object to add attributes
	 * @return move to "/roles/rolegroupmap/"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/rolegroupmapping/addView.do")
	public String addView(@RequestParam(value = "roleId", required = false) String roleId, Model model)
			throws Exception {

		AuthoritySearchVO authoritySearchVO = new AuthoritySearchVO();
		authoritySearchVO.setRoleId(roleId);
		authoritySearchVO.setType("G");
		List<Groups> resultGroups = new ArrayList<Groups>();
		if (!StringUtils.isBlank(roleId)) {
			resultGroups = authoritiesService.getGroupList(authoritySearchVO);
		}
		model.addAttribute("groups", resultGroups);
		model.addAttribute("roleId", roleId);
		return "/roles/rolegroupmap";
	}

	/**
	 * save rows of role-group mapping data
	 * @param groupIds an array of group ids that want to be saved
	 * @param roleId Role Id
	 * @return move to "/rolegroupmapping/addView.do"
	 * @throws Exception fail to save data
	 */
	@RequestMapping("/rolegroupmapping/add.do")
	public String add(@RequestParam(value = "groupId", required = false) String[] groupIds,
			@RequestParam("roleId") String roleId) throws Exception {
		if (groupIds == null) {
			groupIds = new String[0];
		}
		authoritiesService.removeAndSaveList(groupIds, roleId);

		return "forward:/rolegroupmapping/addView.do";
	}
}
