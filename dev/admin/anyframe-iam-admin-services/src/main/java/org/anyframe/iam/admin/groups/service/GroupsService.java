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

package org.anyframe.iam.admin.groups.service;

import java.util.List;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.TempGroups;

/**
 * An interface that provides services about Groups table
 * 
 * @author jongpil.park
 */
public interface GroupsService extends GenericService<Groups, String> {

	/**
	 * find child nodes that extend the given node in one level
	 * @param parentNode parent node
	 * @return List<IamTree> all child nodes that extend the given node in one
	 * level
	 * @throws Exception fail to find nodes
	 */
	List<IamTree> getGroupTree(String parentNode) throws Exception;

	/**
	 * find root node in Groups_Hierarchy table
	 * @return List<IamTree> root node of Groups table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfGroups() throws Exception;

	/**
	 * save that the given node in Groups table
	 * @param groups a row that want to save
	 * @return Groups return Groups object if saving finish successfully
	 * @throws Exception fail to save the row
	 */
	Groups save(Groups groups) throws Exception;
	
	List<Groups> save(List<TempGroups> tempGroupList) throws Exception;
	
	Groups saveTempGroupsToGroups(TempGroups tempGroups) throws Exception;

	Groups saveWithoutHierarchy(Groups groups) throws Exception;
	
	/**
	 * update the given row from Groups table
	 * @param groups a row that want to update
	 * @throws Exception fail to update the row
	 */
	void update(Groups groups) throws Exception;

	/**
	 * delete the given row from Groups table
	 * @param currentNode node Id that want to delete
	 * @return void
	 * @throws Exception fail to delete the row
	 */
	void remove(String currentNode) throws Exception;
	
	void removeAllGroups() throws Exception;

	/**
	 * find all Groups domains
	 * @return List<Groups> list of Groups domain objects
	 * @throws Exception fail to find list
	 */
	List<Groups> getList() throws Exception;
	
	/**
	 * find list of group name that have the given keyword
	 * @param keyword
	 * @return list of group name
	 * @throws Exception fail to find list
	 */
	String getGroupNameList(String keyword) throws Exception;
	
	/**
	 * find group ID that matches the given group name
	 * @param groupName
	 * @return group ID
	 * @throws Exception fail to find group ID
	 */
	String getGroupIdByGroupName(String groupName) throws Exception;
	
	/**
	 * find all ancestor for the given group ID
	 * @param groupId
	 * @return List of group ID
	 * @throws Exception
	 */
	List<String> getParentsGroupIds(String groupId) throws Exception;
	
	List<TempGroups> makeAllTempGroupsList() throws Exception;
}