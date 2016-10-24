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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.TempViewResources;
import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.viewhierarchy.service.ViewHierarchyService;
import org.anyframe.iam.admin.viewresources.dao.ViewResourcesDao;
import org.anyframe.iam.admin.viewresources.service.ViewMappingService;
import org.anyframe.iam.admin.viewresources.service.ViewResourcesService;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.iam.core.acl.ExtBasePermission;
import org.anyframe.pagination.Page;
import org.anyframe.util.DateUtil;

public class ViewResourcesServiceImpl extends GenericServiceImpl<ViewResource, String> implements ViewResourcesService {
	private ViewResourcesDao viewResourcesDao;
	private ViewHierarchyService viewHierarchyService;
	private ViewMappingService viewMappingService;

	private static List<String> viewIds = new ArrayList<String>();

	public ViewResourcesServiceImpl(ViewResourcesDao viewResourcesDao) {
		super(viewResourcesDao);
		this.viewResourcesDao = viewResourcesDao;
	}
	
	public void setViewHierarchyService(ViewHierarchyService viewHierarchyService) {
		this.viewHierarchyService = viewHierarchyService;
	}

	public void setViewMappingService(ViewMappingService viewMappingService) {
		this.viewMappingService = viewMappingService;
	}

	public Page getUnmappedList(ViewResourceSearchVO viewResourceSearchVO) throws Exception{
		return viewResourcesDao.getUnmappedList(viewResourceSearchVO);
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
		String createDate = DateUtil.getCurrentTime("yyyyMMdd");
		
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
	
	@SuppressWarnings("unchecked")
	public List save(List tempViewList) throws Exception{
		List resultList = new ArrayList();
		List viewList = new ArrayList();
		List permissionList = new ArrayList();
		
		for(int i = 0 ; i < tempViewList.size() ; i++ ){
			TempViewResources tempView = (TempViewResources) tempViewList.get(i);
			ViewResource view = saveTempViewResourcesToViewResources(tempView);
			resultList.add(view);
			
			String parentView = tempView.getParentView();
			
			if(!"".equals(parentView) && parentView != null){
				Map viewMap = new HashMap();
				viewMap.put("parentView", parentView);
				viewMap.put("viewResourceId", tempView.getViewResourceId());
				
				viewList.add(viewMap);
			}
			
			String permissionName = tempView.getPermissions();
			
			if(!"".equals(permissionName) && permissionName != null){
				Map permissionMap = new HashMap();
				permissionMap.put("permission", permissionName);
				permissionMap.put("viewResourceId", tempView.getViewResourceId());
				permissionMap.put("refId", tempView.getRefId());
				permissionMap.put("refType", tempView.getRefType());
				
				permissionList.add(permissionMap);
			}
		}
		
		for(int i = 0 ; i < viewList.size() ; i++){
			Map viewMap = (Map) viewList.get(i);
			String parentView = (String) viewMap.get("parentView");
			String viewResourceId = (String) viewMap.get("viewResourceId");
			
			ViewHierarchy viewHierarchy = new ViewHierarchy();
			ViewHierarchyId id = new ViewHierarchyId();
			
			id.setParentView(viewResourceId);
			id.setChildView(parentView);
			
			viewHierarchy.setId(id);
			
			viewHierarchyService.save(viewHierarchy);
		}
		
		for(int i = 0 ; i < permissionList.size() ; i++){
			Map permissionMap = (Map) permissionList.get(i);
			String permission = (String) permissionMap.get("permission");
			String viewResourceId = (String) permissionMap.get("viewResourceId");
			String refId = (String) permissionMap.get("refId");
			String refType = (String) permissionMap.get("refType");
			int mask = ExtBasePermission.getPermissionMask(permission);
			
			ViewResourcesMapping viewResourceMapping = new ViewResourcesMapping();
			ViewResourcesMappingPK id = new ViewResourcesMappingPK();
			id.setRefId(refId);
			id.setViewResourceId(viewResourceId);
			
			viewResourceMapping.setId(id);
			viewResourceMapping.setPermissions(permission);
			viewResourceMapping.setRefType(refType);
			viewResourceMapping.setMask(mask);
			
			viewMappingService.save(viewResourceMapping);
		}
	
		return resultList;
	}
	
	public ViewResource saveTempViewResourcesToViewResources(TempViewResources tempView) throws Exception{
		ViewResource view = new ViewResource();
		
		view.setViewResourceId(tempView.getViewResourceId());
		view.setViewInfo(tempView.getViewInfo());
		view.setViewName(tempView.getViewName());
		view.setViewType(tempView.getViewType());
		view.setVisible(tempView.getVisible());
		view.setDescription(tempView.getDescription());
		view.setCategory(tempView.getCategory());
		view.setSystemName(tempView.getSystemName());
		
		return saveWithoutHierarchy(view);
	}
	
	public ViewResource saveWithoutHierarchy(ViewResource viewResource) throws Exception{
		return viewResourcesDao.save(viewResource);
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
	
	public List<TempViewResources> makeAllTempViewList() throws Exception{
		return viewResourcesDao.makeAllTempViewList();
	}
	
	public void removeAllViewResources() throws Exception{
		viewMappingService.removeAllViewResourceMapping();
		viewHierarchyService.removeAllViewHierarchy();
		viewResourcesDao.removeAllViewResources();
	}
	
	public boolean isExistName(String viewName) throws Exception{
		return viewResourcesDao.isExistName(viewName);
	}
}
