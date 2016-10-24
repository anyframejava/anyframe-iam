package anyframe.iam.admin.roleshierarchy.service;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;

public interface RolesHierarchyService extends GenericService<RolesHierarchy, RolesHierarchyId>{
	RolesHierarchy save(RolesHierarchy rolesHierarchy) throws Exception;
	
	void removeAllRolesHierarchy() throws Exception;
}
