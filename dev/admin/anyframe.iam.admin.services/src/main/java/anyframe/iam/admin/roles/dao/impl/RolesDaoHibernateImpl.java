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
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.domain.TempRoles;
import anyframe.iam.admin.roles.dao.RolesDao;

@Repository("rolesDao")
public class RolesDaoHibernateImpl extends IamGenericDaoHibernate<Roles, String> implements RolesDao {

	public RolesDaoHibernateImpl() {
		super(Roles.class);
	}

	@SuppressWarnings("unchecked")
	public List<IamTree> getRoleTree(String parentNode) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRoleTreeData");
		query.setParameter("parentNode", parentNode);
		List<IamTree> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<IamTree> getRootNodeOfRoles() throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRootNodeOfRoles");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> getRoleHierarchy(String parentNode) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("countRolesList");
		query.setParameter("parentRole", parentNode);
		List<String> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Roles> findRoles(String userId) throws Exception {
		Object[] args = new Object[2];
		args[0] = "type=" + "U";
		args[1] = "userId=" + userId;

		return this.getDynamicHibernateService().findList("findRoles", args);
	}

	public void update(Roles roles) throws Exception {
		this.getHibernateTemplate().merge(roles);
	}

	@SuppressWarnings("unchecked")
	public List<Roles> getList() throws Exception {
		Object[] arg = new Object[1];
		arg[0] = "keywordStr=%";

		return this.getDynamicHibernateService().findList("findAllRolesList", arg);
	}
	
	@SuppressWarnings("unchecked")
	public String getRoleNameList(String keyword) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		Object[] args = new Object[1];
		args[0] = "keyword=" + keyword + "%";
		List list = this.getDynamicHibernateService().findList("findRoleNameList", args);

		StringBuffer roleNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			roleNameList.append(((Map) list.get(i)).get("roleName") + "\n");
		}
		return roleNameList.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getRoleIdByRoleName(String roleName) throws Exception {
		Object[] args = new Object[1];
		String changedRoleName = roleName.toLowerCase();
		args[0] = "roleName=" + changedRoleName;
		List<Roles> list = this.getDynamicHibernateService().findList("findRoleIdByRoleName", args);
		
		if(list.size() == 0){
			return "";
		}
		
		return list.get(0).getRoleId();
	}
	
	@SuppressWarnings("unchecked")
	public List<TempRoles> makeAllTempRolesList() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("makeAllTempRolesList");

		return query.list();
	}
	
	public void removeAllRoles() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllRoles");
		query.executeUpdate();	
	}
}
