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

package org.anyframe.iam.admin.authorities.service.impl;

import java.util.List;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.authorities.dao.AuthoritiesDao;
import org.anyframe.iam.admin.authorities.service.AuthoritiesService;
import org.anyframe.iam.admin.domain.Authorities;
import org.anyframe.iam.admin.domain.AuthoritiesId;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.vo.AuthoritySearchVO;
import org.anyframe.pagination.Page;
import org.anyframe.util.DateUtil;

public class AuthoritiesServiceImpl extends GenericServiceImpl<Authorities, AuthoritiesId> implements
		AuthoritiesService {
	AuthoritiesDao authoritiesDao;

	public AuthoritiesServiceImpl(AuthoritiesDao authoritiesDao) {
		super(authoritiesDao);
		this.authoritiesDao = authoritiesDao;
	}

	public Page getList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getList(authoritySearchVO);
	}

	public Page getExistList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getExistList(authoritySearchVO);
	}

	public List<Groups> getGroupList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getGroupList(authoritySearchVO);
	}

	public List<String> getGroupIdList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getGroupIdList(authoritySearchVO);
	}

	public void removeAndSaveList(String[] groupId, String roleId) throws Exception {
		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");

		AuthoritySearchVO authoritySearchVO = new AuthoritySearchVO();
		authoritySearchVO.setType("G");
		authoritySearchVO.setRoleId(roleId);
		@SuppressWarnings("unchecked")
		List gettedList = getGroupIdList(authoritySearchVO);

		for (int j = 0; j < gettedList.size(); j++) {
			AuthoritiesId authoritiesId = new AuthoritiesId();
			authoritiesId.setSubjectId((String) gettedList.get(j));
			authoritiesId.setRoleId(roleId);
			authoritiesDao.remove(authoritiesId);
		}

		if (groupId != null) {
			for (int i = 0; i < groupId.length; i++) {
				Authorities authorities = new Authorities();
				authorities.setCreateDate(currentTime);
				authorities.setType("G");
				AuthoritiesId newAthoritiesID = new AuthoritiesId();
				newAthoritiesID.setSubjectId(groupId[i]);
				newAthoritiesID.setRoleId(roleId);
				authorities.setId(newAthoritiesID);
				authoritiesDao.save(authorities);
			}
		}
	}
	
	public void removeAllAuthorities() throws Exception{
		authoritiesDao.removeAllAuthorities();
	}

	public void addUser(String[] userIds, String roleId) throws Exception {
		String currentTime = DateUtil.getCurrentTime("yyyyMMdd");

		if (userIds != null) {
			for (int i = 0; i < userIds.length; i++) {
				Authorities authorities = new Authorities();
				AuthoritiesId authoritiesId = new AuthoritiesId();
				authorities.setCreateDate(currentTime);
				authoritiesId.setRoleId(roleId);
				authoritiesId.setSubjectId(userIds[i]);
				authorities.setId(authoritiesId);
				authorities.setType("U");
				authoritiesDao.save(authorities);
			}
		}
	}

	public void deleteUser(String[] userIds, String roleId) throws Exception {

		if (userIds != null) {
			for (int i = 0; i < userIds.length; i++) {
				AuthoritiesId authoritiesId = new AuthoritiesId();
				authoritiesId.setRoleId(roleId);
				authoritiesId.setSubjectId(userIds[i]);
				remove(authoritiesId);
			}
		}

	}
}