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

package org.anyframe.iam.admin.restrictedtimes.service.impl;

import java.util.List;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.RestrictedTimesRoles;
import org.anyframe.iam.admin.domain.RestrictedTimesRolesId;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesRolesDao;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;

public class RestrictedTimesRolesServiceImpl extends GenericServiceImpl<RestrictedTimesRoles, RestrictedTimesRolesId>
		implements RestrictedTimesRolesService {
	RestrictedTimesRolesDao restrictedTimesRolesDao;

	public RestrictedTimesRolesServiceImpl(RestrictedTimesRolesDao restrictedTimesRolesDao) {
		super(restrictedTimesRolesDao);
		this.restrictedTimesRolesDao = restrictedTimesRolesDao;
	}

	public Page getTimeRoleList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		return this.restrictedTimesRolesDao.getTimeRoleList(restrictedTimesSearchVO);
	}

	public List<Roles> findRoleListByTime(String timeId) throws Exception {
		return restrictedTimesRolesDao.findRoleListByTime(timeId);
	}
	
	public RestrictedTimesRoles save(RestrictedTimesRoles restrictedTimesRoles) throws Exception{
		return restrictedTimesRolesDao.save(restrictedTimesRoles);
	}

	public void saveTimeRoles(List<RestrictedTimesRoles> list) throws Exception {
		String timeId = ((RestrictedTimesRoles) list.get(0)).getId().getTimeId();
		removeTimesRolesByTime(timeId);

		for (int i = 0; i < list.size(); i++) {
			RestrictedTimesRoles restrictedTimesRoles = (RestrictedTimesRoles) list.get(i);
			this.restrictedTimesRolesDao.save(restrictedTimesRoles);
		}
	}

	public void delete(List<RestrictedTimesRolesId> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			RestrictedTimesRolesId restrictedTimesRolesId = (RestrictedTimesRolesId) list.get(i);
			super.remove(restrictedTimesRolesId);
		}
	}

	public void removeTimesRolesByTime(String timeId) throws Exception {
		restrictedTimesRolesDao.removeTimesRolesByTime(timeId);
	}
	
	public void removeAllRestrictedTimesRoles() throws Exception{
		restrictedTimesRolesDao.removeAllRestrictedTimesRoles();
	}
}