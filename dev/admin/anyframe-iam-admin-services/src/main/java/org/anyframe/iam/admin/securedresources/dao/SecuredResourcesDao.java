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

package org.anyframe.iam.admin.securedresources.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.domain.TempSecuredResources;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the
 * SecuredResources table.
 * @author youngmin.jo
 */
public interface SecuredResourcesDao extends IamGenericDao<SecuredResources, String> {

	/**
	 * find list of resources that matches the given search condition in
	 * Secured_Resource table
	 * @param resourceSearchVO an object that contains search conditions
	 * @return Page list of resources that matches the given search condition in
	 * Secured_Resources table
	 * @throws Exception fail to find list
	 */
	Page getList(ResourceSearchVO resourceSearchVO) throws Exception;

	/**
	 * find list of resources that matches the given search condition and does
	 * not exist in Secured_Resourcess_Roles table
	 * @param resourceSearchVO an object that contains search conditions
	 * @return Page list of resources that matches the given search condition
	 * @throws Exception fail to find list
	 */
	Page getUnmappedList(ResourceSearchVO resourceSearchVO) throws Exception;

	/**
	 * find list of resources that matches the given search condition and exist
	 * in Secured_Resourcess_Roles table
	 * @param resourceSearchVO an object that contains search conditions
	 * @return Page list of resources that matches the given search condition
	 * @throws Exception fail to find list
	 */
	Page getMappedList(ResourceSearchVO resourceSearchVO) throws Exception;

	/**
	 * find list of resources that matches the given role Id and all its child
	 * roles in Secured_Resources_Roles table
	 * @param resourceSearchVO an object that contains search conditions
	 * @return Page list of resource that matches the given search condition
	 * @throws Exception fail to find list
	 */
	Page getListwithLevel(ResourceSearchVO resourceSearchVO) throws Exception;
	
	List<TempSecuredResources> makeAllTempResourcesList() throws Exception;
	
	void removeAllSecuredResources() throws Exception;
}