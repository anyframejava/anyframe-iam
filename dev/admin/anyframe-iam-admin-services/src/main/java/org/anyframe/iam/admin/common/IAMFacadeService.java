package org.anyframe.iam.admin.common;

import java.util.List;

import javax.jws.WebService;

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
import org.anyframe.iam.admin.domain.TimesResourcesExclusion;
import org.anyframe.iam.admin.domain.TimesResourcesExclusionId;
import org.anyframe.iam.admin.domain.Users;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.vo.AuthoritySearchVO;
import org.anyframe.iam.admin.vo.CommonSearchVO;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.iam.admin.vo.UserSearchVO;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;

@WebService
public interface IAMFacadeService {
	
	// Authorities Service
	Page authoritiesServiceGetList(AuthoritySearchVO searchVO) throws Exception;
	
	Page authoritiesServiceGetExistList(AuthoritySearchVO searchVO) throws Exception;
	
	List<Groups> authoritiesServiceGetGroupList(AuthoritySearchVO searchVO) throws Exception;
	
	void authoritiesServiceRemoveAndSaveList(String[] groupId, String roleId) throws Exception;
	
	void authoritiesServiceAddUser(String[] userIds, String roleId) throws Exception;
	
	void authoritiesServicedeleteUser(String[] userIds, String roleId) throws Exception;
	
	
	// DataUpload Service
	DataUpload dataUploadServiceSave(DataUpload dataUpload) throws Exception;
	
	Page dataUploadServiceGetList(CommonSearchVO searchVO) throws Exception;
	
	void dataUploadServiceDoSnapshot() throws Exception;
	
	void dataUploadServiceApplyExcel(String fileId) throws Exception;
	
	
	// Groups Service
	List<IamTree> groupsServiceGetGroupTree(String parentNode) throws Exception;
	
	List<IamTree> groupsServiceGetRootNodeOfGroups() throws Exception;
	
	Groups groupsServiceSave(Groups groups) throws Exception;
	
	void groupsServiceUpdate(Groups groups) throws Exception;
	
	void groupsServiceRemove(String currentNode) throws Exception;
	
	List<Groups> groupsServiceGetList() throws Exception;
	
	String groupsServiceGetGroupNameList(String keywork) throws Exception;
	
	String groupsServiceGetGroupIdByGroupName(String groupName) throws Exception;
	
	List<String> groupsServiceGetParentsGroupIds(String groupId) throws Exception;
	
	
	// Restricted Times Resources Service
	Page restrictedTimesResourcesServiceGetTimeResourceList(RestrictedTimesSearchVO searchVO) throws Exception;
	
	List<Roles> restrictedTimesResourcesServiceFindRoleListByTime(String timeId) throws Exception;
	
	Page restrictedTimesResourcesServiceFindResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception;
	
	Page restrictedTimesResourcesServiceFindUnmappedResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception;
	
	void restrictedTimesResourcesServiceAddTimeResources(List<RestrictedTimesResources> list) throws Exception;
	
	void restrictedTimesResourcesServiceDelete(List<RestrictedTimesResourcesId> list) throws Exception;
	
	
	// Restricted Times Roles Service
	Page restrictedTimesRolesServiceGetTimeRoleList(RestrictedTimesSearchVO searchVO) throws Exception;
	
	List<Roles> restrictedTimesRolesServiceFindRoleListByTime(String timeId) throws Exception;
	
	void restrictedTimesRolesServiceSaveTimeRoles(List<RestrictedTimesRoles> list) throws Exception;
	
	void restrictedTimesRolesServiceDelete(List<RestrictedTimesRolesId> list) throws Exception;
	
	void restrictedTimesRolesServiceRemoveTimesRolesByTime(String timeId) throws Exception;
	
	
	// Restricted Times Service
	Page restrictedTimesServiceGetList(RestrictedTimesSearchVO searchVO) throws Exception;
	
	void restrictedTimesServiceDelete(String[] timeIds) throws Exception;
	
	void restrictedTimesServiceUpdate(RestrictedTimes restrictedTimes) throws Exception;
	
	RestrictedTimes restrictedTimesServiceSave(RestrictedTimes restrictedTimes) throws Exception;
	
	
	// Times Resources Exclusion Service
	Page timesResourcesExclusionServiceGetTimeExclusionList(RestrictedTimesSearchVO searchVO) throws Exception;
	
	List<Roles> timesResourcesExclusionServiceFindRoleListByTimeResource(String timeId, String resourceId) throws Exception;
	
