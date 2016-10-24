package anyframe.iam.admin.roleshierarchy.dao.impl;

import org.hibernate.Query;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;
import anyframe.iam.admin.roleshierarchy.dao.RolesHierarchyDao;

public class RolesHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<RolesHierarchy, RolesHierarchyId> implements RolesHierarchyDao{
	RolesHierarchyDaoHibernateImpl(){
		super(RolesHierarchy.class);
	}
	
	public void removeAllRolesHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllRolesHierarchy");
		query.executeUpdate();	
	}
}
