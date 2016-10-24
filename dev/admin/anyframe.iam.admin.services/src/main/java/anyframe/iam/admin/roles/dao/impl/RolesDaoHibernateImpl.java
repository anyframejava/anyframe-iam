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

package anyframe.iam.admin.roles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.roles.dao.RolesDao;

@Repository("rolesDao")
public class RolesDaoHibernateImpl extends IamGenericDaoHibernate<Roles, String> implements RolesDao {

	public RolesDaoHibernateImpl() {
		super(Roles.class);
	}

	public List<IamTree> getRoleTree(String parentNode) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRoleTreeData");
		query.setParameter("parentNode", parentNode);
		List<IamTree> list = query.list();
		return list;
	}

	public List<IamTree> getRootNodeOfRoles() throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRootNodeOfRoles");
		return query.list();
	}

	public List<String> getRoleHierarchy(String parentNode) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("countRolesList");
		query.setParameter("parentRole", parentNode);
		List<String> list = query.list();
		return list;
	}

	public List<Roles> findRoles(String userId) throws Exception {
		Object[] args = new Object[2];
		args[0] = "type=" + "U";
		args[1] = "userId=" + userId;

		return this.getDynamicHibernateService().findList("findRoles", args);
	}

	public void update(Roles roles) throws Exception {
		this.getHibernateTemplate().merge(roles);
	}

	public List<Roles> getList() throws Exception {
		Object[] arg = new Object[1];
		arg[0] = "keywordStr=%";

		return this.getDynamicHibernateService().findList("findAllRolesList", arg);
	}
}
