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

package org.anyframe.iam.admin.assist.dao.impl;

import org.anyframe.iam.admin.assist.dao.AssistDao;
import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.CandidateSecuredResources;
import org.anyframe.util.StringUtil;
import org.hibernate.Query;

public class AssistDaoHibernateImpl extends IamGenericDaoHibernate<CandidateSecuredResources, String> implements AssistDao {

	public AssistDaoHibernateImpl() {
		super(CandidateSecuredResources.class);
	}
	
	public int removeAll() throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeCandidateSecuredResources");
		return query.executeUpdate();
	}
	
	public int removeAll(String systemName) throws Exception {
		systemName = StringUtil.null2str(systemName);
		
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeCandidateSecuredResources");
		query.setParameter("systemName", systemName);
		return query.executeUpdate();
	}
}
