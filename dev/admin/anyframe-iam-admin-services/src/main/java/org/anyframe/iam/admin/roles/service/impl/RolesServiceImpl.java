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

package org.anyframe.iam.admin.roles.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.authorities.service.AuthoritiesService;
import org.anyframe.iam.admin.common.IAMException;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;
import org.anyframe.iam.admin.domain.TempRoles;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import org.anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import org.anyframe.iam.admin.roles.dao.RolesDao;
import org.anyframe.iam.admin.roles.service.RolesService;
import org.anyframe.iam.admin.roleshierarchy.service.RolesHierarchyService;
import org.anyframe.iam.admin.securedresourcesroles.dao.SecuredResourcesRolesDao;
import org.anyframe.util.DateUtil;

public class RolesServiceImpl extends GenericServiceImpl<Roles, String> implements RolesService {
	private RolesDao rolesDao;
	private SecuredResourcesRolesDao securedResourcesRolesDao;
	private RolesHierarchyService rolesHierarchyService;
	private AuthoritiesService authoritiesService;
	private RestrictedTimesRolesService restrictedTimesRolesService;
	private TimesResourcesExclusionService timesResourcesExclusionService;

	private static List<String> roleIds = new ArrayList<String>();

	public RolesServiceImpl(RolesDao rolesDao) {
		super(rolesDao);
		this.rolesDao = rolesDao;
	}

	public void setSecuredResourcesRolesDao(SecuredResourcesRolesDao securedResourcesRolesDao) {
		this.securedResourcesRolesDao = securedResourcesRolesDao;
	}

	public void setRolesHierarchyService(RolesHierarchyService rolesHierarchyService) {
		this.rolesHierarchyService = rolesHierarchyService;
	}

	public void setAuthoritiesService(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
	}

	public void setRestrictedTimesRolesService(
			RestrictedTimesRolesService restrictedTimesRolesService) {
		this.restrictedTimesRolesService = restrictedTimesRolesService;
	}

	public void setTimesResourcesExclusionService(
			TimesResourcesExclusionService timesResourcesExclusionService) {
		this.timesResourcesExclusionService = timesResourcesExclusionService;
	}

	public List<IamTree> getRoleTree(String parentNode) throws Exception {
		return rolesDao.getRoleTree(parentNode);
	}

	public List<IamTree> getRootNodeOfRoles() throws Exception {
		return rolesDao.getRootNodeOfRoles();
	}

	public List<Roles> findRoles(String userId) throws Exception {
		return rolesDao.findRoles(userId);
	}

	public void update(Roles roles) throws Exception {
		roles.setModifyDate(DateUtil.getCurrentTime("yyyyMMdd"));
		
		rolesDao.update(roles);
	}
	
	public Roles save(Roles roles) throws Exception {
		String createDate = DateUtil.getCurrentTime("yyyyMMdd");
		
		Iterator<RolesHierarchy> it = roles.getRolesHierarchiesForParentRole().iterator();
		
		RolesHierarchy hierarchy = (RolesHierarchy)it.next();
		
		RolesHierarchy rolesHierarchy = new RolesHierarchy();
		RolesHierarchyId rolesHierarchyId = new RolesHierarchyId();
		
		rolesHierarchyId.setParentRole(roles.getRoleId());
		rolesHierarchyId.setChildRole(hierarchy.getId().getChildRole());
		
		rolesHierarchy.setId(rolesHierarchyId);
		rolesHierarchy.setCreateDate(createDate);
		
		Set<RolesHierarchy> parentRole = new HashSet<RolesHierarchy>();
		
		parentRole.add(rolesHierarchy);
		
		roles.setDescription(roles.getRoleName() + " description");
		roles.setCreateDate(createDate);
		roles.setRolesHierarchiesForParentRole(parentRole);
		
		return rolesDao.save(roles);
	}
	
	public Roles saveWithoutHierarchy(Roles roles) throws Exception{
		return rolesDao.save(roles);
	}
	
	@SuppressWarnings("unchecked")
	public List save(List tempRoleList) throws Exception{
		List resultList = new ArrayList();
		
		for(int i = 0 ; i < tempRoleList.size() ; i++){
			TempRoles tempRoles = (TempRoles) tempRoleList.get(i);
			Roles roles = saveTempRolesToRoles(tempRoles);
			resultList.add(roles);
		}
		
		for(int i = 0 ; i < tempRoleList.size(); i++){
			TempRoles tempRole = (TempRoles) tempRoleList.get(i);
			String parentRole = tempRole.getParentRoles();
			
			if(!"".equals(parentRole) && parentRole != null){
				RolesHierarchy rolesHierarchy = new RolesHierarchy();
				RolesHierarchyId id = new RolesHierarchyId();
				
				id.setChildRole(parentRole);
				id.setParentRole(tempRole.getRoleId());
				
				rolesHierarchy.setId(id);
				rolesHierarchy.setCreateDate(tempRole.getCreateDate());
				
				rolesHierarchyService.save(rolesHierarchy);
			}
		}
		
		return resultList;
	}
	
	public Roles saveTempRolesToRoles(TempRoles tempRoles) throws Exception{
		Roles roles = new Roles();
		
		roles.setRoleId(tempRoles.getRoleId());
		roles.setRoleName(tempRoles.getRoleName());
		roles.setDescription(tempRoles.getDescription());
		roles.setCreateDate(tempRoles.getCreateDate());
		roles.setModifyDate(tempRoles.getModifyDate());
		
		return saveWithoutHierarchy(roles);
	}

	public void remove(String currentNode) throws Exception {
		if (currentNode != null) {
			List<String> childNode = rolesDao.getRoleHierarchy(currentNode);
			int childNodeCount = childNode.size();

			if (childNodeCount > 0) {
				for (int i = 0; i < childNodeCount; i++) {
					remove(childNode.get(i).toString());
				}
			}

			securedResourcesRolesDao.remove(currentNode);

			rolesDao.remove(currentNode);
		}
		else {
			throw new IAMException("No selected node");
		}
	}
	
	public void removeAllRoles() throws Exception{
		
		securedResourcesRolesDao.removeAllSecuredResourcesRoles();
		rolesHierarchyService.removeAllRolesHierarchy();
		authoritiesService.removeAllAuthorities();
		restrictedTimesRolesService.removeAllRestrictedTimesRoles();
		timesResourcesExclusionService.removeAllTimesResourcesExclusion();
		
		rolesDao.removeAllRoles();
	}

	public List<Roles> getList() throws Exception {
		return rolesDao.getList();
	}
	
	public String getRoleNameList(String keyword) throws Exception {
		return rolesDao.getRoleNameList(keyword);
	}
		
	public String getRoleIdByRoleName(String roleName) throws Exception {
		return rolesDao.getRoleIdByRoleName(roleName);
	}
	
	public List<String> getParentsRoleIds(String roleId) throws Exception {
		Roles roles = this.rolesDao.get(roleId);
		
		List<String> rolesIds = findRepeatParentRole(roles);

		return rolesIds;
	}
	
	private List<String> findRepeatParentRole(Roles foundRole) {
		Set<RolesHierarchy> childSet = foundRole.getRolesHierarchiesForParentRole();
		
		for(RolesHierarchy rolesHierarchy : childSet) {
			Roles parentRole = rolesHierarchy.getRolesByChildRole();
			roleIds.add(parentRole.getRoleId());
			findRepeatParentRole(parentRole);
		}

		return roleIds;
	}	
	
	public List<TempRoles> makeAllTempRolesList() throws Exception{
		return this.rolesDao.makeAllTempRolesList();
	}
}