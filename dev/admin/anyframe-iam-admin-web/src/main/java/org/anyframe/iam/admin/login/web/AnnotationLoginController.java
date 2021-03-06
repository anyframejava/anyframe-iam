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

package org.anyframe.iam.admin.login.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Annotation Login Controller
 * @author byounghoon.woo
 * 
 */
@Controller
@SessionAttributes("login")
public class AnnotationLoginController {

	/**
	 * control re-login when session is terminated
	 * @param session HttpSession
	 * @param inputPage requested URL that didn't operate because session was
	 * terminated
	 * @return move to requested URL if inputPage not null
	 * @throws Exception fail to move
	 */
	@RequestMapping("/login/relogin.do")
	public String login(HttpSession session, @RequestParam(required = false, value = "inputPage") String inputPage)
			throws Exception {
		if (inputPage == null) {
			return "forward:/index.jsp?flag=L";
		}
		else {
			return "forward:" + inputPage;
		}
	}

}
