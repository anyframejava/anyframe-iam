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

package anyframe.iam.admin.securedresources.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.domain.SecuredResources;
import anyframe.iam.admin.domain.SecuredResourcesRoles;
import anyframe.iam.admin.domain.SecuredResourcesRolesId;
import anyframe.iam.admin.domain.TempSecuredResources;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import anyframe.iam.admin.securedresources.dao.SecuredResourcesDao;
import anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;
import anyframe.iam.admin.vo.ResourceSearchVO;

public class SecuredResourcesServiceImpl extends GenericServiceImpl<SecuredResources, String> implements
		SecuredResourcesService {
	private SecuredResourcesDao securedResourcesDao;
	private SecuredResourcesRolesService securedResourcesRolesService;
	private RestrictedTimesResourcesService restrictedTimesResourcesService;
	private TimesResourcesExclusionService timesResourcesExclusionService;
	private IIdGenerationService idGenerationServiceResourccesMethod;
	private IIdGenerationService idGenerationServiceResourccesURL;
	private IIdGenerationService idGenerationServiceResourccesPointCut;

	public void setIdGenerationServiceResourccesMethod(IIdGenerationService idGenerationServiceResourccesMethod) {
		this.idGenerationServiceResourccesMethod = idGenerationServiceResourccesMethod;
	}

	public void setIdGenerationServiceResourccesURL(IIdGenerationService idGenerationServiceResourccesURL) {
		this.idGenerationServiceResourccesURL = idGenerationServiceResourccesURL;
	}

	public void setIdGenerationServiceResourccesPointCut(IIdGenerationService idGenerationServiceResourccesPointCut) {
		this.idGenerationServiceResourccesPointCut = idGenerationServiceResourccesPointCut;
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
			resourceId = idGenerationServiceResourccesURL.getNextStringId();
		}
		else if (("method".equals(securedResources.getResourceType()))) {
			resourceId = idGenerationServiceResourccesMethod.getNextStringId();
		}
		else {
			resourceId = idGenerationServiceResourccesPointCut.getNextStringId();
		}
		securedResources.setResourceId(resourceId);
		return this.securedResourcesDao.save(securedResources);
	}
	
	public SecuredResources saveWithoutIdGeneration(SecuredResources securedResources) throws Exception{
		return securedResourcesDao.save(securedResources);
	}
	
	@SuppressWarnings("unchecked")
	public List save(List tempResourceList) throws Exception{
		List resultList = new ArrayList();
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