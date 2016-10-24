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

package anyframe.iam.admin.roles.service;

import java.util.List;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.Roles;

/**
 * An interface that provides services about Roles table
 * 
 * @author jongpil.park
 */
public interface RolesService extends GenericService<Roles, String> {

	/**
	 * find child nodes from Roles and Roles_Hierarchy table that extend the
	 * given node in one level
	 * @param parentNode parent node
	 * @return List<IamTree> all the child nodes that extend the given node in
	 * one level
	 * @throws Exception fail to find nodes
	 */
	List<IamTree> getRoleTree(String parentNode) throws Exception;

	/**
	 * find root node from Role_Hierarchy table
	 * @return List<IamTree> root node of Role table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfRoles() throws Exception;

	/**
	 * find list of roles that match the given user Id
	 * @param userId user id
	 * @return List<Roles> list of roles that match the given user id
	 * @throws Exception fail to find list
	 */
	List<Roles> findRoles(String userId) throws Exception;

	/**
	 * update the given row from Roles table
	 * @param roles a row that want to update
	 * @throws Exception fail to update the row
	 */
	void update(Roles roles) throws Exception;

	/**
	 * delete the given row from Roles table
	 * @param currentNode node Id that want to delete
	 * @throws Exception fail to delete the row
	 */
	void remove(String currentNode) throws Exception;

	/**
	 * find all Roles domains
	 * @return List<Roles> List of Roles domain objects
	 * @throws Exception fail to find list
	 */
	List<Roles> getList() throws Exception;

	/**
	 * save the given row from Roles table
	 * @param roles a row that want to update
	 * @return Roles return Roles domain object
	 * @throws Exception fail to update the row
	 */
	Roles save(Roles roles) throws Exception;
	
	/**
	 * find list of Role name that matches the given keyword
	 * @param keyword
	 * @return list of Role name
	 * @throws Exception fail to find list
	 */
	String getRoleNameList(String keyword) throws Exception;
	
	/**
	 * find Role ID that matches the given role name
	 * @param roleName
	 * @return Role ID
	 * @throws Exception fail to find role ID
	 */
	String getRoleIdByRoleName(String roleName) throws Exception;
	
	/**
	 * find all ancestor for the given role Id
	 * @param roleId
	 * @return List of role ID
	 * @throws Exception fail to find list
	 */
	List<String> getParentsRoleIds(String roleId) throws Exception;
	
	
}