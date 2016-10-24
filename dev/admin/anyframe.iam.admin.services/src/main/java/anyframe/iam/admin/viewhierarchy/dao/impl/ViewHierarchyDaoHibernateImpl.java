package anyframe.iam.admin.viewhierarchy.dao.impl;

import org.hibernate.Query;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;
import anyframe.iam.admin.viewhierarchy.dao.ViewHierarchyDao;

public class ViewHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<ViewHierarchy, ViewHierarchyId> implements ViewHierarchyDao{
	public ViewHierarchyDaoHibernateImpl(){
		super(ViewHierarchy.class);
	}
	
	public void removeAllViewHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllViewHierarchy");
		query.executeUpdate();	
	}
	
}
