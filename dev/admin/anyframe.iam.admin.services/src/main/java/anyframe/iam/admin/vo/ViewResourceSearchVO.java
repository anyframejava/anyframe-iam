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

package anyframe.iam.admin.vo;

/**
 * An Object that contains search conditions related with View Resources table
 * @author AnyframeGen
 * 
 */
public class ViewResourceSearchVO extends CommonSearchVO {
	private static final long serialVersionUID = 1L;
	
	private String parentViewResourceId;

	private String refId;

	private String type;
	
	public String getParentViewResourceId(){
		return parentViewResourceId;
	}
	
	public void setParentViewResourceId(String parentViewResourceId){
		this.parentViewResourceId = parentViewResourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
