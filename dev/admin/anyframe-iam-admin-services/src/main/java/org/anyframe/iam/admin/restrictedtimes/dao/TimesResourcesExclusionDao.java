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
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.TimesResourcesExclusion;
import org.anyframe.iam.admin.domain.TimesResourcesExclusionId;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the
 * TimesResourcesExclusion table.
 * @author sungryong.kim
 */
public interface TimesResourcesExclusionDao extends IamGenericDao<TimesResourcesExclusion, TimesResourcesExclusionId> {

	/**
	 * find list of time exclusion that matches the given search conditions
	 * @param restrictedTimesSearchVO an object that contains search conditions
	 * @return Page list of time exclusion
	 * @throws Exception fail to find list
	 */
	Page getTimeExclusionList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception;

	/**
	 * find list of time exclusion that matches the given timeID and resourceID
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @return List list of time exclusion
	 * @throws Exception fail to find list
	 */
	List<Roles> findRoleListByTimeResource(String timeId, String resourceId) throws Exception;

	/**
	 * remove time exclusion data that matches the given TimeID and ResourceID
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @throws Exception fail to remove data
	 */
	void removeTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception;
	
	void removeAllTimesResourcesExclusion() throws Exception;
}