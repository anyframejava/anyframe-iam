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

package anyframe.iam.admin.viewresources.dao;

import java.util.List;

import anyframe.common.Page;
import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

/**
 * An interface that provides a data management interface to the View_Resources
 * table.
 * @author youngmin.jo
 */
public interface ViewResourcesDao extends IamGenericDao<ViewResource, String> {

	/**
	 * find list of view resources that matches the given search conditions in
	 * View_Resources table
	 * @param viewResourceSearchVO an object that contains search conditions
	 * @return Page list of view resources mapping data
	 * @throws Exception fail to find list
	 */
	Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception;
	
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
	 * find all the child node that extend the given node in VIEWS_HIERARCHY table
	 * @param parentNode parent node
	 * @return List<String> list of the child node that extend the given node
	 * @throws Exception fail to find list
	 */
	List<String> getViewHierarchy(String parentNode) throws Exception;
	
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
	String getViewResourceIdByViewName(String ViewName) throws Exception;
	
	/**
	 * find root node that matches the given system name in VIEW_HIERARCHY table
	 * @param systemName
	 * @return List<IamTree> root node of View table
	 * @throws Exception fail to find root node
	 */
	List<IamTree> getRootNodeOfViewsWithSystemName(String systemName) throws Exception;
}
