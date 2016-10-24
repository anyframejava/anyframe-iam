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

package anyframe.iam.admin.users.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;

import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.authorities.dao.AuthoritiesDao;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.GroupsUsers;
import anyframe.iam.admin.domain.Users;
import anyframe.iam.admin.groupsusers.dao.GroupsUsersDao;
import anyframe.iam.admin.users.dao.UsersDao;
import anyframe.iam.admin.users.service.UsersService;
import anyframe.iam.admin.vo.UserSearchVO;

public class UsersServiceImpl extends GenericServiceImpl<Users, String> implements UsersService {
	UsersDao usersDao;

	GroupsUsersDao groupsUsersDao;

	AuthoritiesDao authoritiesDao;

	public UsersServiceImpl(UsersDao usersDao) {
		super(usersDao);
		this.usersDao = usersDao;
	}

	public void setGroupsUsersDao(GroupsUsersDao groupsUsersDao) {
		this.groupsUsersDao = groupsUsersDao;
	}

	public void setAuthoritiesDao(AuthoritiesDao authoritiesDao) {
		this.authoritiesDao = authoritiesDao;
	}

	public Page getList(UserSearchVO userSearchVO) throws Exception {
		return this.usersDao.getList(userSearchVO);
	}

	// 사용자 정보를 조회한다.
	public Users get(String userId) throws Exception {

		// 사용자 정보를 조회해온다.
		Users users = dao.get(userId);

		Set<GroupsUsers> setGroupsUsers1 = users.getGroupsUserses();

		// Users 객체의 attribute 중에서 LAZY 속성을 가지고 있는 attribute 를 초기화 해준다. (
		// Hibernate session 내에서 초기화를 해줘야 presentation layer에서 해당 값을 사용 할 수 있음)
		Iterator<GroupsUsers> itrGroupsUsers1 = setGroupsUsers1.iterator();

		if (itrGroupsUsers1.hasNext()) {
			GroupsUsers groupsUsers1 = itrGroupsUsers1.next();
			Groups groups1 = groupsUsers1.getGroups();

			Set<GroupsUsers> setGroupsUsers2 = groups1.getGroupsUserses();
			Iterator<GroupsUsers> itrGroupsUsers2 = setGroupsUsers2.iterator();

			if (itrGroupsUsers2.hasNext()) {
				GroupsUsers groupsUsers2 = itrGroupsUsers2.next();
				Hibernate.initialize(groupsUsers2.getGroups());
			}
		}

		return users;
	}

	// 사용자 정보를 등록한다.
	public Users save(Users users, Authorities[] authorities) throws Exception {

		// AUTHORITIES 테이블의 Role / User 매핑 정보를 등록
		if (authorities != null) {
			for (int i = 0; i < authorities.length; i++) {
				authoritiesDao.save(authorities[i]);
			}
		}

		// USERS 테이블의 User 정보 등록 GROUPS_USERS 테이블의 Group / User 매핑 정보 등록
		return usersDao.save(users);
	}

	// 사용자 정보를 수정한다.
	public Users update(Users users, GroupsUsers newGroupsUsers, GroupsUsers currentGroupsUsers,
			Authorities[] authorities) throws Exception {

		// GROUPS_USERS 테이블의 Group / User 매핑 정보 삭제
		if (currentGroupsUsers != null) {
			if (!currentGroupsUsers.getId().getGroupId().equals(newGroupsUsers.getId().getGroupId())) {
				groupsUsersDao.remove(currentGroupsUsers.getId());
			}
		}

		// AUTHORITIES 테이블의 Role / User 매핑 정보를 삭제
		authoritiesDao.remove(users.getUserId(), "SUBJECT");

		// AUTHORITIES 테이블의 Role / User 매핑 정보를 등록
		if (authorities != null) {
			for (int i = 0; i < authorities.length; i++) {
				authoritiesDao.save(authorities[i]);
			}
		}

		// USERS 테이블의 User 정보 수정
		return usersDao.save(users);
	}

	// 사용자 정보를 삭제 한다.
	public void delete(String[] userId) throws Exception {
		if (userId != null) {
			for (int i = 0; i < userId.length; i++) {
				authoritiesDao.remove(userId[i], "SUBJECT");
				remove(userId[i]);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List findUserByName(String userName) throws Exception {
		return usersDao.findUserByName(userName);
	}
}