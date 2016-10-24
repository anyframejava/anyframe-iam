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

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.RestrictedTimesResources;
import anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import anyframe.iam.admin.restrictedtimes.dao.RestrictedTimesResourcesDao;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

public class RestrictedTimesResourcesDaoHibernateImpl extends
		IamGenericDaoHibernate<RestrictedTimesResources, RestrictedTimesResourcesId> implements
		RestrictedTimesResourcesDao {

	public RestrictedTimesResourcesDaoHibernateImpl() {
		super(RestrictedTimesResources.class);
	}

	public Page getTimeResourceList(RestrictedTimesSearchVO searchVO) throws Exception {

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
		List resultList = this.getDynamicHibernateService().findList("findTimeResourceList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countTimeResourceList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List findRoleListByTime(String timeId) throws Exception {
		Object[] args = new Object[1];
		args[0] = "timeId=" + timeId;
		List resultList = this.getDynamicHibernateService().findList("findRoleListByTime", args);
		return resultList;
	}

	public Page findResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception {
		int pageIndex = searchVO.getPage();
		int pageSize = searchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");

		String timeId = StringUtil.null2str(searchVO.getSearchKeyword());
		String sidx = StringUtil.null2str(searchVO.getSidx());
		String sord = StringUtil.null2str(searchVO.getSord());
		String systemName = StringUtil.null2str(searchVO.getSystemName());

		Object[] args = new Object[4];
		args[0] = "timeId=" + timeId;
		args[1] = "sidx=" + sidx;
		args[2] = "sord=" + sord;
		args[3] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findResourceListByTime", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countResourceListByTime", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	public Page findUnmappedResourceListByTime(RestrictedTimesSearchVO searchVO) throws Exception {
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
		String timeId = StringUtil.null2str(searchVO.getTimeId());
		String systemName = StringUtil.null2str(searchVO.getSystemName());

		Object[] args = new Object[8];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "searchType=" + searchType;
		args[4] = "timeId=" + timeId;
		args[5] = "sidx=" + sidx;
		args[6] = "sord=" + sord;
		args[7] = "systemName=" + systemName;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findUnmappedResourceListByTime", args, pageIndex,
				pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countUnmappedResourceListByTime", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}
}
