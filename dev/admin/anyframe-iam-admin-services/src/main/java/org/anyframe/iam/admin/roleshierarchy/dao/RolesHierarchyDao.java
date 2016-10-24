package org.anyframe.iam.admin.roleshierarchy.dao;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;


public interface RolesHierarchyDao extends IamGenericDao<RolesHierarchy, RolesHierarchyId>{
	void removeAllRolesHierarchy() throws Exception;
}
