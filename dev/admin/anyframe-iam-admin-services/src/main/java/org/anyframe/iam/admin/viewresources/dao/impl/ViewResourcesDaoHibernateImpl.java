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

package org.anyframe.iam.admin.viewresources.dao.impl;

import java.util.List;
import java.util.Map;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.TempViewResources;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.viewresources.dao.ViewResourcesDao;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository("viewResourcesDao")
public class ViewResourcesDaoHibernateImpl extends IamGenericDaoHibernate<ViewResource, String> implements
		ViewResourcesDao {

	public ViewResourcesDaoHibernateImpl() {
		super(ViewResource.class);
	}

	@SuppressWarnings("unchecked")
	public Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception {
		int pageIndex = viewResourceSearchVO.getPage();
		int pageSize = viewResourceSearchVO.getRows();
		String sidx = StringUtil.null2str(viewResourceSearchVO.getSidx());
		String sord = StringUtil.null2str(viewResourceSearchVO.getSord());
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String searchCondition = StringUtil.null2str(viewResourceSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(viewResourceSearchVO.getSearchKeyword());
		String parentViewResourceId = StringUtil.null2str(viewResourceSearchVO.getParentViewResourceId());
		String systemName = StringUtil.null2str(viewResourceSearchVO.getSystemName());
		
		Object[] args = new Object[7];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "sidx=" + sidx;
		args[4] = "sord=" + sord;
		args[5] = "parentViewResourceId=" + parentViewResourceId;
		args[6] = "systemName=" + systemName;
		
		List resultList = this.getDynamicHibernateService().findList("findViewResourceList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countViewResourceList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}
	
	@SuppressWarnings("unchecked")
	public List<IamTree> getViewTree(String parentNode) throws Exception{
		
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getViewTreeData");
		query.setParameter("parentNode", parentNode);
		List<IamTree> list = query.list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<IamTree> getRootNodeOfViews() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRootNodeOfView");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<IamTree> getRootNodeOfViewsWithSystemName(String systemName) throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("getRootNodeOfView");
		StringBuffer replaceSQL = new StringBuffer();
		
		if(!"".equals("systemName"))
			replaceSQL.append(" AND B.SYSTEM_NAME = '" + systemName + "'");
		
		String queryString = query.getQueryString();
		queryString = queryString.replace("--replaceSQL", replaceSQL.toString());
		
		SQLQuery replacedQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString);
		replacedQuery.addEntity(IamTree.class);
		
		List<IamTree> resultList = replacedQuery.list();
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getViewHierarchy(String parentNode) throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("countViewList");
		query.setParameter("parentView", parentNode);
		List<String> list = query.list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String getViewNameList(String keyword) throws Exception{
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		Object[] args = new Object[1];
		args[0] = "keyword=" + keyword + "%";
		List list = this.getDynamicHibernateService().findList("findViewNameList", args);
		
		StringBuffer viewNameList = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++)
			viewNameList.append(((Map) list.get(i)).get("viewName") + "\n");
		
		return viewNameList.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getViewNameListWithSystemName(String keyword, String systemName) throws Exception{
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		systemName = StringUtil.null2str(systemName);
		
		Object[] args = new Object[2];
		args[0] = "keyword=" + keyword + "%";
		args[1] = "systemName=" + systemName;
		List list = this.getDynamicHibernateService().findList("findViewNameList", args);
		
		StringBuffer viewNameList = new StringBuffer();
		for(int i = 0 ; i < list.size() ; i++)
			viewNameList.append(((Map) list.get(i)).get("viewName") + "\n");
		
		return viewNameList.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public String getViewResourceIdByViewName(String viewName) throws Exception{
		Object[] args = new Object[1];
		String changedViewName = viewName.toLowerCase();
		args[0] = "viewName=" + changedViewName;
		List<ViewResource> list = this.getDynamicHibernateService().findList("findViewIdByViewName", args);
	
		if(list.size() == 0)
			return "";
		
		return list.get(0).getViewResourceId();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TempViewResources> makeAllTempViewList() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("makeAllTempViewList");

		return query.list();
	}
	
	public void removeAllViewResources() throws Exception{
		Query query = this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllViewResources");
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public boolean isExistName(String viewName) throws Exception{
		Object[] args = new Object[1];
		args[0] = "viewName=" + viewName;
		List<ViewResource> list = this.getDynamicHibernateService().findList("isExistName", args);
		
		if(list.size() == 0)
			return false;
		
		return true;
	}
}
