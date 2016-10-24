package org.anyframe.iam.admin.groupshierarchy.dao;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;


public interface GroupsHierarchyDao extends IamGenericDao<GroupsHierarchy, GroupsHierarchyId>{
	void removeAllGroupsHierarchy() throws Exception;
}
