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

package anyframe.iam.admin.viewresources.service.impl;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.dao.ViewResourcesDao;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

public class ViewResourcesServiceImpl extends GenericServiceImpl<ViewResource, String> implements ViewResourcesService {
	ViewResourcesDao viewResourcesDao;

	public ViewResourcesServiceImpl(ViewResourcesDao viewResourcesDao) {
		super(viewResourcesDao);
		this.viewResourcesDao = viewResourcesDao;
	}

	public Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception {
		return viewResourcesDao.getList(viewResourceSearchVO);
	}

	public void update(ViewResource viewResource) throws Exception {
		this.viewResourcesDao.save(viewResource);
	}

	public void delete(String[] viewResourceIds) throws Exception {
		if (viewResourceIds != null) {
			for (int i = 0; i < viewResourceIds.length; i++) {
				remove(viewResourceIds[i]);
			}
		}
	}

	public ViewResource save(ViewResource viewResource) throws Exception {
		return this.viewResourcesDao.save(viewResource);
	}
}
