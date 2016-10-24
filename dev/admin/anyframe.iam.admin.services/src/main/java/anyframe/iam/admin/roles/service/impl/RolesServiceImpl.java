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

package anyframe.iam.admin.roles.service.impl;

import java.util.List;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.authorities.dao.AuthoritiesDao;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.roles.dao.RolesDao;
import anyframe.iam.admin.roles.service.RolesService;
import anyframe.iam.admin.securedresourcesroles.dao.SecuredResourcesRolesDao;

public class RolesServiceImpl extends GenericServiceImpl<Roles, String> implements RolesService {
	RolesDao rolesDao;

	AuthoritiesDao authoritiesDao;

	SecuredResourcesRolesDao securedResourcesRolesDao;

	public RolesServiceImpl(RolesDao rolesDao) {
		super(rolesDao);
		this.rolesDao = rolesDao;
	}

	public void setAuthoritiesDao(AuthoritiesDao authoritiesDao) {
		this.authoritiesDao = authoritiesDao;
	}

	public void setSecuredResourcesRolesDao(SecuredResourcesRolesDao securedResourcesRolesDao) {
		this.securedResourcesRolesDao = securedResourcesRolesDao;
	}

	public List<IamTree> getRoleTree(String parentNode) throws Exception {
		return rolesDao.getRoleTree(parentNode);
	}

	public List<IamTree> getRootNodeOfRoles() throws Exception {
		return rolesDao.getRootNodeOfRoles();
	}

	public List<Roles> findRoles(String userId) throws Exception {
		return rolesDao.findRoles(userId);
	}

	public void update(Roles roles) throws Exception {
		rolesDao.update(roles);
	}

	public void remove(String currentNode) throws Exception {
		if (currentNode != null) {
			List<String> childNode = rolesDao.getRoleHierarchy(currentNode);
			int childNodeCount = childNode.size();

			if (childNodeCount > 0) {
				for (int i = 0; i < childNodeCount; i++) {
					rolesDao.remove(childNode.get(i).toString());
				}
			}

			securedResourcesRolesDao.remove(currentNode);

			rolesDao.remove(currentNode);
		}
		else {
			throw new Exception();
		}
	}

	public List<Roles> getList() throws Exception {
		return rolesDao.getList();
	}

	public Roles save(Roles roles) throws Exception {
		return rolesDao.save(roles);
	}
}