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

package anyframe.iam.admin.users.service;

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.GroupsUsers;
import anyframe.iam.admin.domain.TempUsers;
import anyframe.iam.admin.domain.Users;
import anyframe.iam.admin.vo.UserSearchVO;

/**
 * An interface that provides services related with Users table
 * @author jongpil.park
 * 
 */
public interface UsersService extends GenericService<Users, String> {

	/**
	 * find list of users that matches the given search conditions in Users
	 * table
	 * @param userSearchVO an object that contains search conditions
	 * @return Page list of users that matches the given search conditions in
	 * Users table
	 * @throws Exception fail to find list
	 */
	Page getList(UserSearchVO userSearchVO) throws Exception;

	/**
	 * find a row that matches the given user id in Users table
	 * @param userId user id
	 * @return Users Users domain object that matches the given user id
	 * @throws Exception fail to find a rows
	 */
	Users get(String userId) throws Exception;

	/**
	 * save rows in Users table and Authorities table
	 * @param users Users domain object that want to be saved
	 * @param authorities authorities that user has
	 * @return Users return Users domain object if saving finish successfully
	 * @throws Exception fail to save rows
	 */
	Users save(Users users, Authorities[] authorities) throws Exception;

	/**
	 * update a row that matches the given user data
	 * @param users Users domain object that want to be updated
	 * @param newGroupsUsers user group that want to be changed
	 * @param currentGroupsUsers current user group
	 * @param authorities authorities that user has
	 * @return Users return Users domain object if saving finish successfully
	 * @throws Exception fail to update the row
	 */
	Users update(Users users, GroupsUsers newGroupsUsers, GroupsUsers currentGroupsUsers, Authorities[] authorities)
			throws Exception;

	/**
	 * delete rows that matches the given user Ids
	 * @param userId an array of user id that want to be deleted
	 * @throws Exception fail to delete rows
	 */
	void delete(String[] userId) throws Exception;

	/**
	 * find list of Users domain object that matches the given user name in
	 * USERS table
	 * @param userName user name
	 * @return List List of Users domain objects
	 * @throws Exception fail to find list
	 */
	@SuppressWarnings("unchecked")
	List findUserByName(String userName) throws Exception;
	
	List<TempUsers> makeAllTempUsersList() throws Exception;
	
	void removeAllUsers() throws Exception;
	
	List save(List tempUsers) throws Exception;
	
}