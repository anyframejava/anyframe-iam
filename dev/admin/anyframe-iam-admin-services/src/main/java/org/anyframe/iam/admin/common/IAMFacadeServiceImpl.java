package org.anyframe.iam.admin.common;

import java.util.List;

import org.anyframe.iam.admin.authorities.service.AuthoritiesService;
import org.anyframe.iam.admin.dataupload.service.DataUploadService;
import org.anyframe.iam.admin.domain.Authorities;
import org.anyframe.iam.admin.domain.DataUpload;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.GroupsUsers;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.RestrictedTimes;
import org.anyframe.iam.admin.domain.RestrictedTimesResources;
import org.anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import org.anyframe.iam.admin.domain.RestrictedTimesRoles;
import org.anyframe.iam.admin.domain.RestrictedTimesRolesId;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.domain.TempSecuredResources;
import org.anyframe.iam.admin.domain.TimesResourcesExclusion;
import org.anyframe.iam.admin.domain.TimesResourcesExclusionId;
import org.anyframe.iam.admin.domain.Users;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.groups.service.GroupsService;
import org.anyframe.iam.admin.groupshierarchy.service.GroupsHierarchyService;
import org.anyframe.iam.admin.groupsusers.service.GroupsUsersService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import org.anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import org.anyframe.iam.admin.roles.service.RolesService;
import org.anyframe.iam.admin.roleshierarchy.service.RolesHierarchyService;
import org.anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import org.anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;
import org.anyframe.iam.admin.users.service.UsersService;
import org.anyframe.iam.admin.viewhierarchy.service.ViewHierarchyService;
import org.anyframe.iam.admin.viewresources.service.ViewMappingService;
import org.anyframe.iam.admin.viewresources.service.ViewResourcesService;
import org.anyframe.iam.admin.vo.AuthoritySearchVO;
import org.anyframe.iam.admin.vo.CommonSearchVO;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.iam.admin.vo.UserSearchVO;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;

public class IAMFacadeServiceImpl implements IAMFacadeService {

	private AuthoritiesService authoritiesService;

	private DataUploadService dataUploadService;
	
	private GroupsService groupsService;
	
	private GroupsHierarchyService groupsHierarchyService;
	
	private GroupsUsersService groupsUsersService;
	
	private RestrictedTimesService restrictedTimesService;
	
	private RestrictedTimesResourcesService restrictedTimesResourcesService;
	
	private RestrictedTimesRolesService restrictedTimesRolesService;
	
	private TimesResourcesExclusionService timesResourcesExclusionService;
	
	private RolesService rolesService;
	
	private RolesHierarchyService rolesHierarchyService;
	
	private SecuredResourcesService securedResourcesService;
	
	private SecuredResourcesRolesService securedResourcesRolesService;
	
	private UsersService usersService;
	
	private ViewHierarchyService viewHierarchyService;
	
	private ViewMappingService viewMappingService;
	
	private ViewResourcesService viewResourcesService;
	
	public void setAuthoritiesService(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
	}

	public void setDataUploadService(DataUploadService dataUploadService) {
		this.dataUploadService = dataUploadService;
	}

	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	public void setGroupsHierarchyService(
			GroupsHierarchyService groupsHierarchyService) {
		this.groupsHierarchyService = groupsHierarchyService;
	}

	public void setGroupsUsersService(GroupsUsersService groupsUsersService) {
		this.groupsUsersService = groupsUsersService;
	}

	public void setRestricredTimesService(
			RestrictedTimesService restricredTimesService) {
		this.restrictedTimesService = restricredTimesService;
	}

	public void setRestrictedTimesResourcesService(
			RestrictedTimesResourcesService restrictedTimesResourcesService) {
		this.restrictedTimesResourcesService = restrictedTimesResourcesService;
	}

	public void setRestricredTimesRolesService(
			RestrictedTimesRolesService restricredTimesRolesService) {
		this.restrictedTimesRolesService = restricredTimesRolesService;
	}

