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

package org.anyframe.iam.admin.restrictedtimes.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.anyframe.exception.BaseException;
import org.anyframe.iam.admin.domain.RestrictedTimesResources;
import org.anyframe.iam.admin.domain.RestrictedTimesResourcesId;
import org.anyframe.iam.admin.domain.RestrictedTimesRoles;
import org.anyframe.iam.admin.domain.RestrictedTimesRolesId;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesResourcesService;
import org.anyframe.iam.admin.restrictedtimes.service.RestrictedTimesRolesService;
import org.anyframe.iam.admin.vo.RestrictedTimesSearchVO;
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
public class RestrictedTimesResourcesServiceTest {
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("restrictedTimesResourcesService")
	RestrictedTimesResourcesService restrictedTimesResourcesService;

	@Autowired
	@Qualifier("restrictedTimesRolesService")
	RestrictedTimesRolesService restrictedTimesRolesService;

	public List<RestrictedTimesResources> makeDomain() {
		// make domain
		RestrictedTimesResources domain1 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id1 = new RestrictedTimesResourcesId();
		id1.setResourceId("MTD-000006");
		id1.setTimeId("TIME-00005");
		domain1.setId(id1);
		RestrictedTimesResources domain2 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id2 = new RestrictedTimesResourcesId();
		id2.setResourceId("MTD-000007");
		id2.setTimeId("TIME-00004");
		domain2.setId(id2);
		List<RestrictedTimesResources> lst = new ArrayList();
		lst.add(domain1);
		lst.add(domain2);

		return lst;
	}

	@Test
	public void testAddTimeResource() throws Exception {

		// make domain
		RestrictedTimesResources domain1 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id1 = new RestrictedTimesResourcesId();
		id1.setResourceId("MTD-000006");
		id1.setTimeId("TIME-00005");
		domain1.setId(id1);
		RestrictedTimesResources domain2 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id2 = new RestrictedTimesResourcesId();
		id2.setResourceId("MTD-000007");
		id2.setTimeId("TIME-00004");
		domain2.setId(id2);
		List<RestrictedTimesResources> lst = new ArrayList();
		lst.add(domain1);
		lst.add(domain2);

		// save
		restrictedTimesResourcesService.addTimeResources(lst);

		// get
		RestrictedTimesResources result1 = restrictedTimesResourcesService.get(id1);
		RestrictedTimesResources result2 = restrictedTimesResourcesService.get(id2);

		// check
		assertEquals(result1.getId().getResourceId(), domain1.getId().getResourceId());
		assertEquals(result1.getId().getTimeId(), domain1.getId().getTimeId());
		assertEquals(result2.getId().getResourceId(), domain2.getId().getResourceId());
		assertEquals(result2.getId().getTimeId(), domain2.getId().getTimeId());

	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		RestrictedTimesResources domain1 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id1 = new RestrictedTimesResourcesId();
		id1.setResourceId("MTD-000006");
		id1.setTimeId("TIME-00005");
		domain1.setId(id1);
		RestrictedTimesResources domain2 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id2 = new RestrictedTimesResourcesId();
		id2.setResourceId("MTD-000007");
		id2.setTimeId("TIME-00004");
		domain2.setId(id2);
		List<RestrictedTimesResources> lst = new ArrayList();
		lst.add(domain1);
		lst.add(domain2);

		List<RestrictedTimesResourcesId> list = new ArrayList();
		list.add(id1);
		list.add(id2);

		// save
		restrictedTimesResourcesService.addTimeResources(lst);

		// delete
		restrictedTimesResourcesService.delete(list);

		// check
		try {
			restrictedTimesResourcesService.get(id1);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class org.anyframe.iam.admin.domain.RestrictedTimesResources' object with id 'RestrictedTimesResourcesId [timeId='"
							+ id1.getTimeId() + "', resourceId='" + id1.getResourceId() + "']' not found", e
							.getMessage());
		}

		try {
			restrictedTimesResourcesService.get(id2);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class org.anyframe.iam.admin.domain.RestrictedTimesResources' object with id 'RestrictedTimesResourcesId [timeId='"
							+ id2.getTimeId() + "', resourceId='" + id2.getResourceId() + "']' not found", e
							.getMessage());
		}
	}

