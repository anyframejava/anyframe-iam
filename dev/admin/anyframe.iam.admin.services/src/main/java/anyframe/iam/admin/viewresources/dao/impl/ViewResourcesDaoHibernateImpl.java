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

package anyframe.iam.admin.viewresources.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.dao.ViewResourcesDao;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

@Repository("viewResourcesDao")
public class ViewResourcesDaoHibernateImpl extends IamGenericDaoHibernate<ViewResource, String> implements
		ViewResourcesDao {

	public ViewResourcesDaoHibernateImpl() {
		super(ViewResource.class);
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
		Object[] args = new Object[5];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "sidx=" + sidx;
		args[4] = "sord=" + sord;

		List resultList = this.getDynamicHibernateService().findList("findViewResourceList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countViewResourceList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

}
