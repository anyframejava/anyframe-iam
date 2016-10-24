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

package anyframe.iam.admin.restrictedtimes.dao;

import java.util.List;

import anyframe.common.Page;
import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.RestrictedTimesRoles;
import anyframe.iam.admin.domain.RestrictedTimesRolesId;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * An interface that provides a data management interface to the
 * RestrictedTimesRoles table.
 * @author sungryong.kim
 */
public interface RestrictedTimesRolesDao extends IamGenericDao<RestrictedTimesRoles, RestrictedTimesRolesId> {

	/**
	 * find list of Time-Role Mapping data that matches the given search
	 * conditions
	 * @param restrictedTimesSearchVO
	 * @return Page List of Time-Role Mapping data
	 * @throws Exception fail to find list
	 */
	Page getTimeRoleList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception;

	/**
	 * find list of Role that matches the given time ID
	 * @param timeId time ID
	 * @return list of Role
	 * @throws Exception fail to find list
	 */
	List findRoleListByTime(String timeId) throws Exception;

	/**
	 * remove Time-Role Mapping data that matches the given Time ID
	 * @param timeId time ID
	 * @throws Exception fail to remove data
	 */
	void removeTimesRolesByTime(String timeId) throws Exception;
}