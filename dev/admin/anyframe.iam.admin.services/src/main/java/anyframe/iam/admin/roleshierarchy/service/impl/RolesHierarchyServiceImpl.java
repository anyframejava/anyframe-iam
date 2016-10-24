package anyframe.iam.admin.roleshierarchy.service.impl;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;
import anyframe.iam.admin.roleshierarchy.dao.RolesHierarchyDao;
import anyframe.iam.admin.roleshierarchy.service.RolesHierarchyService;

public class RolesHierarchyServiceImpl extends GenericServiceImpl<RolesHierarchy, RolesHierarchyId> implements RolesHierarchyService{
	
	private RolesHierarchyDao rolesHierarchyDao;
	
	public RolesHierarchyServiceImpl(RolesHierarchyDao rolesHierarchyDao){
		super(rolesHierarchyDao);
		this.rolesHierarchyDao = rolesHierarchyDao;
	}
	
	public RolesHierarchy save(RolesHierarchy rolesHierarchy) throws Exception{
		return rolesHierarchyDao.save(rolesHierarchy);
	}
	
	public void removeAllRolesHierarchy() throws Exception{
		rolesHierarchyDao.removeAllRolesHierarchy();
	}
}
