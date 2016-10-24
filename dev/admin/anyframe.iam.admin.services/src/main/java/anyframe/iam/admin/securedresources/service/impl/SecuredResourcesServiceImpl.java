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

package anyframe.iam.admin.securedresources.service.impl;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.domain.SecuredResources;
import anyframe.iam.admin.securedresources.dao.SecuredResourcesDao;
import anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import anyframe.iam.admin.vo.ResourceSearchVO;

public class SecuredResourcesServiceImpl extends GenericServiceImpl<SecuredResources, String> implements
		SecuredResourcesService {
	SecuredResourcesDao securedResourcesDao;

	private IIdGenerationService idGenerationServiceResourccesMethod;

	private IIdGenerationService idGenerationServiceResourccesURL;

	private IIdGenerationService idGenerationServiceResourccesPointCut;

	public void setIdGenerationServiceResourccesMethod(IIdGenerationService idGenerationServiceResourccesMethod) {
		this.idGenerationServiceResourccesMethod = idGenerationServiceResourccesMethod;
	}

	public void setIdGenerationServiceResourccesURL(IIdGenerationService idGenerationServiceResourccesURL) {
		this.idGenerationServiceResourccesURL = idGenerationServiceResourccesURL;
	}

	public void setIdGenerationServiceResourccesPointCut(IIdGenerationService idGenerationServiceResourccesPointCut) {
		this.idGenerationServiceResourccesPointCut = idGenerationServiceResourccesPointCut;
	}

	public SecuredResourcesServiceImpl(SecuredResourcesDao securedResourcesDao) {
		super(securedResourcesDao);
		this.securedResourcesDao = securedResourcesDao;
	}

	public Page getList(ResourceSearchVO resourceSearchVO) throws Exception {
		return this.securedResourcesDao.getList(resourceSearchVO);
	}

	public Page getUnmappedList(ResourceSearchVO resourceSearchVO) throws Exception {
		return this.securedResourcesDao.getUnmappedList(resourceSearchVO);
	}

	public Page getMappedList(ResourceSearchVO resourceSearchVO) throws Exception {
		return this.securedResourcesDao.getMappedList(resourceSearchVO);
	}

	public Page getListwithLevel(ResourceSearchVO resourceSearchVO) throws Exception {
		return this.securedResourcesDao.getListwithLevel(resourceSearchVO);
	}

	public void delete(String[] resourceIds) throws Exception {
		if (resourceIds != null) {
			for (int i = 0; i < resourceIds.length; i++) {
				remove(resourceIds[i]);
			}
		}
	}

	public void update(SecuredResources securedResources) throws Exception {
		this.securedResourcesDao.save(securedResources);
	}

	public SecuredResources save(SecuredResources securedResources) throws Exception {
		String resourceId;
		if ("url".equals(securedResources.getResourceType())) {
			resourceId = idGenerationServiceResourccesURL.getNextStringId();
		}
		else if (("method".equals(securedResources.getResourceType()))) {
			resourceId = idGenerationServiceResourccesMethod.getNextStringId();
		}
		else {
			resourceId = idGenerationServiceResourccesPointCut.getNextStringId();
		}
		securedResources.setResourceId(resourceId);
		return this.securedResourcesDao.save(securedResources);
	}
}