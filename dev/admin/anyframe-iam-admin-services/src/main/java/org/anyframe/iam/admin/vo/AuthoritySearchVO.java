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

package org.anyframe.iam.admin.vo;

/**
 * An Object that contains search conditions related with Authorities table
 * @author AnyframeGen
 * 
 */
public class AuthoritySearchVO extends CommonSearchVO {
	private static final long serialVersionUID = 1L;

	private String type;

	private String roleId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String toString() {
		StringBuffer arguments = new StringBuffer();
		arguments.append(" searchKeyword - " + getSearchKeyword() + "\n");
		arguments.append(" searchCondition - " + getSearchCondition() + "\n");
		arguments.append(" searchType - " + type + "\n");
		arguments.append(" roleId - " + roleId + "\n");
		return arguments.toString();
	}

}
