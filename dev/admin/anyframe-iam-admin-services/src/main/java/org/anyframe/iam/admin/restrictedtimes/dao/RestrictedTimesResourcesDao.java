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

package org.anyframe.iam.admin.restrictedtimes.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.RestrictedTimesResources;
import org.anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the
 * RestrictedTimesResources table.
 * @author sungryong.kim
 */
public interface RestrictedTimesResourcesDao extends
		IamGenericDao<RestrictedTimesResources, RestrictedTimesResourcesId> {

	/**
	 * find list of time resources that matches the given search conditions
	 * @param restrictedTimesSearchVO an object that contains search conditions
	 * @return Page List of time resources
	 * @throws Exception fail to find time resources
	 */
	Page getTimeResourceList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception;

	/**
	 * find list of Role that matches the given time ID
	 * @param timeId time ID
	 * @return List list of Role
	 * @throws Exception fail to find list
	 */
	List<Roles> findRoleListByTime(String timeId) throws Exception;

	/**
	 * find list of Resources that matches the given time ID
	 * @param searchVO an object that contains search conditions
	 * @return Page List of resources
	 * @throws Exception fail to find resources
	 */
	Page findResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception;

	/**
	 * find list of Resources that matches the given search conditions and does
	 * not have any mapping-information with Time
	 * @param searchVO an object that contains search conditions
	 * @return Page List of resources
	 * @throws Exception fail to find resources
	 */
	Page findUnmappedResourceListByTime(RestrictedTimesSearchVO resourceSearchVO) throws Exception;
	
	void removeAllRestrictedTimesResources() throws Exception;
}