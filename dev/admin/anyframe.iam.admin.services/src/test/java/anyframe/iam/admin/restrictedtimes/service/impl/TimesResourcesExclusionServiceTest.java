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
import anyframe.iam.admin.domain.TimesResourcesExclusion;
import anyframe.iam.admin.domain.TimesResourcesExclusionId;
import anyframe.iam.admin.restrictedtimes.service.TimesResourcesExclusionService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class TimesResourcesExclusionServiceTest {
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("timesResourcesExclusionService")
	TimesResourcesExclusionService timesResourcesExclusionService;

	// make data
	public List<TimesResourcesExclusion> makeDomain() {
		TimesResourcesExclusion domain1 = new TimesResourcesExclusion();
		TimesResourcesExclusionId id1 = new TimesResourcesExclusionId();
		id1.setResourceId("MTD-000020");
		id1.setRoleId("ROLE_B");
		id1.setTimeId("TIME-00004");
		domain1.setId(id1);

		TimesResourcesExclusion domain2 = new TimesResourcesExclusion();
		TimesResourcesExclusionId id2 = new TimesResourcesExclusionId();
		id2.setResourceId("MTD-000020");
		id2.setRoleId("ROLE_A");
		id2.setTimeId("TIME-00004");
		domain2.setId(id2);

		List<TimesResourcesExclusion> list = new ArrayList();
		list.add(domain1);
		list.add(domain2);

		return list;
	}

	// make ID data
	public List<TimesResourcesExclusionId> makeDomainId() {
		TimesResourcesExclusionId id1 = new TimesResourcesExclusionId();
		id1.setResourceId("MTD-000020");
		id1.setRoleId("ROLE_B");
		id1.setTimeId("TIME-00004");

		List<TimesResourcesExclusionId> list = new ArrayList();
		list.add(id1);

		return list;
	}

	@Test
	public void testSaveTimeExclusion() throws Exception {

		// make data
		TimesResourcesExclusion domain1 = new TimesResourcesExclusion();
		TimesResourcesExclusionId id1 = new TimesResourcesExclusionId();
		id1.setResourceId("MTD-000020");
		id1.setRoleId("ROLE_B");
		id1.setTimeId("TIME-00004");
		domain1.setId(id1);

		List<TimesResourcesExclusion> list = new ArrayList();
		list.add(domain1);

		// save data
		timesResourcesExclusionService.saveTimeExclusion(list);

		// check
		TimesResourcesExclusion resultDomain = timesResourcesExclusionService.get(id1);
		assertEquals(id1.getResourceId(), resultDomain.getId().getResourceId());
		assertEquals(id1.getRoleId(), resultDomain.getId().getRoleId());
		assertEquals(id1.getTimeId(), resultDomain.getId().getTimeId());
	}

	@Test
	public void testDelete() throws Exception {

		// make data
		List<TimesResourcesExclusion> list = makeDomain();
		List<TimesResourcesExclusionId> idList = makeDomainId();
		timesResourcesExclusionService.saveTimeExclusion(list);

		// delete
		timesResourcesExclusionService.delete(idList);

		// check
		try {
			timesResourcesExclusionService.get(idList.get(0));
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.TimesResourcesExclusion' object with id 'TimesResourcesExclusionId [timeId='"
							+ idList.get(0).getTimeId() + "', resourceId='" + idList.get(0).getResourceId()
							+ "', roleId='" + idList.get(0).getRoleId() + "']' not found", e.getMessage());
		}
	}

	@Test
	public void testRemoveTimesExclusionByTimeResource() throws Exception {

		// make data
		List<TimesResourcesExclusion> domaimList = makeDomain();
		List<TimesResourcesExclusionId> idList = makeDomainId();
		TimesResourcesExclusionId id1 = idList.get(0);

		// save
		timesResourcesExclusionService.saveTimeExclusion(domaimList);

		// remove
		timesResourcesExclusionService.removeTimesExclusionByTimeResource(idList.get(0).getTimeId(), idList.get(0)
				.getResourceId());

		// check
		try {
			System.out.println(timesResourcesExclusionService.get(id1));
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.TimesResourcesExclusion' object with id 'TimesResourcesExclusionId [timeId='"
							+ id1.getTimeId() + "', resourceId='" + id1.getResourceId() + "', roleId='"
							+ id1.getRoleId() + "']' not found", e.getMessage());
		}
	}

	@Test
	public void testFindRoleListByTimeResource() throws Exception {

		// make domain
		List<TimesResourcesExclusion> domainList = makeDomain();

		// save
		timesResourcesExclusionService.saveTimeExclusion(domainList);

		// find list
		String timeId = "TIME-00004";
		String resourceId = "MTD-000020";
		List roleList = timesResourcesExclusionService.findRoleListByTimeResource(timeId, resourceId);

		String roleId1 = (String) ((Map) roleList.get(0)).get("roleId");
		String roleId2 = (String) ((Map) roleList.get(1)).get("roleId");
		assertEquals("ROLE_A", roleId1);
		assertEquals("ROLE_B", roleId2);
	}

	@Test
	public void testGetTimeExclusionList() throws Exception {

		// make domain
		List<TimesResourcesExclusion> domainList = makeDomain();

		// save
		timesResourcesExclusionService.saveTimeExclusion(domainList);

		// find list
		RestrictedTimesSearchVO restrictedTimesSearchVO = new RestrictedTimesSearchVO();
		restrictedTimesSearchVO.setSearchCondition("resourceId");
		restrictedTimesSearchVO.setSearchKeyword("MTD-000020");
		Page resultPage = timesResourcesExclusionService.getTimeExclusionList(restrictedTimesSearchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);

		Iterator iter = resultPage.getList().iterator();
		Map resultMap = (Map) iter.next();
		String resultTimeId = (String) (resultMap).get("timeId");
		String resultroleId = (String) (resultMap).get("roleId");
		String resultresourceId = (String) (resultMap).get("resourceId");

		assertEquals("TIME-00004", resultTimeId);
		assertEquals("ROLE_A", resultroleId);
		assertEquals("MTD-000020", resultresourceId);
	}
}