	public void setTimesResourcesExclusionService(
			TimesResourcesExclusionService timesResourcesExclusionService) {
		this.timesResourcesExclusionService = timesResourcesExclusionService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public void setRolesHierarchyService(RolesHierarchyService rolesHierarchyService) {
		this.rolesHierarchyService = rolesHierarchyService;
	}

	public void setSecuredResourcesService(
			SecuredResourcesService securedResourcesService) {
		this.securedResourcesService = securedResourcesService;
	}

	public void setSecuredResourcesRolesService(
			SecuredResourcesRolesService securedResourcesRolesService) {
		this.securedResourcesRolesService = securedResourcesRolesService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public void setViewHierarchyService(ViewHierarchyService viewHierarchyService) {
		this.viewHierarchyService = viewHierarchyService;
	}

	public void setViewMappingService(ViewMappingService viewMappingService) {
		this.viewMappingService = viewMappingService;
	}

	public void setViewResourcesService(ViewResourcesService viewResourcesService) {
		this.viewResourcesService = viewResourcesService;
	}
	
	
	
	
	// UsersService
	public Page usersServiceGetList(UserSearchVO searchVO) throws Exception{
		return usersService.getList(searchVO);
	}
	
	public Users usersServiceGet(String userId) throws Exception{
		return usersService.get(userId);
	}
	
	public Users usersServiceSave(Users users, Authorities[] authorities) throws Exception{
		return usersService.save(users, authorities);
	}
	
	public Users usersServiceUpdate(Users users, GroupsUsers newGroupsUsers, GroupsUsers currentGroupsUsers, Authorities[] authorities) throws Exception{
		return usersService.update(users, newGroupsUsers, currentGroupsUsers, authorities);
	}
	
	public void usersServiceDelete(String[] userId) throws Exception{
		usersService.delete(userId);
	}
	
	public List<Users> usersServiceFindUserByName(String userName) throws Exception{
		return usersService.findUserByName(userName);
	}
	
	
	// AuthoritiesService
	public Page authoritiesServiceGetList(AuthoritySearchVO searchVO) throws Exception{
		return authoritiesService.getList(searchVO);
	}
	
	public Page authoritiesServiceGetExistList(AuthoritySearchVO searchVO) throws Exception{
		return authoritiesService.getExistList(searchVO);
	}
	
	public List<Groups> authoritiesServiceGetGroupList(AuthoritySearchVO searchVO) throws Exception{
		return authoritiesService.getGroupList(searchVO);
	}
	
	public void authoritiesServiceRemoveAndSaveList(String[] groupId, String roleId) throws Exception{
		authoritiesService.removeAndSaveList(groupId, roleId);
	}
	
	public void authoritiesServiceAddUser(String[] userIds, String roleId) throws Exception{
		authoritiesService.addUser(userIds, roleId);
	}
	
	public void authoritiesServicedeleteUser(String[] userIds, String roleId) throws Exception{
		authoritiesService.deleteUser(userIds, roleId);
	}
	
	
	// DataUploadService
	public DataUpload dataUploadServiceSave(DataUpload dataUpload) throws Exception{
		return dataUploadService.save(dataUpload);
	}
	
	public Page dataUploadServiceGetList(CommonSearchVO searchVO) throws Exception{
		return dataUploadService.getList(searchVO);
	}
	
	public void dataUploadServiceDoSnapshot() throws Exception{
		dataUploadService.doSnapshot();
	}
	
	public void dataUploadServiceApplyExcel(String fileId) throws Exception{
		dataUploadService.applyExcel(fileId);
	}
	
	
	// GroupService
	public List<IamTree> groupsServiceGetGroupTree(String parentNode) throws Exception{
		return groupsService.getGroupTree(parentNode);
	}
	
	public List<IamTree> groupsServiceGetRootNodeOfGroups() throws Exception{
		return groupsService.getRootNodeOfGroups();
	}
	
	public Groups groupsServiceSave(Groups groups) throws Exception{
		return groupsService.save(groups);
	}
	
	public void groupsServiceUpdate(Groups groups) throws Exception{
		groupsService.update(groups);
	}
	
	public void groupsServiceRemove(String currentNode) throws Exception{
		groupsService.remove(currentNode);
	}
	
	public List<Groups> groupsServiceGetList() throws Exception{
		return groupsService.getList();
	}
	
	public String groupsServiceGetGroupNameList(String keyword) throws Exception{
		return groupsService.getGroupNameList(keyword);
	}
	
	public String groupsServiceGetGroupIdByGroupName(String groupName) throws Exception{
		return groupsService.getGroupIdByGroupName(groupName);
	}
	
	public List<String> groupsServiceGetParentsGroupIds(String groupId) throws Exception{
		return groupsService.getParentsGroupIds(groupId);
	}
	
	
	// Restricted Times Resources Service
	public Page restrictedTimesResourcesServiceGetTimeResourceList(RestrictedTimesSearchVO searchVO) throws Exception{
		return restrictedTimesResourcesService.getTimeResourceList(searchVO);
	}
	
	public List<Roles> restrictedTimesResourcesServiceFindRoleListByTime(String timeId) throws Exception{
		return restrictedTimesResourcesService.findRoleListByTime(timeId);
	}
	
	public Page restrictedTimesResourcesServiceFindResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception{
		return restrictedTimesResourcesService.findResourceListByTime(searchVO);
	}
	
	public Page restrictedTimesResourcesServiceFindUnmappedResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception{
		return restrictedTimesResourcesService.findUnmappedResourceListByTime(searchVO);
	}
	
	public void restrictedTimesResourcesServiceAddTimeResources(List<RestrictedTimesResources> list) throws Exception{
		restrictedTimesResourcesService.addTimeResources(list);
	}
	
	public void restrictedTimesResourcesServiceDelete(List<RestrictedTimesResourcesId> list) throws Exception{
		restrictedTimesResourcesService.delete(list);
	}
	
	
	// Restricted Times Roles Service
	public Page restrictedTimesRolesServiceGetTimeRoleList(RestrictedTimesSearchVO searchVO) throws Exception{
		return restrictedTimesRolesService.getTimeRoleList(searchVO);
	}
	
	public List<Roles> restrictedTimesRolesServiceFindRoleListByTime(String timeId) throws Exception{
		return restrictedTimesRolesService.findRoleListByTime(timeId);
	}
	
	public void restrictedTimesRolesServiceSaveTimeRoles(List<RestrictedTimesRoles> list) throws Exception{
		restrictedTimesRolesService.saveTimeRoles(list);
	}
	
	public void restrictedTimesRolesServiceDelete(List<RestrictedTimesRolesId> list) throws Exception{
		restrictedTimesRolesService.delete(list);
	}
	
	public void restrictedTimesRolesServiceRemoveTimesRolesByTime(String timeId) throws Exception{
		restrictedTimesRolesService.removeTimesRolesByTime(timeId);
	}
	
	
	// Restricted Times Service
	public Page restrictedTimesServiceGetList(RestrictedTimesSearchVO searchVO) throws Exception{
		return restrictedTimesService.getList(searchVO);
	}
	
	public void restrictedTimesServiceDelete(String[] timeIds) throws Exception{
		restrictedTimesService.delete(timeIds);
	}
	
	public void restrictedTimesServiceUpdate(RestrictedTimes restrictedTimes) throws Exception{
		restrictedTimesService.update(restrictedTimes);
	}
	
	public RestrictedTimes restrictedTimesServiceSave(RestrictedTimes restrictedTimes) throws Exception{
		return restrictedTimesService.save(restrictedTimes);
	}
	
	
	// Times Resources Exclusion Service
	public Page timesResourcesExclusionServiceGetTimeExclusionList(RestrictedTimesSearchVO searchVO) throws Exception{
		return timesResourcesExclusionService.getTimeExclusionList(searchVO);
	}
	
	public List<Roles> timesResourcesExclusionServiceFindRoleListByTimeResource(String timeId, String resourceId) throws Exception{
		return timesResourcesExclusionService.findRoleListByTimeResource(timeId, resourceId);
	}
	
	public void timesResourcesExclusionServiceSaveTimeExclusion(List<TimesResourcesExclusion> list) throws Exception{
		timesResourcesExclusionService.saveTimeExclusion(list);
	}
	
	public void timesResourcesExclusionServiceDelete(List<TimesResourcesExclusionId> list) throws Exception{
		timesResourcesExclusionService.delete(list);
	}
	
	public void timesResourcesExclusionServiceRemoveTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception{
		timesResourcesExclusionService.removeTimesExclusionByTimeResource(timeId, resourceId);
	}
	
	
	// Roles Service
	public List<IamTree> rolesServiceGetRoleTree(String parentNode) throws Exception{
		return rolesService.getRoleTree(parentNode);
	}
	
	public List<IamTree> rolesServiceGetRootNodeOfRoles() throws Exception{
		return rolesService.getRootNodeOfRoles();
	}
	
	public List<Roles> rolesServiceFindRoles(String userId) throws Exception{
		return rolesService.findRoles(userId);
	}
	
	public void rolesServiceUpdate(Roles roles) throws Exception{
		rolesService.update(roles);
	}
	
	public void rolesServiceRemove(String currentNode) throws Exception{
		rolesService.remove(currentNode);
	}
	
	public List<Roles> rolesServiceGetList() throws Exception{
		return rolesService.getList();
	}
	
	public Roles rolesServiceSave(Roles roles) throws Exception{
		return rolesService.save(roles);
	}
	
	public String rolesServiceGetRoleNameList(String keyword) throws Exception{
		return rolesService.getRoleNameList(keyword);
	}
	
	public String rolesServiceGetRoleIdByRoleName(String roleName) throws Exception{
		return rolesService.getRoleIdByRoleName(roleName);
	}
	
	public List<String> rolesServiceGetParentRoleIds(String roleId) throws Exception{
		return rolesService.getParentsRoleIds(roleId);
	}
	
	
	// SecuredResourcesService
	public Page resourceServiceGetList(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getList(searchVO);
	}
	
	public void resourceServiceDelete(String[] resourceIds) throws Exception{
		securedResourcesService.delete(resourceIds);
	}
	
	public Page resourceServiceGetUnmappedList(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getUnmappedList(searchVO);
	}
	
	public Page resourceServiceGetUnmappedResourceList(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getUnmappedResourceList(searchVO);
	}
	
	public Page resourceServiceGetUnmappedAnyRoleList(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getUnmappedAnyRoleList(searchVO);
	}

	public Page resourceServiceGetMappedList(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getMappedList(searchVO);
	}

	public Page resourceServiceGetListWithLevel(ResourceSearchVO searchVO) throws Exception{
		return securedResourcesService.getListwithLevel(searchVO);
	}
	
	public void resourceServiceUpdate(SecuredResources resources) throws Exception{
		securedResourcesService.update(resources);
	}
	
	public SecuredResources resourceServiceSave(SecuredResources resources) throws Exception{
		return securedResourcesService.save(resources);
	}
	
	
	
	// Secured Resources Roles Service
	public void resourcesRolesServiceAddSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception{
		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, roleId);
	}
	
	public void resourcesRolesServiceDeleteSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception{
		securedResourcesRolesService.deleteSecuredResourceRoles(resourceIds, roleId);
	}
	
	
	
	// View Mapping Service
	public Page viewMappingServiceGetList(ViewResourceSearchVO searchVO) throws Exception{
		return viewMappingService.getList(searchVO);
	}
	
	public void viewMappingServiceDelete(List<ViewResourcesMappingPK> list) throws Exception{
		viewMappingService.delete(list);
	}
	
	public List<ViewResourcesMapping> viewMappingServiceGet(String viewResourceId) throws Exception{
		return viewMappingService.get(viewResourceId);
	}
	
	public ViewResourcesMapping viewMappingServiceSave(ViewResourcesMapping[] viewResourcesMapping) throws Exception{
		return viewMappingService.save(viewResourcesMapping);
	}
	
	
	// View Resource Service
	public Page viewResourceServiceGetList(ViewResourceSearchVO searchVO) throws Exception{
		return viewResourcesService.getList(searchVO);
	}
	
	public Page viewResourceServiceGetUnmappedList(ViewResourceSearchVO searchVO) throws Exception{
		return viewResourcesService.getUnmappedList(searchVO); 
	}
	
	public void viewResourceServiceDelete(String[] viewResourceIds) throws Exception{
		viewResourcesService.delete(viewResourceIds);
	}
	
	public void viewResourceServiceUpdate(ViewResource viewResource) throws Exception{
		viewResourcesService.update(viewResource);
	}
	
	public ViewResource viewResourceServiceSave(ViewResource viewResource) throws Exception{
		return viewResourcesService.save(viewResource);
	}
	
	public List<IamTree> viewResourceServiceGetViewTree(String parentNode) throws Exception{
		return viewResourcesService.getViewTree(parentNode);
	}
	
	public void viewResourceServiceRemove(String viewResourceId) throws Exception{
		viewResourcesService.remove(viewResourceId);
	}
	
	public String viewResourceServiceGetViewNameListWithSystemName(String keyword, String systemName) throws Exception{
		return viewResourcesService.getViewNameListWithSystemName(keyword, systemName);
	}
	
	public String viewResourceServiceGetViewResourceIdByViewName(String viewName) throws Exception{
		return viewResourcesService.getViewResourceIdByViewName(viewName);
	}
	
	public List<String> viewResourceServiceGetParentsViewIds(String viewResourceId) throws Exception{
		return viewResourcesService.getParentsViewIds(viewResourceId);
	}
	
	public List<IamTree> viewResourceServiceGetRootNodeOfViewWithSystemName(String systemName) throws Exception{
		return viewResourcesService.getRootNodeOfViewsWithSystemName(systemName);
	}
	
	public boolean viewResourceServiceIsExistName(String viewName) throws Exception{
		return viewResourcesService.isExistName(viewName);
	}
}
