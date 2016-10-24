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
import anyframe.iam.admin.domain.RestrictedTimes;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.vo.RestrictedTimesSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class RestrictedTimesServiceTest {
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("restrictedTimesService")
	RestrictedTimesService restrictedTimesService;

	// make domain
	public RestrictedTimes makeDomain() {
		RestrictedTimes domain = new RestrictedTimes();
		domain.setStartDate("20090830");
		domain.setStartTime("000000");
		domain.setEndDate("20090909");
		domain.setEndTime("235959");
		domain.setTimeType("holiday");
		domain.setDescription("choosuk");

		return domain;
	}

	// check
	public void check(RestrictedTimes expected, RestrictedTimes current) {
		assertEquals(expected.getTimeId(), current.getTimeId());
		assertEquals(expected.getTimeType(), current.getTimeType());
		assertEquals(expected.getEndDate(), current.getEndDate());
		assertEquals(expected.getEndTime(), current.getEndTime());
		assertEquals(expected.getStartDate(), current.getStartDate());
		assertEquals(expected.getStartTime(), current.getStartTime());
		assertEquals(expected.getDescription(), current.getDescription());
	}

	@Test
	public void testSave() throws Exception {
		// make domain
		RestrictedTimes domain = makeDomain();

		// save
		domain = restrictedTimesService.save(domain);

		// get
		RestrictedTimes current = restrictedTimesService.get(domain.getTimeId());

		// check
		check(domain, current);
	}

	@Test
	public void testUpdate() throws Exception {
		// make domain
		RestrictedTimes domain = makeDomain();

		// save
		domain = restrictedTimesService.save(domain);

		domain.setStartDate("20091030");
		domain.setStartTime("100000");
		domain.setEndDate("20091107");
		domain.setEndTime("200000");
		domain.setTimeType("daily");

		// update
		restrictedTimesService.update(domain);

		// get
		RestrictedTimes current = restrictedTimesService.get(domain.getTimeId());

		// check
		check(domain, current);
	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		RestrictedTimes domain1 = makeDomain();
		RestrictedTimes domain2 = new RestrictedTimes();
		domain2.setStartDate("20080808");
		domain2.setStartTime("000000");
		domain2.setEndDate("20090909");
		domain2.setEndTime("235959");
		domain2.setTimeType("daily");

		RestrictedTimes domain3 = new RestrictedTimes();
		domain3.setStartDate("20070707");
		domain3.setStartTime("000000");
		domain3.setEndDate("20101010");
		domain3.setEndTime("235959");
		domain3.setTimeType("daily");

		// save
		domain1 = restrictedTimesService.save(domain1);
		domain2 = restrictedTimesService.save(domain2);
		domain3 = restrictedTimesService.save(domain3);

		String[] timeIds = new String[3];
		timeIds[0] = domain1.getTimeId();
		timeIds[1] = domain2.getTimeId();
		timeIds[2] = domain3.getTimeId();

		// delete
		restrictedTimesService.delete(timeIds);

		// check
		try {
			restrictedTimesService.get(timeIds[0]);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimes' object with id '" + domain1.getTimeId()
							+ "' not found", e.getMessage());
		}

		try {
			restrictedTimesService.get(timeIds[1]);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimes' object with id '" + domain2.getTimeId()
							+ "' not found", e.getMessage());
		}

		try {
			restrictedTimesService.get(timeIds[2]);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.RestrictedTimes' object with id '" + domain3.getTimeId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testGetList() throws Exception {

		// make domain
		RestrictedTimes domain1 = makeDomain();
		RestrictedTimes domain2 = new RestrictedTimes();
		domain2.setStartDate("20080808");
		domain2.setStartTime("000000");
		domain2.setEndDate("20090909");
		domain2.setEndTime("235959");
		domain2.setTimeType("daily");

		RestrictedTimes domain3 = new RestrictedTimes();
		domain3.setStartDate("20070707");
		domain3.setStartTime("000000");
		domain3.setEndDate("20101010");
		domain3.setEndTime("235959");
		domain3.setTimeType("daily");

		// save
		domain1 = restrictedTimesService.save(domain1);
		domain2 = restrictedTimesService.save(domain2);
		domain3 = restrictedTimesService.save(domain3);

		// get list
		RestrictedTimesSearchVO searchVO = new RestrictedTimesSearchVO();
		searchVO.setSearchType("daily");
		Page resultPage = restrictedTimesService.getList(searchVO);
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(2, resultPage.getSize());
		Iterator iter = resultPage.getList().iterator();

		RestrictedTimes result = (RestrictedTimes) iter.next();
		check(result, domain3);

		result = (RestrictedTimes) iter.next();
		check(result, domain2);
	}
}
