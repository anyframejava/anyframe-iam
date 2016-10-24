package org.anyframe.iam.admin.viewhierarchy.dao.impl;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;
import org.anyframe.iam.admin.viewhierarchy.dao.ViewHierarchyDao;
import org.hibernate.Query;


public class ViewHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<ViewHierarchy, ViewHierarchyId> implements ViewHierarchyDao{
	public ViewHierarchyDaoHibernateImpl(){
		super(ViewHierarchy.class);
	}
	
	public void removeAllViewHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllViewHierarchy");
		query.executeUpdate();	
	}
	
}
