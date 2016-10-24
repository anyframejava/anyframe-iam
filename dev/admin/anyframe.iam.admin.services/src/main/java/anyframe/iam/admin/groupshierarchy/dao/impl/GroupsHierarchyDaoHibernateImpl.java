package anyframe.iam.admin.groupshierarchy.dao.impl;

import org.hibernate.Query;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;
import anyframe.iam.admin.groupshierarchy.dao.GroupsHierarchyDao;

public class GroupsHierarchyDaoHibernateImpl extends IamGenericDaoHibernate<GroupsHierarchy,GroupsHierarchyId> implements GroupsHierarchyDao{
	public GroupsHierarchyDaoHibernateImpl(){
		super(GroupsHierarchy.class);
	}
	
	public void removeAllGroupsHierarchy() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllGroupsHierarchy");
		query.executeUpdate();	
	}
}
