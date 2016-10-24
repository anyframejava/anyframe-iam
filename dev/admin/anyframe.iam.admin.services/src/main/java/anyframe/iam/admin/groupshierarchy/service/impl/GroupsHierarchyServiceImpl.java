package anyframe.iam.admin.groupshierarchy.service.impl;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;
import anyframe.iam.admin.groupshierarchy.dao.GroupsHierarchyDao;
import anyframe.iam.admin.groupshierarchy.service.GroupsHierarchyService;

public class GroupsHierarchyServiceImpl extends GenericServiceImpl<GroupsHierarchy, GroupsHierarchyId> implements GroupsHierarchyService{
	GroupsHierarchyDao groupsHierarchyDao;
	
	public GroupsHierarchyServiceImpl(GroupsHierarchyDao groupsHierarchyDao){
		super(groupsHierarchyDao);
		this.groupsHierarchyDao = groupsHierarchyDao;
	}
	
	public GroupsHierarchy save(GroupsHierarchy groupsHierarchy) throws Exception{
		return groupsHierarchyDao.save(groupsHierarchy);
	}
	
	public void removeAllGroupsHierarchy() throws Exception{
		groupsHierarchyDao.removeAllGroupsHierarchy();
	}
}
