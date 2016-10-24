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

package org.anyframe.iam.admin.securedresourcesroles.dao;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.SecuredResourcesRoles;
import org.anyframe.iam.admin.domain.SecuredResourcesRolesId;


/**
 * An interface that provides a data management interface to the
 * SecuredResourcesRoles table.
 * @author youngmin.jo
 */
public interface SecuredResourcesRolesDao extends IamGenericDao<SecuredResourcesRoles, SecuredResourcesRolesId> {

	/**
	 * delete a row that matches the given role id in Secured_Resources_Roles
	 * table
	 * @param roleId role id
	 * @throws Exception fail to delete the row
	 */
	void remove(String roleId) throws Exception;
	
	void removeAllSecuredResourcesRoles() throws Exception;
}