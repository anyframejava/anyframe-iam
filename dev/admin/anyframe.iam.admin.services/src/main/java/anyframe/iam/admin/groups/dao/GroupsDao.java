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

package anyframe.iam.admin.groups.dao;

import java.util.List;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.IamTree;

/**
 * An interface that provides a data management interface to the Groups table.
 * 
 * @author jongpil.park
 */
public interface GroupsDao extends IamGenericDao<Groups, String> {

	/**
	 * find child nodes from Groups table and Groups_Hierarchy table that extend
	 * the given node in one level
	 * @param parentNode parent node
	 * @return List<IamTree> all the child nodes that extend the given node in
	 * one level
	 * @throws Exception fail to find nodes
	 */
	List<IamTree> getGroupTree(String parentNode) throws Exception;

	/**
	 * find root node from Groups_Hierarchy table
	 * @return List<IamTree> root node of Groups table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfGroups() throws Exception;

	/**
	 * find all the child node that extend the given node in Groups_Hierarchy
	 * table
	 * @param parentNode parent node
	 * @return List<String> list of the child node that extend the given node
	 * @throws Exception fail to find list
	 */
	List<String> getGroupHierarchy(String parentNode) throws Exception;

	/**
	 * update the given row from Groups table
	 * @param groups a row that want to update
	 * @throws Exception fail to update a row
	 */
	void update(Groups groups) throws Exception;

	/**
	 * find all Groups domains
	 * @return List<Groups> list of Groups domain objects
	 * @throws Exception fail to find list
	 */
	List<Groups> getList() throws Exception;
	
	/**
	 * find all group name that have the given keyword
	 * @param keyword String keyword
	 * @return list of group name that matches the given keyword
	 * @throws Exception fail to find list
	 */
	String getGroupNameList(String keyword) throws Exception;
	/**
	 * find group ID that matches the given group name
	 * @param groupName
	 * @return	group ID
	 * @throws Exception fail to find group ID
	 */
	String getGroupIdByGroupName(String groupName) throws Exception;
}