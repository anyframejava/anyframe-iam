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

package org.anyframe.iam.admin.roles.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.TempRoles;


/**
 * An interface that provides a data management interface to the Roles table.
 * @author jongpil.park
 */
public interface RolesDao extends IamGenericDao<Roles, String> {

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
	 * @return List<IamTree> root node of Role_Hierarchy table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfRoles() throws Exception;

	/**
	 * find list of roles that match the given user Id
	 * @param userId user Id
	 * @return list of roles that match the given user id
	 * @throws Exception fail to find list
	 */
	List<Roles> findRoles(String userId) throws Exception;

	/**
	 * update the given row from Roles table
	 * @param roles a row that want to update
	 * @throws Exception fail to update
	 */
	void update(Roles roles) throws Exception;

	/**
	 * find list of child node that extend the given node in Roles_Hierarchy
	 * table
	 * @param parentNode parent node
	 * @return List<String> list of the child node that extend the given node
	 * @throws Exception fail to find nodes
	 */
	List<String> getRoleHierarchy(String parentNode) throws Exception;

	/**
	 * find all Roles domains
	 * @return List<Roles> List of Roles domain objects
	 * @throws Exception fail to find list
	 */
	List<Roles> getList() throws Exception;
	
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
	
	List<TempRoles> makeAllTempRolesList() throws Exception;
	
	void removeAllRoles() throws Exception;
	
}