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

package anyframe.iam.admin.restrictedtimes.dao.impl;

import java.util.List;

import org.hibernate.Query;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.TimesResourcesExclusion;
import anyframe.iam.admin.domain.TimesResourcesExclusionId;
import anyframe.iam.admin.restrictedtimes.dao.TimesResourcesExclusionDao;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

public class TimesResourcesExclusionDaoHibernateImpl extends
		IamGenericDaoHibernate<TimesResourcesExclusion, TimesResourcesExclusionId> implements
		TimesResourcesExclusionDao {

	public TimesResourcesExclusionDaoHibernateImpl() {
		super(TimesResourcesExclusion.class);
	}

	public Page getTimeExclusionList(RestrictedTimesSearchVO searchVO) throws Exception {

		int pageIndex = searchVO.getPage();
		int pageSize = searchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");

		String sidx = StringUtil.null2str(searchVO.getSidx());
		String sord = StringUtil.null2str(searchVO.getSord());
		String searchCondition = StringUtil.null2str(searchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(searchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(searchVO.getSearchtype());
		String systemName = StringUtil.null2str(searchVO.getSystemName());

		Object[] args = new Object[8];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[5] = "sidx=" + sidx;
		args[6] = "sord=" + sord;
		args[7] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService()
				.findList("findTimeExclusionList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countTimeExclusionList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List findRoleListByTimeResource(String timeId, String resourceId) throws Exception {
		Object[] args = new Object[2];
		args[0] = "timeId=" + timeId;
		args[1] = "resourceId=" + resourceId;
		List resultList = this.getDynamicHibernateService().findList("findRoleListByTimeResource", args);
		return resultList;
	}

	public void removeTimesExclusionByTimeResource(String timeId, String resourceId) throws Exception {
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery(
				"removeTimesExclusionByTimeResource");
		query.setParameter("timeId", timeId);
		query.setParameter("resourceId", resourceId);
		query.executeUpdate();
	}
	
	public void removeAllTimesResourcesExclusion() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery(
		"removeAllTimesResourcesExclusion");
		query.executeUpdate();
	}
}
