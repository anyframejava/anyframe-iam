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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import anyframe.common.Page;
import anyframe.iam.admin.common.IAMException;
import anyframe.iam.admin.domain.Authorities;
import anyframe.iam.admin.domain.AuthoritiesId;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.GroupsUsers;
import anyframe.iam.admin.domain.GroupsUsersId;
import anyframe.iam.admin.domain.Users;
import anyframe.iam.admin.users.service.UsersService;
import anyframe.iam.admin.vo.UserSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class UsersServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("usersService")
	UsersService usersService;

	public Users makeDomain() {
		return makeDomain("GOGOGO");
	}

	public Users makeDomain(String userId) {
		Users domain = new Users();
		domain.setUserId(userId);
		domain.setUserName("test user");
		domain.setPassword("test123");
		domain.setEnabled("Y");

		GroupsUsers groupsUsers = new GroupsUsers();
		GroupsUsersId groupsUsersId = new GroupsUsersId();
		groupsUsersId.setGroupId("GRP-0001");
		groupsUsersId.setUserId(userId);
		Groups group = new Groups();
		group.setGroupId("GRP-0001");
		group.setGroupName("삼성SDS");

		groupsUsers.setId(groupsUsersId);
		groupsUsers.setUsers(domain);
		groupsUsers.setGroups(group);

		Set<GroupsUsers> groupsUserses = new HashSet<GroupsUsers>();
		groupsUserses.add(groupsUsers);

		domain.setGroupsUserses(groupsUserses);

		return domain;
	}

	public void checkResult(Users domain, Users resultDomain) {
		assertNotNull(resultDomain);
		assertEquals(domain.getUserId(), resultDomain.getUserId());
		assertEquals(domain.getUserName(), resultDomain.getUserName());
		assertEquals(domain.getPassword(), resultDomain.getPassword());

	}

	@Test
	public void testInsertAndGetUsers() throws Exception {

		// make domain
		Users domain = makeDomain();
		Authorities[] authorities = new Authorities[1];
		authorities[0] = new Authorities();
		AuthoritiesId id = new AuthoritiesId();
		id.setRoleId("ROLE_USER");
		id.setSubjectId("GOGOGO");
		authorities[0].setId(id);
		authorities[0].setType("U");

		// insert
		usersService.save(domain, authorities);

		// select
		Users resultDomain = usersService.get(domain.getUserId());

		// check
		checkResult(domain, resultDomain);
	}

	@Test
	public void testUpdate() throws Exception {

		// make domain
		Users domain = makeDomain();
		Authorities[] authorities1 = null;

		// insert
		usersService.save(domain, authorities1);

		// update
		Users users = new Users();
		users.setUserId("GOGOGO");
		users.setUserName("TESTUSER123");

		Users gettedUsers = usersService.get(users.getUserId());
		users.setPassword(gettedUsers.getPassword());

		Authorities[] authorities = null;

		GroupsUsers newGroupUsers = new GroupsUsers();
		GroupsUsersId newGroupsUsersId = new GroupsUsersId();
		newGroupsUsersId.setGroupId("GRP-0002");
		newGroupsUsersId.setUserId(users.getUserId());
		newGroupUsers.setId(newGroupsUsersId);
		newGroupUsers.setCreateDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));
		newGroupUsers.setModifyDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));

		GroupsUsers currentGroupUsers = null;
		GroupsUsersId currentGroupsUsersId = null;

		if (gettedUsers.getGroupsUserses().iterator().hasNext()) {
			if (gettedUsers.getGroupsUserses().iterator().next().getGroups().getGroupsUserses().iterator().hasNext()) {
				currentGroupUsers = new GroupsUsers();
				currentGroupsUsersId = new GroupsUsersId();

				currentGroupsUsersId.setGroupId(gettedUsers.getGroupsUserses().iterator().next().getGroups()
						.getGroupsUserses().iterator().next().getId().getGroupId());
				currentGroupsUsersId.setUserId(users.getUserId());
			}
		}

		currentGroupUsers.setId(currentGroupsUsersId);

		usersService.update(users, newGroupUsers, currentGroupUsers, authorities);

		Users resultDomain = usersService.get(users.getUserId());
		assertEquals(users.getUserName(), resultDomain.getUserName());
		String resultGroupId = resultDomain.getGroupsUserses().iterator().next().getGroups().getGroupId();
		assertEquals(newGroupsUsersId.getGroupId(), resultGroupId);
	}

	@Test
	public void testGetList() throws Exception {

		// make domain
		Users domain = makeDomain();
		Authorities[] authorities = null;

		// insert
		usersService.save(domain, authorities);

		// find list
		UserSearchVO searchVO = new UserSearchVO();
		searchVO.setGroupId("GRP-0001");
		searchVO.setSearchKeyword("test user");
		searchVO.setSearchCondition("userName");
		Page resultPage = usersService.getList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());

		Iterator iter = resultPage.getList().iterator();
		Map resultMap = (Map) iter.next();
		assertEquals(domain.getUserId(), resultMap.get("userId"));
	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		Users domain = makeDomain();
		Authorities[] authorities = null;

		// insert
		usersService.save(domain, authorities);

		// delete
		String[] userId = new String[1];
		userId[0] = "GOGOGO";
		usersService.delete(userId);

		// check
		try {
			usersService.get(userId[0]);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof IAMException);
			assertEquals("Fail to compare exception message.",
					"Users details of  specified users identifier not displayed", e.getMessage());
		}
	}

	@Test
	public void testFindUserByName() throws Exception {

		// make domain
		Users domain = makeDomain();
		Authorities[] authorities = null;

		// insert
		usersService.save(domain, authorities);

		// find user
		String userName = domain.getUserName();
		List resultList = usersService.findUserByName(userName);

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(1, resultList.size());

		Users resultDomain = (Users) resultList.get(0);
		checkResult(domain, resultDomain);
	}
}
