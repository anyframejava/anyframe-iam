package org.anyframe.iam.admin.roleshierarchy.service.impl;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;
import org.anyframe.iam.admin.roleshierarchy.dao.RolesHierarchyDao;
import org.anyframe.iam.admin.roleshierarchy.service.RolesHierarchyService;

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
