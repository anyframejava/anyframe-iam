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

package org.anyframe.iam.admin.securedresources.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.domain.SecuredResourcesRoles;
import org.anyframe.iam.admin.domain.SecuredResourcesRolesId;
import org.anyframe.iam.admin.domain.TempSecuredResources;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import org.anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import org.anyframe.iam.admin.securedresources.dao.SecuredResourcesDao;
import org.anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import org.anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.idgen.IdGenService;
import org.anyframe.pagination.Page;

public class SecuredResourcesServiceImpl extends GenericServiceImpl<SecuredResources, String> implements
		SecuredResourcesService {
	private SecuredResourcesDao securedResourcesDao;
	private SecuredResourcesRolesService securedResourcesRolesService;
	private RestrictedTimesResourcesService restrictedTimesResourcesService;
	private TimesResourcesExclusionService timesResourcesExclusionService;
	private IdGenService idGenServiceResourccesMethod;
	private IdGenService idGenServiceResourccesURL;
	private IdGenService idGenServiceResourccesPointCut;

	public void setIdGenServiceResourccesMethod(IdGenService idGenServiceResourccesMethod) {
		this.idGenServiceResourccesMethod = idGenServiceResourccesMethod;
	}

	public void setIdGenServiceResourccesURL(IdGenService idGenServiceResourccesURL) {
		this.idGenServiceResourccesURL = idGenServiceResourccesURL;
	}

	public void setIdGenServiceResourccesPointCut(IdGenService idGenServiceResourccesPointCut) {
		this.idGenServiceResourccesPointCut = idGenServiceResourccesPointCut;
	}

	public void setRestrictedTimesResourcesService(
			RestrictedTimesResourcesService restrictedTimesResourcesService) {
		this.restrictedTimesResourcesService = restrictedTimesResourcesService;
	}

	public void setSecuredResourcesRolesService(
			SecuredResourcesRolesService securedResourcesRolesService) {
		this.securedResourcesRolesService = securedResourcesRolesService;
	}
	
	public void setTimesResourcesExclusionService(
			TimesResourcesExclusionService timesResourcesExclusionService) {
		this.timesResourcesExclusionService = timesResourcesExclusionService;
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
	
	public Page getUnmappedResourceList(ResourceSearchVO searchVO) throws Exception{
		return this.securedResourcesDao.getUnmappedResourceList(searchVO);
	}
	
	public Page getUnmappedAnyRoleList(ResourceSearchVO searchVO) throws Exception{
		return this.securedResourcesDao.getUnmappedAnyRoleList(searchVO);
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
			resourceId = idGenServiceResourccesURL.getNextStringId();
		}
		else if (("method".equals(securedResources.getResourceType()))) {
			resourceId = idGenServiceResourccesMethod.getNextStringId();
		}
		else {
			resourceId = idGenServiceResourccesPointCut.getNextStringId();
		}
		securedResources.setResourceId(resourceId);
		return this.securedResourcesDao.save(securedResources);
	}
	
	public SecuredResources saveWithoutIdGeneration(SecuredResources securedResources) throws Exception{
		return securedResourcesDao.save(securedResources);
	}
	
	@SuppressWarnings("unchecked")
	public List<SecuredResources> save(List<TempSecuredResources> tempResourceList) throws Exception{
		List<SecuredResources> resultList = new ArrayList<SecuredResources>();
		List roleList = new ArrayList();
		
		for(int i = 0 ; i < tempResourceList.size() ; i++){
			TempSecuredResources tempResource = (TempSecuredResources) tempResourceList.get(i);
			SecuredResources resource = saveTempSecuredResourcesToResources(tempResource);
			resultList.add(resource);
			
			String roleId = tempResource.getRoleId();
			
			if(!"".equals(roleId) && roleId != null){
				String[] roleIds = roleId.split(",");
				
				for(int j = 0 ; j < roleIds.length ; j++){
					Map roleMap = new HashMap();
					roleMap.put("roleId", roleIds[j]);
					roleMap.put("resourceId", tempResource.getResourceId());
					roleMap.put("createDate", tempResource.getCreateDate());
					
					roleList.add(roleMap);
				}
			}
		}
		
		for(int i = 0 ; i < roleList.size(); i++){
			Map roleMap = (Map) roleList.get(i);
			String roleId = (String) roleMap.get("roleId");
			String resourceId = (String) roleMap.get("resourceId");
			String createDate = (String) roleMap.get("createDate");
			
			SecuredResourcesRoles securedResourcesRole = new SecuredResourcesRoles();
			SecuredResourcesRolesId id = new SecuredResourcesRolesId();
			
			id.setResourceId(resourceId);
			id.setRoleId(roleId);
			
			securedResourcesRole.setId(id);
			securedResourcesRole.setCreateDate(createDate);
			
			securedResourcesRolesService.save(securedResourcesRole);
		}
		
		return resultList;
	}
	
	public List<TempSecuredResources> makeAllTempResourcesList() throws Exception {
		return this.securedResourcesDao.makeAllTempResourcesList();
	}
	
	public void removeAllSecuredResources() throws Exception{
		securedResourcesRolesService.removeAllSecuredResourcesRoles();
		restrictedTimesResourcesService.removeAllRestrictedTimesResources();
		timesResourcesExclusionService.removeAllTimesResourcesExclusion();
		securedResourcesDao.removeAllSecuredResources();
	}
	
	public SecuredResources saveTempSecuredResourcesToResources(TempSecuredResources tempResources) throws Exception{
		SecuredResources securedResources = new SecuredResources();
		
		securedResources.setResourceId(tempResources.getResourceId());
		securedResources.setResourceName(tempResources.getResourceName());
		securedResources.setResourcePattern(tempResources.getResourcePattern());
		securedResources.setResourceType(tempResources.getResourceType());
		securedResources.setCreateDate(tempResources.getCreateDate());
		securedResources.setModifyDate(tempResources.getModifyDate());
		securedResources.setDescription(tempResources.getDescription());
		securedResources.setSortOrder(Long.parseLong(tempResources.getSortOrder()));
		securedResources.setSystemName(tempResources.getSystemName());
		
		return saveWithoutIdGeneration(securedResources);
	}
}