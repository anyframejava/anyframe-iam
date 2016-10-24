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

package anyframe.iam.admin.authorities.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.common.Page;
import anyframe.iam.admin.authorities.service.AuthoritiesService;
import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.vo.AuthoritySearchVO;

/**
 * Annotation Authorities Controller
 * @author jongpil.park
 * 
 */

@Controller
public class AnnotationAuthoritiesController {
	@Resource(name = "authoritiesService")
	private AuthoritiesService authoritiesService;

	/**
	 * add role-user mapping data in Authorities table
	 * @param userIds an array of user Ids that want to be added
	 * @param roleId role Id
	 * @return move to "/authorities/existListData.do?&isMapped=Y"
	 * @throws Exception fail to add data
	 */
	@JsonError
	@RequestMapping("/authorities/add.do")
	public String add(@RequestParam("userId") String[] userIds, @RequestParam("roleId") String roleId) throws Exception {
		authoritiesService.addUser(userIds, roleId);
		return "forward:/authorities/existListData.do?&isMapped=Y";
	}

	/**
	 * delete role-user mapping data in Authorities table
	 * @param userIds an array of user Ids that want to be deleted
	 * @param roleId role Id
	 * @return move to "authorities/listData.do?"
	 * @throws Exception fail to delete data
	 */
	@JsonError
	@RequestMapping("/authorities/delete.do")
	public String delete(@RequestParam("userId") String[] userIds, @RequestParam("roleId") String roleId)
			throws Exception {
		authoritiesService.deleteUser(userIds, roleId);
		return "forward:/authorities/listData.do?";
	}

	/**
	 * make role-user mapping data that doesn't exist in Authorities table to
	 * use in the grid component
	 * @param searchVO an object that contains search conditions
	 * @param model an object to contain attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/authorities/listData.do")
	public String listData(AuthoritySearchVO searchVO, Model model) throws Exception {

		Page resultPage = authoritiesService.getList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * make role-user mapping data that exist in Authorities table to use in the
	 * grid component
	 * @param searchVO an object that contains search conditions
	 * @param model an object to contain attributes
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@JsonError
	@RequestMapping("/authorities/existListData.do")
	public String existListData(AuthoritySearchVO searchVO, Model model) throws Exception {

		Page resultPage = authoritiesService.getExistList(searchVO);
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

}
