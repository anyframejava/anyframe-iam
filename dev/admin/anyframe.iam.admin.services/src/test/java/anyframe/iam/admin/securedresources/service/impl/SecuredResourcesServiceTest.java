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

package anyframe.iam.admin.securedresources.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.common.Page;
import anyframe.common.exception.BaseException;
import anyframe.iam.admin.domain.IamResourceResult;
import anyframe.iam.admin.domain.SecuredResources;
import anyframe.iam.admin.domain.TempSecuredResources;
import anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;
import anyframe.iam.admin.vo.ResourceSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SecuredResourcesServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("securedResourcesService")
	SecuredResourcesService securedResourcesService;

	@Autowired
	@Qualifier("securedResourcesRolesService")
	SecuredResourcesRolesService securedResourcesRolesService;

	@Autowired
	@Qualifier("jdbcProperties")
	Properties jdbcProperties;

	// make domain
	public SecuredResources makeDomain() {
		SecuredResources domain = new SecuredResources();
		domain.setResourceName("aaaa");
		domain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		domain.setResourceType("pointCut");
		domain.setDescription("......");
		domain.setSystemName("SAMPLE");

		return domain;
	}

	public SecuredResources makePointCutDomain() {
		SecuredResources domain = new SecuredResources();
		domain.setResourceName("aaaa");
		domain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		domain.setResourceType("pointCut");
		domain.setDescription("......");
		domain.setSystemName("SAMPLE");

		return domain;
	}

	public SecuredResources makeMethodDomain() {
		SecuredResources domain = new SecuredResources();
		domain.setResourceName("aaaa");
		domain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		domain.setResourceType("Method");
		domain.setDescription("......");
		domain.setSystemName("SAMPLE");

		return domain;
	}

	public SecuredResources makeURLDomain() {
		SecuredResources domain = new SecuredResources();
		domain.setResourceName("aaaa");
		domain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		domain.setResourceType("URL");
		domain.setDescription("......");
		domain.setSystemName("SAMPLE");

		return domain;
	}

	public String[] makeAndSaveDamains() throws Exception {

		// make data
		SecuredResources pointCutDomain = new SecuredResources();
		pointCutDomain.setResourceName("aaaa");
		pointCutDomain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		pointCutDomain.setResourceType("PointCut");
		pointCutDomain.setDescription("......");
		pointCutDomain.setSystemName("SAMPLE");
		SecuredResources methodDomain = new SecuredResources();
		methodDomain.setResourceName("aaaa");
		methodDomain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		methodDomain.setResourceType("Method");
		methodDomain.setDescription("......");
		methodDomain.setSystemName("SAMPLE");
		SecuredResources urlDomain = new SecuredResources();
		urlDomain.setResourceName("aaaa");
		urlDomain.setResourcePattern("com.sds.emp.code.service.codeService.findCodeName");
		urlDomain.setResourceType("URL");
		urlDomain.setDescription("......");
		urlDomain.setSystemName("SAMPLE");
		String[] resourceIds = new String[3];

		// save data
		securedResourcesService.save(pointCutDomain);
		securedResourcesService.save(methodDomain);
		securedResourcesService.save(urlDomain);

		// set data
		resourceIds[0] = urlDomain.getResourceId();
		resourceIds[1] = pointCutDomain.getResourceId();
		resourceIds[2] = methodDomain.getResourceId();

		return resourceIds;
	}

	// check
	public void checkResult(SecuredResources domain, SecuredResources resultDomain) {
		assertNotNull(resultDomain);
		assertEquals(domain.getResourceId(), resultDomain.getResourceId());
		assertEquals(domain.getResourceName(), resultDomain.getResourceName());
		assertEquals(domain.getResourcePattern(), resultDomain.getResourcePattern());
		assertEquals(domain.getResourceType(), resultDomain.getResourceType());
		assertEquals(domain.getDescription(), resultDomain.getDescription());
	}

	@Test
	public void testSave() throws Exception {
		SecuredResources pointCutDomain = makePointCutDomain();
		SecuredResources methodDomain = makeMethodDomain();
		SecuredResources urlDomain = makeURLDomain();
		SecuredResources resultDomain;
		// save PointCut resource
		securedResourcesService.save(pointCutDomain);

		// get
		resultDomain = securedResourcesService.get(pointCutDomain.getResourceId());

		// check
		checkResult(pointCutDomain, resultDomain);

		// save Method resource
		securedResourcesService.save(methodDomain);

		// get
		resultDomain = securedResourcesService.get(methodDomain.getResourceId());

		// check
		checkResult(methodDomain, resultDomain);

		// save URL resource
		securedResourcesService.save(urlDomain);

		// get
		resultDomain = securedResourcesService.get(urlDomain.getResourceId());

		// check
		checkResult(urlDomain, resultDomain);
	}

	@Test
	public void testGetList() throws Exception {
		SecuredResources domain = makePointCutDomain();
		ResourceSearchVO searchVO = new ResourceSearchVO();
		searchVO.setSearchKeyword("aaaa");
		searchVO.setSearchCondition("resourceName");
		searchVO.setSystemName("ALL");
		// save one data
		securedResourcesService.save(domain);

		// find list
		Page resultPage = securedResourcesService.getList(searchVO);

		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());
		checkResult(domain, (SecuredResources) resultPage.getList().iterator().next());

	}

	@Test
	public void testDelete() throws Exception {
		SecuredResources domain = makePointCutDomain();
		String[] ids = new String[1];

		// save
		securedResourcesService.save(domain);
		ids[0] = domain.getResourceId();

		// delete
		securedResourcesService.delete(ids);

		// get
		try {
			securedResourcesService.get(domain.getResourceId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.SecuredResources' object with id '" + domain.getResourceId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testGetMappedList() throws Exception {

		String[] resourceIds = makeAndSaveDamains();

		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, "ROLE_A");

		// find list
		ResourceSearchVO searchVO = new ResourceSearchVO();
		searchVO.setSearchKeyword("aaaa");
		searchVO.setSearchCondition("resourceName");
		searchVO.setRoleId("ROLE_A");

		Page resultPage = securedResourcesService.getMappedList(searchVO);
		assertEquals(3, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		// check
		assertEquals(resourceIds[0], ((Map) iter.next()).get("resourceId"));
		assertEquals(resourceIds[2], ((Map) iter.next()).get("resourceId"));
		assertEquals(resourceIds[1], ((Map) iter.next()).get("resourceId"));
	}

	@Test
	public void testGetUnmappedList() throws Exception {

		String[] resourceIds = makeAndSaveDamains();

		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, "ROLE_B");

		// find list
		ResourceSearchVO searchVO = new ResourceSearchVO();
		searchVO.setSearchKeyword("aaaa");
		searchVO.setSearchCondition("resourceName");
		searchVO.setRoleId("ROLE_A");

		Page resultPage = securedResourcesService.getUnmappedList(searchVO);
		assertEquals(3, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		// check
		assertEquals(resourceIds[0], ((SecuredResources) iter.next()).getResourceId());
		assertEquals(resourceIds[2], ((SecuredResources) iter.next()).getResourceId());
		assertEquals(resourceIds[1], ((SecuredResources) iter.next()).getResourceId());
	}

	@Test
	public void testUpdate() throws Exception {
		SecuredResources domain = makePointCutDomain();

		// save
		securedResourcesService.save(domain);

		domain.setResourceName("bbbb");
		// update
		securedResourcesService.update(domain);

		// check
		SecuredResources resultDomain = securedResourcesService.get(domain.getResourceId());

		checkResult(domain, resultDomain);
	}

	@Test
	public void testGetListWithLevel() throws Exception {

		String testDialect = jdbcProperties.getProperty("test.hibernate.dialect");

		try {

			// save data
			String[] resourceIds = makeAndSaveDamains();

			securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, "ROLE_A");

			// find list
			ResourceSearchVO searchVO = new ResourceSearchVO();
			searchVO.setRoleId("ROLE_A");
			searchVO.setSearchCondition("resourceName");
			searchVO.setSearchKeyword("aaaa");
			searchVO.setSidx("resourceId");

			Page resultPage = securedResourcesService.getListwithLevel(searchVO);
			assertEquals(3, resultPage.getSize());
			Iterator iter = resultPage.getList().iterator();

			// check
			int i;
			for (i = 0; i < 3; i++) {
				IamResourceResult resultResource = (IamResourceResult) iter.next();
				System.out.println(resultResource.getResourceId());
				assertEquals(resultResource.getResourceId(), resourceIds[i]);
			}

		}
		catch (Exception e) {
			if ("org.hibernate.dialect.OracleDialect".equals(testDialect)) {
				assertNotNull(e);
			}
		}
	}
	
	
	@Test
	public void testMakeAllTempRolesList() throws Exception{
		List<TempSecuredResources> resultList = securedResourcesService.makeAllTempResourcesList();
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
	}
	
//	@Test
//	public void testRemoveAllSecuredResources() throws Exception{
//		securedResourcesService.removeAllSecuredResources();
//		
//		ResourceSearchVO searchVO = new ResourceSearchVO();
//		Page resultPage = securedResourcesService.getList(searchVO);
//		assertTrue(resultPage.getSize() == 0);
//	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testSaveList() throws Exception{
//		securedResourcesService.removeAllSecuredResources();
		
		TempSecuredResources sr1 = new TempSecuredResources();
		sr1.setResourceId("tempresource1");
		sr1.setResourceName("tempresource1");
		sr1.setResourcePattern("tempresource1");
		sr1.setResourceType("url");
		sr1.setCreateDate("20100909");
		sr1.setSortOrder("99");
		sr1.setSystemName("sample1");
		sr1.setRoleId("ROLE_ADMIN");
		
		TempSecuredResources sr2 = new TempSecuredResources();
		sr2.setResourceId("tempresource2");
		sr2.setResourceName("tempresource2");
		sr2.setResourcePattern("tempresource2");
		sr2.setResourceType("method");
		sr2.setCreateDate("20100909");
		sr2.setSortOrder("99");
		sr2.setSystemName("sample1");
		sr2.setRoleId("ROLE_USER,ROLE_ADMIN");
		
		List resourceList = new ArrayList();
		resourceList.add(sr1);
		resourceList.add(sr2);
		
		securedResourcesService.save(resourceList);
		
		ResourceSearchVO searchVO = new ResourceSearchVO();
		
		Page resultPage = securedResourcesService.getList(searchVO);

		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		
	}
}
