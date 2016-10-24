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

package org.anyframe.iam.admin.restrictedtimes.service;

import java.util.List;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.TimesResourcesExclusion;
import org.anyframe.iam.admin.domain.TimesResourcesExclusionId;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides services related with Times_resources_Exclusion
 * table
 * @author sungryong.kim
 * 
 */
public interface TimesResourcesExclusionService extends
		GenericService<TimesResourcesExclusion, TimesResourcesExclusionId> {

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
	@SuppressWarnings("unchecked")
	List findRoleListByTimeResource(String timeId, String resourceId) throws Exception;

	/**
	 * save Time-Exclusion data
	 * @param list list of Time-Exclusion data
	 * @throws Exception fail to save data
	 */
	void saveTimeExclusion(List<TimesResourcesExclusion> list) throws Exception;

	TimesResourcesExclusion save(TimesResourcesExclusion timesResourcesExclusion) throws Exception;
	
	/**
	 * delete time-exclusion data that matches the given
	 * TimeResourcesExclusionId
	 * @param list list of TimeResourcesExclusionId
	 * @throws Exception fail to delete data
	 */
	void delete(List<TimesResourcesExclusionId> list) throws Exception;

	/**
	 * remove time exclusion data that matches the given TimeID and ResourceID
	 * @param timeId Time ID
	 * @param resourceId Resource ID
	 * @throws Exception fail to remove data
	 */
	void removeTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception;
	
	void removeAllTimesResourcesExclusion() throws Exception;
}