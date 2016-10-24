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

package org.anyframe.iam.admin.viewresources.service.impl;

import java.util.List;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.viewresources.dao.ViewMappingDao;
import org.anyframe.iam.admin.viewresources.service.ViewMappingService;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;

public class ViewMappingServiceImpl extends GenericServiceImpl<ViewResourcesMapping, ViewResourcesMappingPK> implements
		ViewMappingService {
	ViewMappingDao viewMappingDao;

	public ViewMappingServiceImpl(ViewMappingDao viewMappingDao) {
		super(viewMappingDao);
		this.viewMappingDao = viewMappingDao;
	}
	
	public Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception {
		return viewMappingDao.getList(viewResourceSearchVO);
	}

	public void delete(List<ViewResourcesMappingPK> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			ViewResourcesMappingPK viewResourcesMappingPK = (ViewResourcesMappingPK) list.get(i);
			super.remove(viewResourcesMappingPK);
		}
	}

	public List<ViewResourcesMapping> get(String viewResourceId) throws Exception {
		return viewMappingDao.get(viewResourceId);
	}
	
	public ViewResourcesMapping save(ViewResourcesMapping viewResourcesMapping) throws Exception{
		return viewMappingDao.save(viewResourcesMapping);
	}

	public ViewResourcesMapping save(ViewResourcesMapping[] viewResourcesMapping) throws Exception {
		if (viewResourcesMapping != null) {
			viewMappingDao.delete(viewResourcesMapping[0].getId().getViewResourceId());

			for (int i = 0; i < viewResourcesMapping.length; i++) {
				viewMappingDao.save(viewResourcesMapping[i]);
			}
		}

		return new ViewResourcesMapping();
	}
	
	public void removeAllViewResourceMapping() throws Exception{
		viewMappingDao.removeAllViewResourceMapping();
	}
}
