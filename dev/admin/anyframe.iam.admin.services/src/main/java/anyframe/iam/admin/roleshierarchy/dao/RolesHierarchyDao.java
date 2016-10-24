package anyframe.iam.admin.roleshierarchy.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;

public interface RolesHierarchyDao extends IamGenericDao<RolesHierarchy, RolesHierarchyId>{
	void removeAllRolesHierarchy() throws Exception;
}
