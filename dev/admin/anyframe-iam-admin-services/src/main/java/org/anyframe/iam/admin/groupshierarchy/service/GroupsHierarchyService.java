package org.anyframe.iam.admin.groupshierarchy.service;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;

public interface GroupsHierarchyService extends GenericService<GroupsHierarchy, GroupsHierarchyId>{
	GroupsHierarchy save(GroupsHierarchy groupsHierarchy) throws Exception;
	void removeAllGroupsHierarchy() throws Exception;
}
