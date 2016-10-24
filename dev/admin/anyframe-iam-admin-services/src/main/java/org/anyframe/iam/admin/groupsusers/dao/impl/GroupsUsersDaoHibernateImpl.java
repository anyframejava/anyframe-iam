/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.anyframe.iam.admin.groupsusers.dao.impl;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.GroupsUsers;
import org.anyframe.iam.admin.domain.GroupsUsersId;
import org.anyframe.iam.admin.groupsusers.dao.GroupsUsersDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


@Repository("groupsUsersDao")
public class GroupsUsersDaoHibernateImpl extends IamGenericDaoHibernate<GroupsUsers, GroupsUsersId> implements
		GroupsUsersDao {

	public GroupsUsersDaoHibernateImpl() {
		super(GroupsUsers.class);
	}
	
	public void remove(String groupId) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeGroupsUsersByGroupId");
		query.setParameter("groupId", groupId);
		query.executeUpdate();
	}
	
	public void removeAllGroupsUsers() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllGroupsUsers");
		query.executeUpdate();	
	}
}
