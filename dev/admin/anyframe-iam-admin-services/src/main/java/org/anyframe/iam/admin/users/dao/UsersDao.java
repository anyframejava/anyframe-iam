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

package org.anyframe.iam.admin.users.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.TempUsers;
import org.anyframe.iam.admin.domain.Users;
import org.anyframe.iam.admin.vo.UserSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the Users table.
 * @author jongpil.park
 */
public interface UsersDao extends IamGenericDao<Users, String> {

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
}