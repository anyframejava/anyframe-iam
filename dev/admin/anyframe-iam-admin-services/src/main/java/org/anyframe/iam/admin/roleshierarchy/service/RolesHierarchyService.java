package org.anyframe.iam.admin.roleshierarchy.service;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;

public interface RolesHierarchyService extends GenericService<RolesHierarchy, RolesHierarchyId>{
	RolesHierarchy save(RolesHierarchy rolesHierarchy) throws Exception;
	
	void removeAllRolesHierarchy() throws Exception;
}
