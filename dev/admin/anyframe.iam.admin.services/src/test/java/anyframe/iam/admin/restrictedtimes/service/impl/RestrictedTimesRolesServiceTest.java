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

package anyframe.iam.admin.restrictedtimes.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import anyframe.common.Page;
import anyframe.common.exception.BaseException;
import anyframe.iam.admin.domain.RestrictedTimesRoles;
import anyframe.iam.admin.domain.RestrictedTimesRolesId;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class RestrictedTimesRolesServiceTest {
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("restrictedTimesRolesService")
	RestrictedTimesRolesService restrictedTimesRolesService;

	public List<RestrictedTimesRoles> makeDomain() {
		RestrictedTimesRoles domain1 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id1 = new RestrictedTimesRolesId();
		id1.setRoleId("ROLE_A");
		id1.setTimeId("TIME-00004");
		domain1.setId(id1);

		RestrictedTimesRoles domain2 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id2 = new RestrictedTimesRolesId();
		id2.setRoleId("ROLE_B");
		id2.setTimeId("TIME-00004");
		domain2.setId(id2);

		List<RestrictedTimesRoles> list = new ArrayList<RestrictedTimesRoles>();

		list.add(domain1);
		list.add(domain2);

		return list;
	}

	public List<RestrictedTimesRolesId> makeDomainId() {
		RestrictedTimesRolesId id1 = new RestrictedTimesRolesId();
		id1.setRoleId("ROLE_A");
		id1.setTimeId("TIME-00004");

		RestrictedTimesRolesId id2 = new RestrictedTimesRolesId();
		id2.setRoleId("ROLE_B");
		id2.setTimeId("TIME-00004");

		List<RestrictedTimesRolesId> list = new ArrayList();
		list.add(id1);
		list.add(id2);

		return list;
	}

	@Test
	public void testSaveTimeRoles() throws Exception {

		// make data
		RestrictedTimesRoles domain1 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id1 = new RestrictedTimesRolesId();
		id1.setRoleId("ROLE_A");
		id1.setTimeId("TIME-00004");
		domain1.setId(id1);

		RestrictedTimesRoles domain2 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id2 = new RestrictedTimesRolesId();
		id2.setRoleId("ROLE_B");
		id2.setTimeId("TIME-00005");
		domain2.setId(id2);

		List<RestrictedTimesRoles> list = new ArrayList<RestrictedTimesRoles>();

		list.add(domain1);
		list.add(domain2);

		// save
		restrictedTimesRolesService.saveTimeRoles(list);

		// check
		RestrictedTimesRoles resultDomain1 = restrictedTimesRolesService.get(id1);
		assertEquals(domain1.getId().getRoleId(), resultDomain1.getId().getRoleId());
		assertEquals(domain1.getId().getTimeId(), resultDomain1.getId().getTimeId());

		RestrictedTimesRoles resultDomain2 = restrictedTimesRolesService.get(id2);
		assertEquals(domain2.getId().getRoleId(), resultDomain2.getId().getRoleId());
		assertEquals(domain2.getId().getTimeId(), resultDomain2.getId().getTimeId());
	}

	@Test
	public void testDelete() throws Exception {

		// make and save data
		List<RestrictedTimesRoles> list = makeDomain();
		restrictedTimesRolesService.saveTimeRoles(list);

		// delete
		List<RestrictedTimesRolesId> idList = makeDomainId();
		restrictedTimesRolesService.delete(idList);

		// check
		try {
			restrictedTimesRolesService.get(idList.get(0));
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimesRoles' object with id 'RestrictedTimesRolesId [timeId='"
							+ idList.get(0).getTimeId() + "', roleId='" + idList.get(0).getRoleId() + "']' not found",
					e.getMessage());
		}

		try {
			restrictedTimesRolesService.get(idList.get(1));
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimesRoles' object with id 'RestrictedTimesRolesId [timeId='"
							+ idList.get(1).getTimeId() + "', roleId='" + idList.get(1).getRoleId() + "']' not found",
					e.getMessage());
		}
	}

	@Test
	public void testRemoveTimesRolesByTime() throws Exception {

		// make data
		RestrictedTimesRoles domain1 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id1 = new RestrictedTimesRolesId();
		id1.setRoleId("ROLE_A");
		id1.setTimeId("TIME-00004");
		domain1.setId(id1);

		RestrictedTimesRoles domain2 = new RestrictedTimesRoles();
		RestrictedTimesRolesId id2 = new RestrictedTimesRolesId();
		id2.setRoleId("ROLE_B");
		id2.setTimeId("TIME-00005");
		domain2.setId(id2);

		List<RestrictedTimesRoles> list = new ArrayList<RestrictedTimesRoles>();

		list.add(domain1);
		list.add(domain2);

		// save
		restrictedTimesRolesService.saveTimeRoles(list);

		// delete
		restrictedTimesRolesService.removeTimesRolesByTime("TIME-00004");

		// check
		try {
			RestrictedTimesRoles resultDomain1 = restrictedTimesRolesService.get(id1);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimesRoles' object with id 'RestrictedTimesRolesId [timeId='"
							+ id1.getTimeId() + "', roleId='" + id1.getRoleId() + "']' not found", e.getMessage());
		}
	}

	@Test
	public void testGetTimeRoleList() throws Exception {

		// make and save data
		List<RestrictedTimesRoles> list = makeDomain();
		List<RestrictedTimesRolesId> idList = makeDomainId();
		restrictedTimesRolesService.saveTimeRoles(list);

		// find list
		RestrictedTimesSearchVO restrictedTimesSearchVO = new RestrictedTimesSearchVO();
		restrictedTimesSearchVO.setSearchCondition("timeId");
		restrictedTimesSearchVO.setSearchKeyword("TIME-00004");
		Page resultPage = restrictedTimesRolesService.getTimeRoleList(restrictedTimesSearchVO);

		Iterator iter = resultPage.getList().iterator();

		Map resultMap1 = (Map) iter.next();
		String resultRoleId1 = (String) resultMap1.get("roleId");
		String resultTimeId1 = (String) resultMap1.get("timeId");
		assertEquals("ROLE_A", resultRoleId1);
		assertEquals("TIME-00004", resultTimeId1);

		Map resultMap2 = (Map) iter.next();
		String resultRoleId2 = (String) resultMap2.get("roleId");
		String resultTimeId2 = (String) resultMap2.get("timeId");
		assertEquals("ROLE_B", resultRoleId2);
		assertEquals("TIME-00004", resultTimeId2);
	}

	@Test
	public void testFindRoleListByTime() throws Exception {

		// make and save data
		List<RestrictedTimesRoles> list = makeDomain();
		List<RestrictedTimesRolesId> idList = makeDomainId();
		restrictedTimesRolesService.saveTimeRoles(list);

		// find list
		List resultList = restrictedTimesRolesService.findRoleListByTime("TIME-00004");
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(2, resultList.size());

		// check
		String resultRole1 = (String) ((Map) resultList.get(0)).get("roleId");
		String resultRole2 = (String) ((Map) resultList.get(1)).get("roleId");
		assertEquals("ROLE_A", resultRole1);
		assertEquals("ROLE_B", resultRole2);
	}

}
