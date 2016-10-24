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

package org.anyframe.iam.admin.viewresources.service;

import java.util.List;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.TempViewResources;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;

/**
 * An interface that provides services about View_Resources_Mapping table
 * @author youngmin.jo
 * 
 */
public interface ViewResourcesService extends GenericService<ViewResource, String> {

	/**
	 * find list of view resources that matches the given search conditions in
	 * View_Resources table
	 * @param viewResourceSearchVO an object that contains search conditions
	 * @return Page list of view resources data
	 * @throws Exception fail to find list
	 */
	Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception;

	/**
	 * delete view resources that match the given viewResource IDs
	 * @param viewresourceIds list of view resource IDs
	 * @throws Exception fail to delete data
	 */
	void delete(String[] viewResourceIds) throws Exception;

	/**
	 * update view resource that matches the given viewResouece ID
	 * @param viewResource ViewResource domain object that want to be updated
	 * @throws Exception
	 */
	void update(ViewResource viewResource) throws Exception;

	/**
	 * save rows in view resource table
	 * @param viewResource a row that want to save
	 * @return ViewResource return ViewResource domain object
	 * @throws Exception fail to delete rows
	 */
	ViewResource save(ViewResource viewResource) throws Exception;
	
	List<IamTree> getViewTree(String parentNode) throws Exception;
	
	List<IamTree> getRootNodeOfViews() throws Exception;
	
	void remove(String viewResourceId) throws Exception;
	
	String getViewNameList(String keyword) throws Exception;

	String getViewNameListWithSystemName(String keyword, String systemName) throws Exception;
	
	String getViewResourceIdByViewName(String viewName) throws Exception;
	
	List<String> getParentsViewIds(String viewResourceId) throws Exception;
	
	List<IamTree> getRootNodeOfViewsWithSystemName(String systemName) throws Exception;
	
	List<TempViewResources> makeAllTempViewList() throws Exception;
	
	void removeAllViewResources() throws Exception;
	
	@SuppressWarnings("unchecked")
	List save(List tempViewResource) throws Exception;
	
	boolean isExistName(String viewName) throws Exception;
}
