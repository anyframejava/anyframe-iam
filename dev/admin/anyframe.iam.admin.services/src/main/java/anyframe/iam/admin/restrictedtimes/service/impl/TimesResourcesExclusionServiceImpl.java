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

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.TimesResourcesExclusion;
import anyframe.iam.admin.domain.TimesResourcesExclusionId;
import anyframe.iam.admin.restrictedtimes.dao.TimesResourcesExclusionDao;
import anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

public class TimesResourcesExclusionServiceImpl extends
		GenericServiceImpl<TimesResourcesExclusion, TimesResourcesExclusionId> implements
		TimesResourcesExclusionService {
	TimesResourcesExclusionDao timesResourcesExclusionDao;

	public TimesResourcesExclusionServiceImpl(TimesResourcesExclusionDao timesResourcesExclusionDao) {
		super(timesResourcesExclusionDao);
		this.timesResourcesExclusionDao = timesResourcesExclusionDao;
	}

	public Page getTimeExclusionList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		return this.timesResourcesExclusionDao.getTimeExclusionList(restrictedTimesSearchVO);
	}

	@SuppressWarnings("unchecked")
	public List findRoleListByTimeResource(String timeId, String resourceId) throws Exception {
		return timesResourcesExclusionDao.findRoleListByTimeResource(timeId, resourceId);
	}

	public void saveTimeExclusion(List<TimesResourcesExclusion> list) throws Exception {
		String timeId = ((TimesResourcesExclusion) list.get(0)).getId().getTimeId();
		String resourceId = ((TimesResourcesExclusion) list.get(0)).getId().getResourceId();

		removeTimesExclusionByTimeResource(timeId, resourceId);

		for (int i = 0; i < list.size(); i++) {
			TimesResourcesExclusion timesResourcesExclusion = (TimesResourcesExclusion) list.get(i);
			this.timesResourcesExclusionDao.save(timesResourcesExclusion);
		}
	}

	public void delete(List<TimesResourcesExclusionId> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			TimesResourcesExclusionId timesResourcesExclusionId = (TimesResourcesExclusionId) list.get(i);
			super.remove(timesResourcesExclusionId);
		}
	}

	public void removeTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception {
		timesResourcesExclusionDao.removeTimesExclusionByTimeResource(timeId, resourceId);
	}
}