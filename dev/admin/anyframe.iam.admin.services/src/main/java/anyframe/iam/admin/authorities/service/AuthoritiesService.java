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

package anyframe.iam.admin.authorities.service;

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.AuthoritiesId;
import anyframe.iam.admin.vo.AuthoritySearchVO;

/**
 * An interface that provides services about Authorities table.
 * 
 * @author youngmin.jo
 */
public interface AuthoritiesService extends GenericService<Authorities, AuthoritiesId> {

	/**
	 * find a list of users from users table that matches the given search
	 * conditions and doesn't exist in Authorities table
	 * 
	 * @param authoritySearchVO an object that contains search Conditions
	 * @return Page a list of users from users table that matches the given
	 * search conditions and doesn't exist in Authorities table
	 * @throws Exception fail to get rows
	 */
	Page getList(AuthoritySearchVO authoritySearchVO) throws Exception;

	/**
	 * find list of users from users table that matches the given search
	 * conditions and exists in Authorities table
	 * @param authoritySearchVO an object that contains search conditions
	 * @return Page a list of users from users table that matches the given
	 * search conditions and exists in Authorities table
	 * @throws Exception fail to get rows
	 */
	Page getExistList(AuthoritySearchVO authoritySearchVO) throws Exception;

	/**
	 * find list of groups that matches the given search conditions in groups
	 * table
	 * @param authoritySearchVO an object that contains search conditions
	 * @return List a list of groups from groups table which has given search
	 * conditions
	 * @throws Exception fail to get a list
	 */
	@SuppressWarnings("unchecked")
	List getGroupList(AuthoritySearchVO authoritySearchVO) throws Exception;

	/**
	 * find list of group Ids that matches the given conditions in groups Table
	 * @param authoritySearchVO an object that contains search conditions
	 * @return List list of group Ids that matches the given conditions in
	 * groups Table
	 * @throws Exception fail to get a list
	 */
	@SuppressWarnings("unchecked")
	List getGroupIdList(AuthoritySearchVO authoritySearchVO) throws Exception;

	/**
	 * remove rows which matches the given roleId in authorities table, then
	 * save new rows
	 * @param groupId an array of group ids
	 * @param roleId Role Id
	 * @throws Exception fail to remove or save
	 */
	void removeAndSaveList(String[] groupId, String roleId) throws Exception;

	
	void removeAllAuthorities() throws Exception;
	
	/**
	 * add users which have the given roleId in authorities table
	 * @param userIds an array of user ids
	 * @param roleId Role Id
	 * @throws Exception fail to insert into table
	 */
	void addUser(String[] userIds, String roleId) throws Exception;

	/**
	 * delete users which match the given userIds in authorities table.
	 * @param userIds an array of user ids
	 * @param roleId Role Id
	 * @throws Exception fail to delete the data
	 */
	void deleteUser(String[] userIds, String roleId) throws Exception;
}