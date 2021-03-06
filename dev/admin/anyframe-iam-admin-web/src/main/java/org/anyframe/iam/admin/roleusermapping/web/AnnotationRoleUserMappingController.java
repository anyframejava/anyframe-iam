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

package org.anyframe.iam.admin.roleusermapping.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Annotation Role-User mapping Controller
 * @author youngmin.jo
 * 
 */
@Controller
public class AnnotationRoleUserMappingController {

	/**
	 * move to Role-User mapping page
	 * @return move to "role/roleusermap"
	 * @throws Exception fail to move to the page
	 */
	@RequestMapping("/roleusermapping/addView.do")
	public String addView() throws Exception {
		return "/roles/roleusermap";
	}
}
