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

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.ViewResourcesMapping;
import org.anyframe.iam.admin.domain.ViewResourcesMappingPK;
import org.anyframe.iam.admin.viewresources.dao.ViewMappingDao;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("viewMappingDao")
public class ViewMappingDaoHibernateImpl extends IamGenericDaoHibernate<ViewResourcesMapping, ViewResourcesMappingPK>
		implements ViewMappingDao {

	public ViewMappingDaoHibernateImpl() {
		super(ViewResourcesMapping.class);
	}
	
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
		String type = StringUtil.null2str(viewResourceSearchVO.getType());
		String parentViewResourceId = StringUtil.null2str(viewResourceSearchVO.getParentViewResourceId());
		String systemName = StringUtil.null2str(viewResourceSearchVO.getSystemName());

		Object[] args = new Object[8];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "sidx=" + sidx;
		args[4] = "sord=" + sord;
		args[5] = "type=" + type;
		args[6] = "parentViewResourceId=" + parentViewResourceId;
		args[7] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findViewMappingList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countViewMappingList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List<ViewResourcesMapping> get(String viewResourceId) throws Exception {
		Object[] args = new Object[1];
		args[0] = "viewResourceId=" + viewResourceId;

		List<ViewResourcesMapping> resultList = this.getDynamicHibernateService().findList("findViewMappings", args);

		return resultList;
	}

	public ViewResourcesMapping delete(String viewResourceId) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("deleteMapping");
		query.setParameter("viewResourceId", viewResourceId);
		query.executeUpdate();

		return new ViewResourcesMapping();
	}
	
	public void removeAllViewResourceMapping() throws Exception{
		Query query = this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllViewResourceMapping");
		query.executeUpdate();
	}
}
