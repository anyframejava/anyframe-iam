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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

/**
 * An object to make JSTree JSON data
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "GROUPS")
public class IamTree extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String title;

	private String state;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TITLE", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "STATE", length = 8)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		IamTree pojo = (IamTree) o;

		if ((title != null) ? (!title.equals(pojo.title)) : (pojo.title != null)) {
			return false;
		}

		if ((state != null) ? (!state.equals(pojo.state)) : (pojo.state != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((title != null) ? title.hashCode() : 0);
		result = (31 * result) + ((state != null) ? state.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");
		sb.append("title").append("='").append(getTitle()).append("', ");
		sb.append("state").append("='").append(getState()).append("', ");
		sb.append("]");

		return sb.toString();
	}
}
