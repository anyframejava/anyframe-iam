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

package org.anyframe.iam.admin.securedresources.dao.impl;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.IamResourceResult;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.domain.TempSecuredResources;
import org.anyframe.iam.admin.securedresources.dao.SecuredResourcesDao;
import org.anyframe.iam.admin.vo.ResourceSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository("securedResourcesDao")
public class SecuredResourcesDaoHibernateImpl extends IamGenericDaoHibernate<SecuredResources, String> implements
		SecuredResourcesDao {

	public SecuredResourcesDaoHibernateImpl() {
		super(SecuredResources.class);
	}

	public Page getList(ResourceSearchVO resourceSearchVO) throws Exception {
		int pageIndex = resourceSearchVO.getPage();
		int pageSize = resourceSearchVO.getRows();
		String sidx = StringUtil.null2str(resourceSearchVO.getSidx());
		String sord = StringUtil.null2str(resourceSearchVO.getSord());
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String searchCondition = StringUtil.null2str(resourceSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(resourceSearchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(resourceSearchVO.getSearchtype());
		String systemName = StringUtil.null2str(resourceSearchVO.getSystemName());

		Object[] args = new Object[7];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[4] = "sidx=" + sidx;
		args[5] = "sord=" + sord;
		args[6] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findSecuredResourcesList", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countSecuredResourcesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	public Page getMappedList(ResourceSearchVO resourceSearchVO) throws Exception {
		int pageIndex = resourceSearchVO.getPage();
		int pageSize = resourceSearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(resourceSearchVO.getSidx());
		String sord = StringUtil.null2str(resourceSearchVO.getSord());
		String searchCondition = StringUtil.null2str(resourceSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(resourceSearchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(resourceSearchVO.getSearchtype());
		String roleId = StringUtil.null2str(resourceSearchVO.getRoleId());
		String systemName = StringUtil.null2str(resourceSearchVO.getSystemName());

		Object[] args = new Object[8];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[4] = "roleId=" + roleId;
		args[5] = "sidx=" + sidx;
		args[6] = "sord=" + sord;
		args[7] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findMappedResourcesList", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countMappedResourcesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	public Page getUnmappedList(ResourceSearchVO resourceSearchVO) throws Exception {
		int pageIndex = resourceSearchVO.getPage();
		int pageSize = resourceSearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(resourceSearchVO.getSidx());
		String sord = StringUtil.null2str(resourceSearchVO.getSord());
		String searchCondition = StringUtil.null2str(resourceSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(resourceSearchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(resourceSearchVO.getSearchtype());
		String roleId = StringUtil.null2str(resourceSearchVO.getRoleId());
		String systemName = StringUtil.null2str(resourceSearchVO.getSystemName());

		Object[] args = new Object[8];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[4] = "sidx=" + sidx;
		args[5] = "sord=" + sord;
		args[6] = "roleId=" + roleId;
		args[7] = "systemName=" + systemName;
		
		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findUnmappedResourcesList", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countUnmappedResourcesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	public Page getListwithLevel(ResourceSearchVO resourceSearchVO) throws Exception {
		int pageIndex = resourceSearchVO.getPage();
		int pageSize = resourceSearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(resourceSearchVO.getSidx());
		String sord = StringUtil.null2str(resourceSearchVO.getSord());
		String searchCondition = StringUtil.null2str(resourceSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(resourceSearchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(resourceSearchVO.getSearchtype());
		String roleId = StringUtil.null2str(resourceSearchVO.getRoleId());
		String systemName = StringUtil.null2str(resourceSearchVO.getSystemName());

		int startIndex = (pageIndex - 1) * pageSize;

		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery(
				"findsecuredResourceswithlevel");
		StringBuffer replaceSQL = new StringBuffer();
		if (!("".equals(searchKeyword))) {
			if ("resourceName".equals(searchCondition)) {
				replaceSQL.append("AND a.resource_name like '%" + searchKeyword + "%'");
			}
			else if ("resourceId".equals(searchCondition)) {
				replaceSQL.append("AND a.resource_id like '%" + searchKeyword + "%'");
			}
			else {
				replaceSQL.append("AND a.resource_pattern like '%" + searchKeyword + "%'");
			}
		}
		if ("URL".equals(searchType) || "Method".equals(searchType) || "PointCut".equals(searchType)) {
			replaceSQL.append("AND a.resource_type like '" + searchType + "'");
		}
		if("".equals(systemName) || "All".equals(systemName)){
			replaceSQL.append(" AND a.resource_name = " + systemName);
		}
		
		String queryString = query.getQueryString();
		queryString = queryString.replace("--replaceSQL1", replaceSQL.toString());

		StringBuffer replaceSQLOrderby = new StringBuffer();

		if ("resourceId".equals(sidx) || "".equals(sidx)) {
			replaceSQLOrderby.append("ORDER BY a.resource_id ");
		}
		else {
			replaceSQLOrderby.append("ORDER BY a." + sidx);
		}

		if ("desc".equals(sord) || "".equals(sord)) {
			replaceSQLOrderby.append("DESC");
		}
		else {
			replaceSQLOrderby.append("ASC");
		}
		queryString = queryString.replace("--replaceSQL2", replaceSQLOrderby.toString());

		SQLQuery replacedQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
		replacedQuery.addEntity(IamResourceResult.class);
		replacedQuery.setParameter("roleId", roleId);
		replacedQuery.setFirstResult(startIndex);
		replacedQuery.setMaxResults(pageSize);
		
		@SuppressWarnings("unchecked")
		List<IamResourceResult> resultList = replacedQuery.list();

		query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("countsecuredResourceswithlevel");
		queryString = query.getQueryString();
		queryString = queryString.replace("--replaceSQL", replaceSQL.toString());
		replacedQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
		replacedQuery.setParameter("roleId", roleId);

		int totalSize = Integer.parseInt(replacedQuery.list().get(0).toString());

		Page resultPage = new Page(resultList, pageIndex, totalSize, pageUnit, pageSize);
		return resultPage;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TempSecuredResources> makeAllTempResourcesList() throws Exception {
		
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("makeAllTempResourcesList");

		return query.list();
	}
	
	public void removeAllSecuredResources() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllSecuredResources");
		query.executeUpdate();	
	}
	
	
}
