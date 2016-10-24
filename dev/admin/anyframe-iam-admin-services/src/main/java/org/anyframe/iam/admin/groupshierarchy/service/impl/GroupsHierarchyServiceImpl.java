package org.anyframe.iam.admin.groupshierarchy.service.impl;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;
import org.anyframe.iam.admin.groupshierarchy.dao.GroupsHierarchyDao;
import org.anyframe.iam.admin.groupshierarchy.service.GroupsHierarchyService;

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
