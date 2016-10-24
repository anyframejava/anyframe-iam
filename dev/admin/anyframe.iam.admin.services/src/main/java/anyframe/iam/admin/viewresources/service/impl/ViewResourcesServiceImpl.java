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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.dao.ViewResourcesDao;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

public class ViewResourcesServiceImpl extends GenericServiceImpl<ViewResource, String> implements ViewResourcesService {
	ViewResourcesDao viewResourcesDao;
	
	private static List<String> viewIds = new ArrayList<String>();

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
				try{
					exists(viewResourceIds[i]);
					remove(viewResourceIds[i]);
				} catch(Exception e){
					log.debug("ROWs already deleted");
				}
			}
		}
	}
	
	public void remove(String currentNode) throws Exception{
		if(currentNode != null){
			List<String> childNode = viewResourcesDao.getViewHierarchy(currentNode);
			int childNodeCount = childNode.size();
			
			if(childNodeCount > 0){
				for(int i = 0 ; i < childNodeCount ; i++){
					remove(childNode.get(i).toString());
				}
			}
			
			viewResourcesDao.remove(currentNode);
		}
	}

	public ViewResource save(ViewResource viewResource) throws Exception {
		String createDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");
		
		Iterator<ViewHierarchy> it = viewResource.getViewHierarchiesForParentView().iterator();
		
		ViewHierarchy hierarchy = (ViewHierarchy) it.next();
		
		ViewHierarchy viewHierarchy = new ViewHierarchy();
		ViewHierarchyId viewHierarchyId = new ViewHierarchyId();
		
		viewHierarchyId.setParentView(viewResource.getViewResourceId());
		viewHierarchyId.setChildView(hierarchy.getId().getChildView());
		
		viewHierarchy.setId(viewHierarchyId);
		viewHierarchy.setCreateDate(createDate);
		
		Set<ViewHierarchy> parentView = new HashSet<ViewHierarchy>();
		
		parentView.add(viewHierarchy);
		
		
		viewResource.setViewHierarchiesForParentView(parentView);
		
		return this.viewResourcesDao.save(viewResource);
	}
	
	public List<IamTree> getViewTree(String parentNode) throws Exception{
		return viewResourcesDao.getViewTree(parentNode);
	}
	
	public List<IamTree> getRootNodeOfViews() throws Exception{
		return viewResourcesDao.getRootNodeOfViews();
	}
	
	public String getViewNameList(String keyword) throws Exception{
		return viewResourcesDao.getViewNameList(keyword);
	}
	
	public String getViewNameListWithSystemName(String keyword, String systemName) throws Exception{
		return viewResourcesDao.getViewNameListWithSystemName(keyword, systemName);
	}
	
	public String getViewResourceIdByViewName(String viewName) throws Exception{
		return viewResourcesDao.getViewResourceIdByViewName(viewName);
	}
	
	public List<String> getParentsViewIds(String viewResourceId) throws Exception{
		ViewResource viewResource = this.viewResourcesDao.get(viewResourceId);
		
		List<String> viewIds = findRepeatParentView(viewResource);
		
		return viewIds;
	}
	
	private List<String> findRepeatParentView(ViewResource foundViewResource){
		Set<ViewHierarchy> childSet = foundViewResource.getViewHierarchiesForParentView();
		
		for(ViewHierarchy viewHierarchy : childSet){
			ViewResource parentView = viewHierarchy.getViewByChildView();
			viewIds.add(parentView.getViewResourceId());
			findRepeatParentView(parentView);
		}
		
		return viewIds;
	}
	
	public List<IamTree> getRootNodeOfViewsWithSystemName(String systemName) throws Exception{
		return viewResourcesDao.getRootNodeOfViewsWithSystemName(systemName);
	}
}
