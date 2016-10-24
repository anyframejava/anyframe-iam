package org.anyframe.iam.admin.groupshierarchy.dao.impl;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;
import org.anyframe.iam.admin.groupshierarchy.dao.GroupsHierarchyDao;
import org.hibernate.Query;


public class GroupsHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<GroupsHierarchy,GroupsHierarchyId> implements GroupsHierarchyDao{
	public GroupsHierarchyDaoHibernateImpl(){
		super(GroupsHierarchy.class);
	}
	
	public void removeAllGroupsHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllGroupsHierarchy");
		query.executeUpdate();	
	}
}
