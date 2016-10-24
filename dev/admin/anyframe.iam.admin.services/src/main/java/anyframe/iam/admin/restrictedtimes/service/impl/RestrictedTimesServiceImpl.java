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

package anyframe.iam.admin.restrictedtimes.service.impl;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.domain.RestrictedTimes;
import anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesDao;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

public class RestrictedTimesServiceImpl extends GenericServiceImpl<RestrictedTimes, String> implements
		RestrictedTimesService {
	RestrictedTimesDao restrictedTimesDao;

	private IIdGenerationService idGenerationServiceRestrictedTimes;

	public void setIdGenerationServiceRestrictedTimes(IIdGenerationService idGenerationServiceRestrictedTimes) {
		this.idGenerationServiceRestrictedTimes = idGenerationServiceRestrictedTimes;
	}

	public RestrictedTimesServiceImpl(RestrictedTimesDao restrictedTimesDao) {
		super(restrictedTimesDao);
		this.restrictedTimesDao = restrictedTimesDao;
	}

	public Page getList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		return this.restrictedTimesDao.getList(restrictedTimesSearchVO);
	}

	public void delete(String[] timeIds) throws Exception {
		if (timeIds != null) {
			for (int i = 0; i < timeIds.length; i++) {
				remove(timeIds[i]);
			}
		}
	}

	public void update(RestrictedTimes restrictedTimes) throws Exception {
		this.restrictedTimesDao.save(restrictedTimes);
	}

	public RestrictedTimes save(RestrictedTimes restrictedTimes) throws Exception {
		String timeId;
		timeId = idGenerationServiceRestrictedTimes.getNextStringId();

		restrictedTimes.setTimeId(timeId);
		return this.restrictedTimesDao.save(restrictedTimes);
	}

}