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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.anyframe.exception.BaseException;
import org.anyframe.iam.admin.authorities.service.AuthoritiesService;
import org.anyframe.iam.admin.domain.Authorities;
import org.anyframe.iam.admin.domain.AuthoritiesId;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.vo.AuthoritySearchVO;
import org.anyframe.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class AuthoritiesServiceTest {
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("authoritiesService")
	AuthoritiesService authoritiesService;

	@Test
	public void testAddUser() throws Exception {

		// make data
		String[] userIds = new String[2];
		userIds[0] = "bearbin.song";
		userIds[1] = "zephyr.lim";
		String roleId = "ROLE_USER";

		// save
		authoritiesService.addUser(userIds, roleId);

		// check
		AuthoritiesId id1 = new AuthoritiesId();
		id1.setRoleId("ROLE_USER");
		id1.setSubjectId("bearbin.song");
		Authorities resultDomain1 = authoritiesService.get(id1);

		assertEquals("ROLE_USER", resultDomain1.getId().getRoleId());
		assertEquals("bearbin.song", resultDomain1.getId().getSubjectId());

		AuthoritiesId id2 = new AuthoritiesId();
		id2.setRoleId("ROLE_USER");
		id2.setSubjectId("zephyr.lim");
		Authorities resultDomain2 = authoritiesService.get(id2);

		assertEquals("ROLE_USER", resultDomain2.getId().getRoleId());
		assertEquals("zephyr.lim", resultDomain2.getId().getSubjectId());
	}

	@Test
	public void testDeleteUser() throws Exception {

		// make data
		String[] userIds = new String[2];
		userIds[0] = "bearbin.song";
		userIds[1] = "zephyr.lim";
		String roleId = "ROLE_USER";

		// save
		authoritiesService.addUser(userIds, roleId);

		// delete
		authoritiesService.deleteUser(userIds, roleId);

		// check
		AuthoritiesId id1 = new AuthoritiesId();
		id1.setRoleId("ROLE_USER");
		id1.setSubjectId("bearbin.song");
		try {
			Authorities resultDomain1 = authoritiesService.get(id1);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class org.anyframe.iam.admin.domain.Authorities' object with id 'AuthoritiesId [roleId='"
							+ id1.getRoleId() + "', subjectId='" + id1.getSubjectId() + "']' not found", e.getMessage());
		}

		AuthoritiesId id2 = new AuthoritiesId();
		id2.setRoleId("ROLE_USER");
		id2.setSubjectId("zephyr.lim");
		try {
			Authorities resultDomain2 = authoritiesService.get(id2);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class org.anyframe.iam.admin.domain.Authorities' object with id 'AuthoritiesId [roleId='"
							+ id2.getRoleId() + "', subjectId='" + id2.getSubjectId() + "']' not found", e.getMessage());
		}

	}

	@Test
	public void testRemoveAndSaveList() throws Exception {

		// make data
		String[] groupId = new String[3];
		groupId[0] = "GRP-0041";
		groupId[1] = "GRP-0014";
		groupId[2] = "GRP-0004";
		String roleId = "ROLE_B";

		// remove and save
		authoritiesService.removeAndSaveList(groupId, roleId);

		// check
		AuthoritiesId id1 = new AuthoritiesId();
		id1.setRoleId("ROLE_B");
		id1.setSubjectId("GRP-0012");
		try {
			authoritiesService.get(id1);
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class org.anyframe.iam.admin.domain.Authorities' object with id 'AuthoritiesId [roleId='"
							+ id1.getRoleId() + "', subjectId='" + id1.getSubjectId() + "']' not found", e.getMessage());
		}

		AuthoritiesId id2 = new AuthoritiesId();
		id2.setRoleId("ROLE_B");
		id2.setSubjectId(groupId[0]);
		assertTrue(authoritiesService.exists(id2));

		AuthoritiesId id3 = new AuthoritiesId();
		id3.setRoleId("ROLE_B");
		id3.setSubjectId(groupId[1]);
		assertTrue(authoritiesService.exists(id2));

		AuthoritiesId id4 = new AuthoritiesId();
		id4.setRoleId("ROLE_B");
		id4.setSubjectId(groupId[2]);
		assertTrue(authoritiesService.exists(id2));
	}

	@Test
	public void testGetGroupIdList() throws Exception {

		// make data
		String[] groupId = new String[3];
		groupId[0] = "GRP-0004";
		groupId[1] = "GRP-0014";
		groupId[2] = "GRP-0041";
		String roleId = "ROLE_B";

		// remove and save
		authoritiesService.removeAndSaveList(groupId, roleId);

		// get group id list
		AuthoritySearchVO searchVO = new AuthoritySearchVO();
		searchVO.setType("G");
		searchVO.setRoleId("ROLE_B");
		List resultList = authoritiesService.getGroupIdList(searchVO);

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);

		for (int i = 0; i < resultList.size(); i++) {
			String resultGroupId = (String) resultList.get(i);
			assertEquals(groupId[i], resultGroupId);
		}
	}

	@Test
	public void testGetGroupList() throws Exception {

		// make data
		String[] groupId = new String[3];
		groupId[0] = "GRP-0004";
		groupId[1] = "GRP-0014";
		groupId[2] = "GRP-0041";
		String roleId = "ROLE_B";

		// remove and save
		authoritiesService.removeAndSaveList(groupId, roleId);

		// get group list
		AuthoritySearchVO searchVO = new AuthoritySearchVO();
		searchVO.setRoleId("ROLE_B");
		List<Groups> resultList = authoritiesService.getGroupList(searchVO);

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);

		for (int i = 0; i < resultList.size(); i++) {
			String resultGroupId = (String) resultList.get(i).getGroupId();
			assertEquals(groupId[i], resultGroupId);
		}
	}

	@Test
	public void testGetExistList() throws Exception {

		// make data
		String[] userIds = new String[2];
		userIds[0] = "bearbin.song";
		userIds[1] = "zephyr.lim";
		String roleId = "IS_AUTHENTICATED_REMEMBERED";

		// save
		authoritiesService.addUser(userIds, roleId);

		// find list
		AuthoritySearchVO searchVO = new AuthoritySearchVO();
		searchVO.setRoleId("IS_AUTHENTICATED_REMEMBERED");
		Page resultPage = authoritiesService.getExistList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(2, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		String resultUserId1 = (String) ((Map) iter.next()).get("userId");
		assertEquals(userIds[1], resultUserId1);

		String resultUserId2 = (String) ((Map) iter.next()).get("userId");
		assertEquals(userIds[0], resultUserId2);
	}

	@Test
	public void testGetList() throws Exception {

		// find list
		AuthoritySearchVO searchVO = new AuthoritySearchVO();
		searchVO.setSearchKeyword("Choi");
		searchVO.setSearchCondition("userName");
		Page resultPage = authoritiesService.getList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(2, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		String resultUserId1 = (String) ((Map) iter.next()).get("userId");
		assertEquals("youngnam06.choi", resultUserId1);

		String resultUserId2 = (String) ((Map) iter.next()).get("userId");
		assertEquals("insuun.choi", resultUserId2);
	}
}
