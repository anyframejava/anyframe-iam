package anyframe.iam.admin.groupshierarchy.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;

public interface GroupsHierarchyDao extends IamGenericDao<GroupsHierarchy, GroupsHierarchyId>{
	void removeAllGroupsHierarchy() throws Exception;
}
