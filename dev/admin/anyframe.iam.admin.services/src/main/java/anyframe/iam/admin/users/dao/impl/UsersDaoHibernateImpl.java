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

package anyframe.iam.admin.users.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.TempUsers;
import anyframe.iam.admin.domain.Users;
import anyframe.iam.admin.users.dao.UsersDao;
import anyframe.iam.admin.vo.UserSearchVO;

@Repository("usersDao")
public class UsersDaoHibernateImpl extends IamGenericDaoHibernate<Users, String> implements UsersDao {

	public UsersDaoHibernateImpl() {
		super(Users.class);
	}

	public Page getList(UserSearchVO userSearchVO) throws Exception {
		int pageIndex = userSearchVO.getPage();
		int pageSize = userSearchVO.getRows();
		if (pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String sidx = StringUtil.null2str(userSearchVO.getSidx());
		String sord = StringUtil.null2str(userSearchVO.getSord());
		String searchCondition = StringUtil.null2str(userSearchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(userSearchVO.getSearchKeyword());
		String isNumeric = StringUtil.isNumeric(searchKeyword) ? "true" : "false";
		String groupId = StringUtil.null2str(userSearchVO.getGroupId());

		Object[] args = new Object[7];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + groupId + "";
		args[3] = "isNumeric=" + isNumeric;
		args[4] = "groupId=" + groupId;
		args[5] = "sidx=" + sidx;
		args[6] = "sord=" + sord;

		@SuppressWarnings("unchecked")
		List resultList = this.getDynamicHibernateService().findList("findUsersList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countUsersList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	public List findUserByName(String userName) throws Exception {
		Object[] arg = new Object[1];
		arg[0] = "userName=%" + userName + "%";

		return this.getDynamicHibernateService().findList("findUserByName", arg);
	}
	
	@SuppressWarnings("unchecked")
	public List<TempUsers> makeAllTempUsersList() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("makeAllTempUsersList");

		return query.list();
	}
	
	public void removeAllUsers() throws Exception{
		Query query = (Query) this.getSessionFactory().getCurrentSession().getNamedQuery("removeAllUsers");
		query.executeUpdate();	
	}
}
