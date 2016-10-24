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

package anyframe.iam.admin.securedresourcesroles.service.impl;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.domain.SecuredResourcesRoles;
import anyframe.iam.admin.domain.SecuredResourcesRolesId;
import anyframe.iam.admin.securedresourcesroles.dao.SecuredResourcesRolesDao;
import anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;

public class SecuredResourcesRolesServiceImpl extends
		GenericServiceImpl<SecuredResourcesRoles, SecuredResourcesRolesId> implements SecuredResourcesRolesService {
	SecuredResourcesRolesDao securedResourcesRolesDao;

	public SecuredResourcesRolesServiceImpl(SecuredResourcesRolesDao securedResourcesRolesDao) {
		super(securedResourcesRolesDao);
		this.securedResourcesRolesDao = securedResourcesRolesDao;
	}

	public void addSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception {
		String currentTime = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");

		if (resourceIds != null) {
			for (int i = 0; i < resourceIds.length; i++) {
				SecuredResourcesRoles securedResourcesRoles = new SecuredResourcesRoles();
				SecuredResourcesRolesId securedResourcesRolesId = new SecuredResourcesRolesId();
				securedResourcesRoles.setCreateDate(currentTime);
				securedResourcesRolesId.setRoleId(roleId);
				securedResourcesRolesId.setResourceId(resourceIds[i]);
				securedResourcesRoles.setId(securedResourcesRolesId);
				this.securedResourcesRolesDao.save(securedResourcesRoles);
			}
		}
	}

	public void deleteSecuredResourceRoles(String[] resourceIds, String roleId) throws Exception {

		if (resourceIds != null) {
			for (int i = 0; i < resourceIds.length; i++) {
				SecuredResourcesRolesId securedResourcesRolesId = new SecuredResourcesRolesId();
				securedResourcesRolesId.setRoleId(roleId);
				securedResourcesRolesId.setResourceId(resourceIds[i]);
				remove(securedResourcesRolesId);
			}
		}
	}

	public SecuredResourcesRoles save(SecuredResourcesRoles securedResourcesRoles) throws Exception {
		return this.securedResourcesRolesDao.save(securedResourcesRoles);
	}
}