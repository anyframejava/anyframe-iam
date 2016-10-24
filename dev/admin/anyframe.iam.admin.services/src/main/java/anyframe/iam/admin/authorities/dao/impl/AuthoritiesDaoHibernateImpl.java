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

package anyframe.iam.admin.authorities.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.authorities.dao.AuthoritiesDao;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.AuthoritiesId;
import anyframe.iam.admin.vo.AuthoritySearchVO;

@Repository("authoritiesDao")
public class AuthoritiesDaoHibernateImpl extends
		IamGenericDaoHibernate<Authorities, AuthoritiesId> implements
		AuthoritiesDao {

	public AuthoritiesDaoHibernateImpl() {
		super(Authorities.class);
	}

	public Page getList(AuthoritySearchVO authoritySearchVO) throws Exception {
		int pageIndex = authoritySearchVO.getPage();
		int pageSize = authoritySearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(authoritySearchVO.getSidx());
		String sord = StringUtil.null2str(authoritySearchVO.getSord());
		String searchCondition = StringUtil.null2str(authoritySearchVO
				.getSearchCondition());
		String searchKeyword = StringUtil.null2str(authoritySearchVO
				.getSearchKeyword());
		String isNumeric = StringUtil.isNumeric(searchKeyword) ? "true"
				: "false";
		String roleId = StringUtil.null2str(authoritySearchVO.getRoleId());

		Object[] args = new Object[7];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "isNumeric=" + isNumeric;
		args[4] = "roleId=" + roleId;
		args[5] = "sidx=" + sidx;
		args[6] = "sord=" + sord;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList(
				"findAuthoritiesList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find(
				"countAuthoritiesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(),
				pageUnit, pageSize);
		return resultPage;
	}

	public Page getExistList(AuthoritySearchVO authoritySearchVO)
			throws Exception {
		int pageIndex = authoritySearchVO.getPage();
		int pageSize = authoritySearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(authoritySearchVO.getSidx());
		String sord = StringUtil.null2str(authoritySearchVO.getSord());
		String searchCondition = StringUtil.null2str(authoritySearchVO
				.getSearchCondition());
		String searchKeyword = StringUtil.null2str(authoritySearchVO
				.getSearchKeyword());
		String roleId = StringUtil.null2str(authoritySearchVO.getRoleId());

		Object[] args = new Object[6];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "sidx=" + sidx;
		args[4] = "sord=" + sord;
		args[5] = "roleId=" + roleId;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList(
				"findExistAuthoritiesList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find(
				"countExistAuthoritiesList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(),
				pageUnit, pageSize);
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List getGroupList(AuthoritySearchVO authoritySearchVO)
			throws Exception {
		String type = StringUtil.null2str(authoritySearchVO.getType());
		String roleId = StringUtil.null2str(authoritySearchVO.getRoleId());
		Object[] args = new Object[2];
		args[0] = "type=" + type;
		args[1] = "roleId=" + roleId;

		List resultList = this.getDynamicHibernateService().findList(
				"findAuthorityGroupList", args);

		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List getGroupIdList(AuthoritySearchVO authoritySearchVO)
			throws Exception {
		String type = StringUtil.null2str(authoritySearchVO.getType());
		String roleId = StringUtil.null2str(authoritySearchVO.getRoleId());
		Object[] args = new Object[2];
		args[0] = "type=" + type;
		args[1] = "roleId=" + roleId;

		List resultList = this.getDynamicHibernateService().findList(
				"findGroupIdList", args);

		return resultList;
	}

	public void remove(String id, String type) throws Exception {
		Query query = null;

		query = (Query) this.getSessionFactory().getCurrentSession()
				.getNamedQuery("removeAuthoritiesBySubjectId");
		query.setParameter("subjectId", id);
		query.executeUpdate();

	}

	public void removeAllAuthorities() throws Exception {

		Query query = (Query) this.getSessionFactory().getCurrentSession()
				.getNamedQuery("removeAllAuthorities");
		query.executeUpdate();
	}

}
