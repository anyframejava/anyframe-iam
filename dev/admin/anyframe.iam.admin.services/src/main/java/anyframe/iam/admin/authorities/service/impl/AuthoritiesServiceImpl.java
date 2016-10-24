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

package anyframe.iam.admin.authorities.service.impl;

import java.util.List;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.authorities.dao.AuthoritiesDao;
import anyframe.iam.admin.authorities.service.AuthoritiesService;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.AuthoritiesId;
import anyframe.iam.admin.vo.AuthoritySearchVO;

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

	public List getGroupList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getGroupList(authoritySearchVO);
	}

	public List getGroupIdList(AuthoritySearchVO authoritySearchVO) throws Exception {
		return this.authoritiesDao.getGroupIdList(authoritySearchVO);
	}

	public void removeAndSaveList(String[] groupId, String roleId) throws Exception {
		String currentTime = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");

		AuthoritySearchVO authoritySearchVO = new AuthoritySearchVO();
		authoritySearchVO.setType("G");
		authoritySearchVO.setRoleId(roleId);
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

	public void addUser(String[] userIds, String roleId) throws Exception {
		String currentTime = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");

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