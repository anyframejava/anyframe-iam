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
import org.anyframe.iam.admin.domain.RestrictedTimes;
import org.anyframe.iam.admin.domain.TempRestrictedTimes;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the RestrictedTimes
 * table.
 * 
 * @author sungryong.kim
 */
public interface RestrictedTimesDao extends IamGenericDao<RestrictedTimes, String> {

	/**
	 * find list of restricted time resources that matches the given search
	 * condition in Restricted_Times table
	 * @param restrictedTimesSearchVO an object that contains search conditions
	 * @return Page list of restricted time resources
	 * @throws Exception fail to find list
	 */
	Page getList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception;
	
	List<TempRestrictedTimes> makeAllTempRestrictedTimesList() throws Exception;
	
	void removeAllRestrictedTimes() throws Exception;
}