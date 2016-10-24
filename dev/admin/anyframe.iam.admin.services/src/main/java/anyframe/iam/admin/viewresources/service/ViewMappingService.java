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

package anyframe.iam.admin.viewresources.service;

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.ViewResourcesMapping;
import anyframe.iam.admin.domain.ViewResourcesMappingPK;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

/**
 * An interface that provides services about View_Resources_Mapping table
 * @author jongpil.park
 * 
 */
public interface ViewMappingService extends GenericService<ViewResourcesMapping, ViewResourcesMappingPK> {

	/**
	 * find list of view resources mapping data that matches the given search
	 * conditions in View_Resources_Mapping table
	 * @param viewResourceSearchVO an object that contains search conditions
	 * @return Page list of view resources mapping data
	 * @throws Exception fail to find list
	 */
	Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception;

	/**
	 * delete view resources mapping data that match the given
	 * ViewResourcesMappingPK
	 * @param list list of ViewResourcesMappingPK
	 * @throws Exception fail to delete data
	 */
	void delete(List<ViewResourcesMappingPK> list) throws Exception;

	/**
	 * find View Mapping data that matches the given viewResource ID
	 * @param viewResourceId View Resource ID
	 * @return List List of view Mapping data that matches the given
	 * viewResource ID
	 * @throws Exception fail to find list
	 */
	@SuppressWarnings("unchecked")
	List get(String viewResourceId) throws Exception;

	/**
	 * delete View Mapping data that matches the given viewResource ID
	 * @param viewResourceId View Resource ID
	 * @return ViewResourceMapping return an Object if deleting finish
	 * successfully
	 * @throws Exception fail to delete data
	 */
	ViewResourcesMapping save(ViewResourcesMapping[] viewResourcesMapping) throws Exception;
	
	ViewResourcesMapping save(ViewResourcesMapping viewResourcesMapping) throws Exception;
	
	void removeAllViewResourceMapping() throws Exception;
}
