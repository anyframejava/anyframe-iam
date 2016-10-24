package org.anyframe.iam.admin.roleshierarchy.dao.impl;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;
import org.anyframe.iam.admin.roleshierarchy.dao.RolesHierarchyDao;
import org.hibernate.Query;


public class RolesHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<RolesHierarchy, RolesHierarchyId> implements RolesHierarchyDao{
	RolesHierarchyDaoHibernateImpl(){
		super(RolesHierarchy.class);
	}
	
	public void removeAllRolesHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllRolesHierarchy");
		query.executeUpdate();	
	}
}
