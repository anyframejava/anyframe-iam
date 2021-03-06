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

package org.anyframe.iam.admin.restrictedtimes.dao.impl;

import java.util.List;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.RestrictedTimes;
import org.anyframe.iam.admin.domain.TempRestrictedTimes;
import org.anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesDao;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.hibernate.Query;

public class RestrictedTimesDaoHibernateImpl extends IamGenericDaoHibernate<RestrictedTimes, String> implements
		RestrictedTimesDao {

	public RestrictedTimesDaoHibernateImpl() {
		super(RestrictedTimes.class);
	}

	public Page getList(RestrictedTimesSearchVO restrictedTimesSearchVO) throws Exception {
		int pageIndex = restrictedTimesSearchVO.getPage();
		int pageSize = restrictedTimesSearchVO.getRows();
		String sidx = StringUtil.null2str(restrictedTimesSearchVO.getSidx());
		String sord = StringUtil.null2str(restrictedTimesSearchVO.getSord());
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String searchCondition = StringUtil.null2str(restrictedTimesSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(restrictedTimesSearchVO.getSearchKeyword());
		String searchType = StringUtil.null2str(restrictedTimesSearchVO.getSearchtype());
		String systemName = StringUtil.null2str(restrictedTimesSearchVO.getSystemName());

		Object[] args = new Object[7];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[4] = "sidx=" + sidx;
		args[5] = "sord=" + sord;
		args[6] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findRestrictedTimesList", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countRestrictedTimesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}
	
	@SuppressWarnings("unchecked")
	public List<TempRestrictedTimes> makeAllTempRestrictedTimesList() throws Exception {
		
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("makeAllTempTimesList");

		return query.list();
	}
	
	public void removeAllRestrictedTimes() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllRestrictedTimes");
		query.executeUpdate();	
	}
}
