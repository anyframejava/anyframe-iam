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

package org.anyframe.iam.admin.restrictedtimes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.RestrictedTimes;
import org.anyframe.iam.admin.domain.RestrictedTimesResources;
import org.anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import org.anyframe.iam.admin.domain.RestrictedTimesRoles;
import org.anyframe.iam.admin.domain.RestrictedTimesRolesId;
import org.anyframe.iam.admin.domain.TempRestrictedTimes;
import org.anyframe.iam.admin.domain.TimesResourcesExclusion;
import org.anyframe.iam.admin.domain.TimesResourcesExclusionId;
import org.anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesDao;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import org.anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.idgen.IdGenService;
import org.anyframe.pagination.Page;

public class RestrictedTimesServiceImpl extends GenericServiceImpl<RestrictedTimes, String> implements
		RestrictedTimesService {
	private RestrictedTimesDao restrictedTimesDao;
	private RestrictedTimesRolesService restrictedTimesRolesService;
	private RestrictedTimesResourcesService restrictedTimesResourcesService;
	private TimesResourcesExclusionService timesResourcesExclusionService;

	private IdGenService idGenServiceRestrictedTimes;

	public void setIdGenServiceRestrictedTimes(IdGenService idGenServiceRestrictedTimes) {
		this.idGenServiceRestrictedTimes = idGenServiceRestrictedTimes;
	}
	
	public void setRestrictedTimesRolesService(
			RestrictedTimesRolesService restrictedTimesRolesService) {
		this.restrictedTimesRolesService = restrictedTimesRolesService;
	}
	
	public void setRestrictedTimesResourcesService(
			RestrictedTimesResourcesService restrictedTimesResourcesService) {
		this.restrictedTimesResourcesService = restrictedTimesResourcesService;
	}

	public void setTimesResourcesExclusionService(
			TimesResourcesExclusionService timesResourcesExclusionService) {
		this.timesResourcesExclusionService = timesResourcesExclusionService;
	}

	public RestrictedTimesServiceImpl(RestrictedTimesDao restrictedTimesDao) {
		super(restrictedTimesDao);
		this.restrictedTimesDao = restrictedTimesDao;
	}

	public Page getList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		return this.restrictedTimesDao.getList(restrictedTimesSearchVO);
	}

	public void delete(String[] timeIds) throws Exception {
		if (timeIds != null) {
			for (int i = 0; i < timeIds.length; i++) {
				remove(timeIds[i]);
			}
		}
	}

	public void update(RestrictedTimes restrictedTimes) throws Exception {
		this.restrictedTimesDao.save(restrictedTimes);
	}

	public RestrictedTimes save(RestrictedTimes restrictedTimes) throws Exception {
		String timeId;
		timeId = idGenServiceRestrictedTimes.getNextStringId();

		restrictedTimes.setTimeId(timeId);
		return this.restrictedTimesDao.save(restrictedTimes);
	}
	
	public RestrictedTimes saveTempRestrictedTimesToRestrictedTimes(TempRestrictedTimes tempRestrictedTimes) throws Exception{
		RestrictedTimes times = new RestrictedTimes();
		
		times.setTimeId(tempRestrictedTimes.getTimeId());
		times.setTimeType(tempRestrictedTimes.getTimeType());
		times.setStartDate(tempRestrictedTimes.getStartDate());
		times.setStartTime(tempRestrictedTimes.getStartTime());
		times.setEndDate(tempRestrictedTimes.getEndDate());
		times.setEndTime(tempRestrictedTimes.getEndTime());
		times.setDescription(tempRestrictedTimes.getDescription());
		times.setSystemName(tempRestrictedTimes.getSystemName());
		
		return restrictedTimesDao.save(times);
	}
	
	
	@SuppressWarnings("unchecked")
	public List save(List tempRestrictedTimesList) throws Exception{
		List resultList = new ArrayList();
		List roleList = new ArrayList();
		List resourceList = new ArrayList();
		List exclusionRoleList = new ArrayList();
		
		for(int i = 0 ; i < tempRestrictedTimesList.size(); i++){
			TempRestrictedTimes tempTimes = (TempRestrictedTimes) tempRestrictedTimesList.get(i);
			RestrictedTimes time = saveTempRestrictedTimesToRestrictedTimes(tempTimes);
			resultList.add(time);
			String timeId = tempTimes.getTimeId();
			
			String roleIds = tempTimes.getRoleId();
			if(!"".equals(roleIds) && roleIds != null){
				String[] roleIdList = roleIds.split(",");
				
				for(int j = 0 ; j < roleIdList.length ; j++){
					Map roleMap = new HashMap();
					roleMap.put("roleId", roleIdList[j]);
					roleMap.put("timeId", timeId);
					
					roleList.add(roleMap);
				}
			}
			
			String resourceId = tempTimes.getResourceId();
			if(!"".equals(resourceId) && resourceId != null){
				
				Map resourceMap = new HashMap();
				resourceMap.put("resourceId", resourceId);
				resourceMap.put("timeId", timeId);
				
				resourceList.add(resourceMap);
			}
			
			String timeExclusionRoleIds = tempTimes.getTimesExclusionRoles();
			if(!"".equals(timeExclusionRoleIds) && timeExclusionRoleIds != null){
				String [] timeExclusionRoleIdList = timeExclusionRoleIds.split(",");
				
				for(int j = 0 ; j < timeExclusionRoleIdList.length ; j++){
					Map exclusionMap = new HashMap();
					exclusionMap.put("exclusionRole", timeExclusionRoleIdList[j]);
					exclusionMap.put("timeId", timeId);
					exclusionMap.put("resourceId", resourceId);
					
					exclusionRoleList.add(exclusionMap);
				}
			}
		}
		
		for(int i = 0 ; i < roleList.size() ; i++){
			Map roleMap = (Map) roleList.get(i);
			String roleId = (String) roleMap.get("roleId");
			String timeId = (String) roleMap.get("timeId");
			
			RestrictedTimesRoles timeRole = new RestrictedTimesRoles();
			RestrictedTimesRolesId id = new RestrictedTimesRolesId();
			
			id.setRoleId(roleId);
			id.setTimeId(timeId);
			
			timeRole.setId(id);
			restrictedTimesRolesService.save(timeRole);
		}
		
		for(int i = 0 ; i < resourceList.size() ; i++){
			Map resourceMap = (Map) resourceList.get(i);
			String resourceId = (String) resourceMap.get("resourceId");
			String timeId = (String) resourceMap.get("timeId");
			
			RestrictedTimesResources timeResource = new RestrictedTimesResources();
			RestrictedTimesResourcesId id = new RestrictedTimesResourcesId();
			
			id.setResourceId(resourceId);
			id.setTimeId(timeId);
			
			timeResource.setId(id);
			restrictedTimesResourcesService.save(timeResource);
		}
		
		for(int i = 0 ; i < exclusionRoleList.size() ; i++){
			Map exclusionMap = (Map) exclusionRoleList.get(i);
			String exclusionRole = (String) exclusionMap.get("exclusionRole");
			String timeId = (String) exclusionMap.get("timeId");
			String resourceId = (String) exclusionMap.get("resourceId");
			
			TimesResourcesExclusion timesExclusion = new TimesResourcesExclusion();
			TimesResourcesExclusionId id = new TimesResourcesExclusionId();
			
			id.setResourceId(resourceId);
			id.setRoleId(exclusionRole);
			id.setTimeId(timeId);
			
			timesExclusion.setId(id);
			timesResourcesExclusionService.save(timesExclusion);
		}
		
		
		return resultList;
	}
	
	public List<TempRestrictedTimes> makeAllTempRestrictedTimesList() throws Exception {
		return this.restrictedTimesDao.makeAllTempRestrictedTimesList();
	}
	
	public void removeAllRestrictedTimes() throws Exception{
		restrictedTimesResourcesService.removeAllRestrictedTimesResources();
		restrictedTimesRolesService.removeAllRestrictedTimesRoles();
		timesResourcesExclusionService.removeAllTimesResourcesExclusion();
		restrictedTimesDao.removeAllRestrictedTimes();
	}

}