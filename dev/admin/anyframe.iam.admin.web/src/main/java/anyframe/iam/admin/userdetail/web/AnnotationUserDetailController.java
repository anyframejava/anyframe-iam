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

package anyframe.iam.admin.userdetail.web;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.common.util.StringUtil;
import anyframe.iam.admin.users.service.UsersService;

/**
 * Annotation User detail Controller
 * @author jongpil.park
 * 
 */
@Controller
public class AnnotationUserDetailController {

	@Resource(name = "usersService")
	private UsersService usersService;

	/**
	 * move to user list page
	 * @return move to user list page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/userdetail/list.do")
	public String list() throws Exception {
		return "/users/usergrouplist";
	}

	/**
	 * move to user Id checking page
	 * @param userId user Id
	 * @param model Model object to add attributes
	 * @return move to id checking page
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/userdetail/checkid.do")
	public String checkId(@RequestParam(value = "userId") String userId, Model model) throws Exception {
		userId = StringUtil.null2str(userId);
		model.addAttribute("userId", userId);
		return "/users/checkid";
	}

	/**
	 * check user Id if it is duplicated
	 * @param userId user Id that want to be checked
	 * @param model Model object to add attributes
	 * @return move to id checking page
	 * @throws Exception fail to check Id
	 */
	@RequestMapping("/userdetail/duplicationconfirm.do")
	public String duplicationConfirm(@RequestParam(value = "userId", required = true) String userId, Model model)
			throws Exception {
		String newUserId = new String(userId.getBytes("8859_1"), "utf-8");
		if (!StringUtils.isBlank(newUserId)) {
			boolean exist = usersService.exists(userId);
			model.addAttribute("userId", newUserId);
			model.addAttribute("exist", exist);
		}
		else
			model.addAttribute("exist", true);
		return "/users/checkid";
	}
}
