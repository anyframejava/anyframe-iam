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

package anyframe.iam.admin.viewresources.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

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
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ViewResourcesServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("viewResourcesService")
	ViewResourcesService viewResourcesService;

	// make domain
	public ViewResource makeDomain() {
		ViewResource domain = new ViewResource();
		domain.setViewResourceId("addUser");
		domain.setViewName("addUser");
		domain.setCategory("User Mgmt.");
		domain.setDescription("사용자 추가");
		domain.setViewInfo("사용자 추가");

		return domain;
	}

	@Test
	public void testGetList() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		viewResourcesService.save(domain);

		// search
		ViewResourceSearchVO searchVO = new ViewResourceSearchVO();
		searchVO.setSearchCondition("viewResourceId");
		searchVO.setSearchKeyword("addUser");
		Page resultPage = viewResourcesService.getList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());

		Iterator iter = resultPage.getList().iterator();
		ViewResource resultDomain = (ViewResource) iter.next();
		assertEquals(domain.getViewResourceId(), resultDomain.getViewResourceId());
	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		viewResourcesService.save(domain);

		// delete
		String[] viewResourceIds = new String[1];
		viewResourceIds[0] = "addUser";
		viewResourcesService.delete(viewResourceIds);

		// check
		try {
			viewResourcesService.get("addUser");
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.ViewResource' object with id '" + domain.getViewResourceId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testUpdate() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		ViewResource updatedDomain = viewResourcesService.save(domain);

		// update
		updatedDomain.setCategory("사용자관리");
		updatedDomain.setViewName("사용자추가");
		viewResourcesService.update(updatedDomain);

		// check
		ViewResource resultDomain = viewResourcesService.get(updatedDomain.getViewResourceId());
		assertEquals(resultDomain.getViewName(), updatedDomain.getViewName());
		assertEquals(resultDomain.getCategory(), updatedDomain.getCategory());

	}
}
