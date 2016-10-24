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
 * An Object that contains search conditions related with Restricted_Times table
 * @author AnyframeGen
 * 
 */
public class RestrictedTimesSearchVO extends CommonSearchVO {
	private static final long serialVersionUID = 1L;

	private String timeId;

	private String searchType;
	
	private String systemName;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public String getSearchtype() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String toString() {
		StringBuffer arguments = new StringBuffer();
		arguments.append(" searchKeyword - " + getSearchKeyword() + "\n");
		arguments.append(" searchCondition - " + getSearchCondition() + "\n");
		arguments.append(" searchType - " + searchType + "\n");
		return arguments.toString();
	}
}
