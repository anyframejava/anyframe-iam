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
import anyframe.iam.admin.domain.ViewResourcesMapping;
import anyframe.iam.admin.domain.ViewResourcesMappingPK;
import anyframe.iam.admin.viewresources.service.ViewMappingService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class ViewMappingServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("viewMappingService")
	ViewMappingService viewMappingService;

	// make domain
	public ViewResourcesMapping[] makeDomain() {
		String[] mask = new String[1];
		mask[0] = "10000";
		ViewResourcesMapping[] viewResourcesMapping = new ViewResourcesMapping[1];
		ViewResourcesMappingPK[] viewResourcesMappingPK = new ViewResourcesMappingPK[1];

		viewResourcesMapping[0] = new ViewResourcesMapping();
		viewResourcesMappingPK[0] = new ViewResourcesMappingPK();

		viewResourcesMappingPK[0].setViewResourceId("updateUser");
		viewResourcesMappingPK[0].setRefId("ROLE_ADMIN");

		viewResourcesMapping[0].setId(viewResourcesMappingPK[0]);
		viewResourcesMapping[0].setMask(Integer.parseInt(mask[0], 2));
		String permissions = "READ";
		viewResourcesMapping[0].setPermissions(permissions);
		viewResourcesMapping[0].setRefType("ROLE");

		return viewResourcesMapping;
	}

	@Test
	public void testSave() throws Exception {

		// make domain
		ViewResourcesMapping[] viewResourcesMapping = makeDomain();

		// save
		viewMappingService.save(viewResourcesMapping);

		// get
		ViewResourcesMappingPK pk = new ViewResourcesMappingPK();
		pk.setRefId("ROLE_ADMIN");
		pk.setViewResourceId("updateUser");
		ViewResourcesMapping resultDomain = viewMappingService.get(pk);

		assertNotNull(resultDomain);
		assertEquals(viewResourcesMapping[0].getId().getViewResourceId(), resultDomain.getId().getViewResourceId());
		assertEquals(viewResourcesMapping[0].getId().getRefId(), resultDomain.getId().getRefId());
	}

	@Test
	public void testGet() throws Exception {

		// make domain
		ViewResourcesMapping[] viewResourcesMapping = makeDomain();

		// save
		viewMappingService.save(viewResourcesMapping);

		// get
		String viewResourceId = "updateUser";
		List resultList = viewMappingService.get(viewResourceId);

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(1, resultList.size());

		ViewResourcesMapping resultDomain = (ViewResourcesMapping) resultList.get(0);
		assertEquals(viewResourcesMapping[0].getId().getRefId(), resultDomain.getId().getRefId());
		assertEquals(viewResourceId, resultDomain.getId().getViewResourceId());
	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		ViewResourcesMapping[] viewResourcesMapping = makeDomain();

		// save
		viewMappingService.save(viewResourcesMapping);

		// delete
		List<ViewResourcesMappingPK> list = new ArrayList();
		ViewResourcesMappingPK id1 = new ViewResourcesMappingPK();
		id1.setRefId("ROLE_ADMIN");
		id1.setViewResourceId("updateUser");
		list.add(id1);

		viewMappingService.delete(list);

		// check
		try {
			viewMappingService.get(id1);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.ViewResourcesMapping' object with id '" + id1.toString()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testGetList() throws Exception {

		// make domain
		ViewResourcesMapping[] viewResourcesMapping = makeDomain();

		// save
		viewMappingService.save(viewResourcesMapping);

		// find list
		ViewResourceSearchVO searchVO = new ViewResourceSearchVO();
		searchVO.setSearchCondition("viewResourceId");
		searchVO.setSearchKeyword("updateUser");
		searchVO.setSystemName("SAMPLE");
		Page resultPage = viewMappingService.getList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());

		Iterator iter = resultPage.getList().iterator();
		Map resultDomain = (Map) iter.next();

		assertEquals("updateUser", resultDomain.get("viewResourceId"));
		assertEquals("ROLE_ADMIN", resultDomain.get("refId"));
	}
}
