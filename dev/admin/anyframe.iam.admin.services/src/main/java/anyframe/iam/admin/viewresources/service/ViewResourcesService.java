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
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

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
	
	/**
	 * find child nodes that extend the given node in one level
	 * @param parentNode parent node
	 * @return List<IamTree> all child node that extend the given node on one level
	 * @throws Exception fail to find node
	 */
	List<IamTree> getViewTree(String parentNode) throws Exception;
	
	/**
	 * find root node in VIEW_HIERARCHY table
	 * @return List<IamTree> root node of View table
	 * @throws Exception
	 */
	List<IamTree> getRootNodeOfViews() throws Exception;
	
	/**
	 * delete the given row from View table
	 * @param viewResourceId view resource id that want to delete
	 * @return void
	 * @throws Exception fail to delete the row
	 */
	void remove(String viewResourceId) throws Exception;
	
	/**
	 * find list of view name that have the given keyword
	 * @param keyword
	 * @return list of view name
	 * @throws Exception fail to find list	 
	 */
	String getViewNameList(String keyword) throws Exception;

	/**
	 * find list of view name that have the given keyword and system name
	 * @param keyword
	 * @param systemName
	 * @return list of view name
	 * @throws Exception fail to find list
	 */
	String getViewNameListWithSystemName(String keyword, String systemName) throws Exception;
	
	/**
	 * find viewResourceId that matches the given view name
	 * @param viewName
	 * @return view resource id
	 * @throws Exception fail to find view resource id
	 */
	String getViewResourceIdByViewName(String viewName) throws Exception;
	
	/**
	 * find all ancestor for the given view resource id
	 * @param viewResourceId
	 * @return list of view resource id
	 * @throws Exception fail to find list
	 */
	List<String> getParentsViewIds(String viewResourceId) throws Exception;
	
	/**
	 * find root node that matches the given system name in VIEW_HIERARCHY table
	 * @param systemName
	 * @return List<IamTree> root node of View table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfViewsWithSystemName(String systemName) throws Exception;
}
