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

package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import anyframe.core.generic.model.BaseObject;

/**
 * The primary key class for the GROUPS_HIERARCHY database table.
 * 
 */
@Embeddable
public class GroupsHierarchyId extends BaseObject implements Serializable {
	private String parentGroup;

	private String childGroup;

	@Column(name = "PARENT_GROUP", nullable = false, length = 20)
	public String getParentGroup() {
		return this.parentGroup;
	}

	public void setParentGroup(String parentGroup) {
		this.parentGroup = parentGroup;
	}

	@Column(name = "CHILD_GROUP", nullable = false, length = 20)
	public String getChildGroup() {
		return this.childGroup;
	}

	public void setChildGroup(String childGroup) {
		this.childGroup = childGroup;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		GroupsHierarchyId pojo = (GroupsHierarchyId) o;

		if ((parentGroup != null) ? (!parentGroup.equals(pojo.parentGroup)) : (pojo.parentGroup != null)) {
			return false;
		}

		if ((childGroup != null) ? (!childGroup.equals(pojo.childGroup)) : (pojo.childGroup != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((parentGroup != null) ? parentGroup.hashCode() : 0);
		result = ((childGroup != null) ? childGroup.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("parentGroup").append("='").append(getParentGroup()).append("', ");
		sb.append("childGroup").append("='").append(getChildGroup()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
