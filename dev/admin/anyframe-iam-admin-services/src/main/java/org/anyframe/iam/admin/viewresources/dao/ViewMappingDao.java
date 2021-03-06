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

package org.anyframe.iam.admin.viewresources.dao;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides a data management interface to the
 * View_Resources_Mapping table.
 * @author jongpil.park
 */
public interface ViewMappingDao extends IamGenericDao<ViewResourcesMapping, ViewResourcesMappingPK> {

	/**
	 * find list of view resources mapping data that matches the given search
	 * conditions in View_Resources_Mapping table
	 * @param viewResourceSearchVO an object that contains search conditions
	 * @return Page list of view resources mapping data
	 * @throws Exception fail to find list
	 */
	Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception;

	/**
	 * find View Mapping data that matches the given viewResource ID
	 * @param viewResourceId View Resource ID
	 * @return List List of view Mapping data that matches the given
	 * viewResource ID
	 * @throws Exception fail to find list
	 */
	List<ViewResourcesMapping> get(String viewResourceId) throws Exception;

	/**
	 * delete View Mapping data that matches the given viewResource ID
	 * @param viewResourceId View Resource ID
	 * @return ViewResourceMapping return an Object if deleting finish
	 * successfully
	 * @throws Exception fail to delete data
	 */
	ViewResourcesMapping delete(String viewResourceId) throws Exception;
	
	void removeAllViewResourceMapping() throws Exception;
}
