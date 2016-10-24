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
 * IDS Domain Object that related with IDS table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "IDS")
public class Ids extends BaseObject implements Serializable {
	private String tableName;

	private Long nextId;

	@Id
	@Column(name = "TABLE_NAME", unique = true, nullable = false, length = 16)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "NEXT_ID", nullable = false, precision = 30, scale = 0)
	public Long getNextId() {
		return this.nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		Ids pojo = (Ids) o;

		if ((nextId != null) ? (!nextId.equals(pojo.nextId)) : (pojo.nextId != null)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((nextId != null) ? nextId.hashCode() : 0);

		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("tableName").append("='").append(getTableName()).append("', ");
		sb.append("nextId").append("='").append(getNextId()).append("'");
		sb.append("]");

		return sb.toString();
	}
}
