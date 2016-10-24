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
import java.util.ArrayList;

/**
 * An object that represent node of tree in JS Tree UI
 * @author AnyframeGen
 * 
 */
public class JSTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Attributes attributes;

	private Data data;

	private String state;
	
	private ArrayList<JSTreeNode> children;
	
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<JSTreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<JSTreeNode> children) {
		this.children = children;
	}
}
