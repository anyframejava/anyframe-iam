package anyframe.iam.admin.groupshierarchy.service;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;

public interface GroupsHierarchyService extends GenericService<GroupsHierarchy, GroupsHierarchyId>{
	GroupsHierarchy save(GroupsHierarchy groupsHierarchy) throws Exception;
	void removeAllGroupsHierarchy() throws Exception;
}
