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

package anyframe.iam.admin.restrictedtimes.service;

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.RestrictedTimes;
import anyframe.iam.admin.domain.TempRestrictedTimes;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

/**
 * An interface that provides services about Restricted_Times table
 * @author sungryong.kim
 * 
 */
public interface RestrictedTimesService extends GenericService<RestrictedTimes, String> {

	/**
	 * find list of restricted time that matches the given search condition in
	 * Restricted_Times table
	 * @param restrictedTimesSearchVO an object that contains search conditions
	 * @return Page list of restricted time that matches the given search
	 * condition in Restricted_Times table
	 * @throws Exception fail to find list
	 */
	Page getList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception;

	/**
	 * delete restricted time that matches the given time IDs
	 * @param timeIds array of time IDs
	 * @throws Exception fail to delete data
	 */
	void delete(String[] timeIds) throws Exception;

	/**
	 * update the given row from Restricted_Times table
	 * @param restrictedTimes a row that want to update
	 * @throws Exception fail to update the row
	 */
	void update(RestrictedTimes restrictedTimes) throws Exception;

	/**
	 * save that the given row in Restricted_Times table
	 * @param restrictedTimes a row that want to save
	 * @return RestrictedTimes return an Object if saving finish successfully
	 * @throws Exception fail to save the row
	 */
	RestrictedTimes save(RestrictedTimes restrictedTimes) throws Exception;
	
	RestrictedTimes saveTempRestrictedTimesToRestrictedTimes(TempRestrictedTimes tempRestrictedTimes) throws Exception;
	
	@SuppressWarnings("unchecked")
	List save(List tempRestrictedTimes) throws Exception;
	
	List<TempRestrictedTimes> makeAllTempRestrictedTimesList() throws Exception;
	
	void removeAllRestrictedTimes() throws Exception;
}