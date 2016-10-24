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

package org.anyframe.iam.admin.authorities.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.Authorities;
import org.anyframe.iam.admin.domain.AuthoritiesId;
import org.anyframe.iam.admin.vo.AuthoritySearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the Authorities
 * table.
 * @author youngmin.jo
 */
public interface AuthoritiesDao extends IamGenericDao<Authorities, AuthoritiesId> {

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
	 * delete a row that matches the given id in Authorities table
	 * @param id subject id that want to delete
	 * @param type resource type
	 * @throws Exception fail to delete
	 */
	void remove(String id, String type) throws Exception;

	/**
	 * find list of groups that matches the given search conditions in groups
	 * table
	 * @param authoritySearchVO an object that contains search conditions
	 * @return List a list of groups from groups table which has given search
	 * conditions
	 * @throws Exception fail to get a list
	 */
	@SuppressWarnings("unchecked")
	List getGroupList(AuthoritySearchVO authorituSearchVO) throws Exception;

	/**
	 * find list of group Ids that matches the given conditions in groups Table
	 * @param authoritySearchVO an object that contains search conditions
	 * @return List list of group Ids that matches the given conditions in
	 * groups Table
	 * @throws Exception fail to get a list
	 */
	@SuppressWarnings("unchecked")
	List getGroupIdList(AuthoritySearchVO authorituSearchVO) throws Exception;
	
	void removeAllAuthorities() throws Exception;
}