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

package anyframe.iam.admin.groups.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.groups.dao.GroupsDao;

@Repository("groupsDao")
public class GroupsDaoHibernateImpl extends IamGenericDaoHibernate<Groups, String> implements GroupsDao {

	public GroupsDaoHibernateImpl() {
		super(Groups.class);
	}

	// Group Tree 정보 조회
	// 현재 node 의 하위 node 중에서 1 level 만 조회 한다.
	@SuppressWarnings("unchecked")
	public List<IamTree> getGroupTree(String parentNode) throws Exception {

		// context-hibernate.xml의 mappingLocations 에 정의된 파일들중에서 getGroupTreeData
		// 쿼리 정보를 가져온다.
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getGroupTreeData");

		// Parameter 세팅
		query.setParameter("parentNode", parentNode);

		// 쿼리 수행
		List<IamTree> list = query.list();

		return list;
	}

	// RootNode 정보 조회
	@SuppressWarnings("unchecked")
	public List<IamTree> getRootNodeOfGroups() throws Exception {

		// context-hibernate.xml의 mappingLocations 에 정의된 파일들중에서 getGroupTreeData
		// 쿼리 정보를 가져온다.
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRootNodeOfGroups");

		// 쿼리 수행
		return query.list();
	}

	// 선택된 node 의 하위 node 조회

	@SuppressWarnings("unchecked")
	public List<String> getGroupHierarchy(String parentNode) throws Exception {

		// context-hibernate.xml의 mappingLocations 에 정의된 파일들중에서 getGroupTreeData
		// 쿼리 정보를 가져온다.

		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("countGroupsList");

		// Parameter 세팅

		query.setParameter("parentGroup", parentNode);

		// 쿼리 수행
		List<String> list = query.list();

		return list;
	}

	// Groups 테이블의 Group 정보를 수정한다.
	public void update(Groups groups) throws Exception {
		this.getHibernateTemplate().merge(groups);
	}

	public List<Groups> getList() throws Exception {
		Object[] arg = new Object[1];
		arg[0] = "keywordStr=%";

		List<Groups> resultList = (List<Groups>) this.getDynamicHibernateService().findList("findAllGroupsList", arg);
		return resultList;
	}
}