	void timesResourcesExclusionServiceSaveTimeExclusion(List<TimesResourcesExclusion> list) throws Exception;
	
	void timesResourcesExclusionServiceDelete(List<TimesResourcesExclusionId> list) throws Exception;
	
	void timesResourcesExclusionServiceRemoveTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception;
	
	
	// Roles Service
	List<IamTree> rolesServiceGetRoleTree(String parentNode) throws Exception;
	
	List<IamTree> rolesServiceGetRootNodeOfRoles() throws Exception;
	
	List<Roles> rolesServiceFindRoles(String userId) throws Exception;
	
	void rolesServiceUpdate(Roles roles) throws Exception;
	
	void rolesServiceRemove(String currentNode) throws Exception;
	
	List<Roles> rolesServiceGetList() throws Exception;
	
	Roles rolesServiceSave(Roles roles) throws Exception;
	
	String rolesServiceGetRoleNameList(String keyword) throws Exception;
	
	String rolesServiceGetRoleIdByRoleName(String roleName) throws Exception;
	
	List<String> rolesServiceGetParentRoleIds(String roleId) throws Exception;
	
	
	
	// Secured Resources Service
	Page resourceServiceGetList(ResourceSearchVO searchVO) throws Exception;
	
	void resourceServiceDelete(String[] resourceIds) throws Exception;
	
	Page resourceServiceGetUnmappedList(ResourceSearchVO searchVO) throws Exception;
	
	Page resourceServiceGetUnmappedResourceList(ResourceSearchVO searchVO) throws Exception;
	
	Page resourceServiceGetUnmappedAnyRoleList(ResourceSearchVO searchVO) throws Exception;
	
	Page resourceServiceGetMappedList(ResourceSearchVO searchVO) throws Exception;
	
	Page resourceServiceGetListWithLevel(ResourceSearchVO searchVO) throws Exception;
	
	void resourceServiceUpdate(SecuredResources securedResources) throws Exception;
	
	SecuredResources resourceServiceSave(SecuredResources securedResources) throws Exception;
	
	
	// Secured Resources Roles Service
	void resourcesRolesServiceAddSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception;
	
	void resourcesRolesServiceDeleteSecuredResourcesRoles(String[] resourceIds, String roleId) throws Exception;
	

	
	// User Service
	Page usersServiceGetList(UserSearchVO userSearchVO) throws Exception;
	
	Users usersServiceGet(String userId) throws Exception;
	
	Users usersServiceSave(Users users, Authorities[] authorities) throws Exception;
	
	Users usersServiceUpdate(Users users, GroupsUsers newGroupsUsers, GroupsUsers currentGroupsUsers, Authorities[] authorities)
	throws Exception;
	
	void usersServiceDelete(String[] userId) throws Exception;

	List<Users> usersServiceFindUserByName(String userName) throws Exception;
	
	
	
	// View Mapping Service
	Page viewMappingServiceGetList(ViewResourceSearchVO searchVO) throws Exception;
	
	void viewMappingServiceDelete(List<ViewResourcesMappingPK> list) throws Exception;
	
	List<ViewResourcesMapping> viewMappingServiceGet(String viewResourceId) throws Exception;
	
	ViewResourcesMapping viewMappingServiceSave(ViewResourcesMapping[] viewResourcesMapping) throws Exception;
	
	
	
	// View Resource Service
	Page viewResourceServiceGetList(ViewResourceSearchVO searchVO) throws Exception;
	
	Page viewResourceServiceGetUnmappedList(ViewResourceSearchVO searchVO) throws Exception;
	
	void viewResourceServiceDelete(String[] viewResourceIds) throws Exception;
	
	void viewResourceServiceUpdate(ViewResource viewResource) throws Exception;
	
	ViewResource viewResourceServiceSave(ViewResource viewResource) throws Exception;
	
	List<IamTree> viewResourceServiceGetViewTree(String parentNode) throws Exception;
	
	void viewResourceServiceRemove(String viewResourceId) throws Exception;
	
	String viewResourceServiceGetViewNameListWithSystemName(String keyword, String systemName) throws Exception;
	
	String viewResourceServiceGetViewResourceIdByViewName(String viewName) throws Exception;
	
	List<String> viewResourceServiceGetParentsViewIds(String viewResourceId) throws Exception;
	
	List<IamTree> viewResourceServiceGetRootNodeOfViewWithSystemName(String systemName) throws Exception;
	
	boolean viewResourceServiceIsExistName(String viewName) throws Exception;
	
}
