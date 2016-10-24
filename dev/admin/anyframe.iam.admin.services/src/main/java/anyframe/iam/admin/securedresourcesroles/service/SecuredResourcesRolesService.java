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

package anyframe.iam.admin.securedresourcesroles.service;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.SecuredResourcesRoles;
import anyframe.iam.admin.domain.SecuredResourcesRolesId;

/**
 * An interface that provides services related with Secured_Resources_Roles
 * table
 * @author youngmin.jo
 */
public interface SecuredResourcesRolesService extends GenericService<SecuredResourcesRoles, SecuredResourcesRolesId> {

	/**
	 * add rows in Secured_Resources_Roles table
	 * @param resourceIds an array of resource ids that want to be saved
	 * @param roleId role Id
	 * @throws Exception fail to save rows
	 */
	void addSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception;

	/**
	 * delete rows that match the given resource Ids in Secured_Resources_Roles
	 * table
	 * @param resourceIds an array of resource ids that want to be saved
	 * @param roleId role Id
	 * @throws Exception fail to delete rows
	 */
	void deleteSecuredResourceRoles(String[] resourceIds, String roleId) throws Exception;

	/**
	 * save rows in Secured_Resources_Roles table
	 * @param securedResourcesRoles a row that want to save
	 * @return SecuredResourcesRoles return SecuredResourcesRoles domain object
	 * @throws Exception fail to delete rows
	 */
	SecuredResourcesRoles save(SecuredResourcesRoles securedResourcesRoles) throws Exception;

}