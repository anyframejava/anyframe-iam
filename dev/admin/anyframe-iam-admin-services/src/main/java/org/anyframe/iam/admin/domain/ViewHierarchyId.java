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

package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the ROLES_HIERARCHY database table.
 * 
 */

@Embeddable
public class ViewHierarchyId implements Serializable{
	private static final long serialVersionUID = 1L;

	private String parentView;
	
	private String childView;
	
	@Column(name = "PARENT_VIEW", nullable = false, length = 50)
	public String getParentView(){
		return this.parentView;
	}
	
	public void setParentView(String parentView){
		this.parentView = parentView;
	}
	
	@Column(name = "CHILD_VIEW", nullable = false, length = 50)
	public String getChildView(){
		return this.childView;
	}
	
	public void setChildView(String childView){
		this.childView = childView;
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		ViewHierarchyId pojo = (ViewHierarchyId) o;

		if ((parentView != null) ? (!parentView.equals(pojo.parentView)) : (pojo.parentView != null)) {
			return false;
		}

		if ((childView != null) ? (!childView.equals(pojo.childView)) : (pojo.childView != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = (31 * result) + ((parentView != null) ? parentView.hashCode() : 0);
		result = ((childView != null) ? childView.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("parentView").append("='").append(getParentView()).append("', ");
		sb.append("childView").append("='").append(getChildView()).append("'");
		sb.append("]");

		return sb.toString();
	}

}
