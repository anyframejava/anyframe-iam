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
import anyframe.iam.admin.domain.RestrictedTimesResources;
import anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesResourcesDao;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

public class RestrictedTimesResourcesServiceImpl extends
		GenericServiceImpl<RestrictedTimesResources, RestrictedTimesResourcesId> implements
		RestrictedTimesResourcesService {
	RestrictedTimesResourcesDao restrictedTimesResourcesDao;

	public RestrictedTimesResourcesServiceImpl(RestrictedTimesResourcesDao restrictedTimesResourcesDao) {
		super(restrictedTimesResourcesDao);
		this.restrictedTimesResourcesDao = restrictedTimesResourcesDao;
	}

	public Page getTimeResourceList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		return this.restrictedTimesResourcesDao.getTimeResourceList(restrictedTimesSearchVO);
	}

	public List findRoleListByTime(String timeId) throws Exception {
		return restrictedTimesResourcesDao.findRoleListByTime(timeId);
	}

	public Page findResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception {
		return this.restrictedTimesResourcesDao.findResourceListByTime(searchVO);
	}

	public Page findUnmappedResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception {
		return this.restrictedTimesResourcesDao.findUnmappedResourceListByTime(searchVO);
	}

	public void addTimeResources(List<RestrictedTimesResources> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			RestrictedTimesResources restrictedTimesResources = (RestrictedTimesResources) list.get(i);
			this.restrictedTimesResourcesDao.save(restrictedTimesResources);
		}
	}

	public void delete(List<RestrictedTimesResourcesId> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			RestrictedTimesResourcesId restrictedTimesResourcesId = (RestrictedTimesResourcesId) list.get(i);
			super.remove(restrictedTimesResourcesId);
		}
	}
}