	@Test
	public void testGetTimeResourceList() throws Exception {

		// make domain
		RestrictedTimesResources domain1 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id1 = new RestrictedTimesResourcesId();
		id1.setResourceId("MTD-000006");
		id1.setTimeId("TIME-00005");
		domain1.setId(id1);
		RestrictedTimesResources domain2 = new RestrictedTimesResources();
		RestrictedTimesResourcesId id2 = new RestrictedTimesResourcesId();
		id2.setResourceId("MTD-000007");
		id2.setTimeId("TIME-00005");
		domain2.setId(id2);
		List<RestrictedTimesResources> lst = new ArrayList();
		lst.add(domain1);
		lst.add(domain2);

		List<RestrictedTimesResourcesId> list = new ArrayList();
		list.add(id1);
		list.add(id2);

		// save
		restrictedTimesResourcesService.addTimeResources(lst);
		RestrictedTimesSearchVO restrictedTimesSearchVO = new RestrictedTimesSearchVO();
		restrictedTimesSearchVO.setSearchKeyword("TIME-00005");
		restrictedTimesSearchVO.setSearchCondition("timeId");
		Page resultPage = restrictedTimesResourcesService.getTimeResourceList(restrictedTimesSearchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(2, resultPage.getSize());

		Iterator iter = resultPage.getList().iterator();
		Map resultMap = (Map) iter.next();
		String resultTimeId = (String) (resultMap).get("timeId");
		String resultResourceId = (String) (resultMap).get("resourceId");
		assertEquals("TIME-00005", resultTimeId);
		assertEquals("MTD-000006", resultResourceId);
	}

	@Test
	public void testFindRoleListByTimeTest() throws Exception {

		// make data and save
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

		List<RestrictedTimesRoles> list = new ArrayList();

		list.add(domain1);
		list.add(domain2);

		restrictedTimesRolesService.saveTimeRoles(list);

		// find list
		List<Roles> resultList = restrictedTimesResourcesService.findRoleListByTime("TIME-00004");
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(2, resultList.size());

		// check
		String resultRole1 = resultList.get(0).getRoleId();
		String resultRole2 = resultList.get(1).getRoleId();
		assertEquals(domain1.getId().getRoleId(), resultRole1);
		assertEquals(domain2.getId().getRoleId(), resultRole2);
	}

	@Test
	public void testFindResourceListByTime() throws Exception {

		// make and save data
		List<RestrictedTimesResources> list = makeDomain();
		restrictedTimesResourcesService.addTimeResources(list);

		// find list
		RestrictedTimesSearchVO searchVO = new RestrictedTimesSearchVO();
		searchVO.setSearchKeyword("TIME-00004");
		Page resultPage = restrictedTimesResourcesService.findResourceListByTime(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		String resultResourceId = (String) ((Map) iter.next()).get("resourceId");
		assertEquals("MTD-000007", resultResourceId);
	}

	@Test
	public void testFindUnmappedResourceListByTime() throws Exception {

		// save sample data
		List<RestrictedTimesResources> list = makeDomain();
		restrictedTimesResourcesService.addTimeResources(list);

		// set search condition
		RestrictedTimesSearchVO searchVO = new RestrictedTimesSearchVO();
		searchVO.setTimeId("TIME-00004");
		searchVO.setSearchKeyword("000007");
		searchVO.setSearchCondition("resourceId");

		// find list
		Page resultPage = restrictedTimesResourcesService.findUnmappedResourceListByTime(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);

		Iterator iter = resultPage.getList().iterator();

		String resultResourceId = (String) ((Map) iter.next()).get("resourceId");
		assertEquals("WEB-000007", resultResourceId);
	}
}
