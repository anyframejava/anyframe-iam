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

package anyframe.iam.admin.securedresourcesroles.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.common.exception.BaseException;
import anyframe.iam.admin.domain.SecuredResourcesRoles;
import anyframe.iam.admin.domain.SecuredResourcesRolesId;
import anyframe.iam.admin.securedresourcesroles.service.SecuredResourcesRolesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SecuredResourcesRolesServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("securedResourcesRolesService")
	SecuredResourcesRolesService securedResourcesRolesService;

	// make domain
	public SecuredResourcesRoles makeDomain() {
		SecuredResourcesRoles securedResourcesRoles = new SecuredResourcesRoles();
		SecuredResourcesRolesId id = new SecuredResourcesRolesId();
		id.setResourceId("mtd_000006");
		id.setRoleId("ROLE_A");
		securedResourcesRoles.setId(id);
		securedResourcesRoles.setCreateDate("20090811");

		return securedResourcesRoles;
	}

	// check
	public void checkResult(SecuredResourcesRoles domain, SecuredResourcesRoles resultDomain) {
		assertNotNull(resultDomain);
		assertEquals(domain.getId(), resultDomain.getId());
		assertEquals(domain.getCreateDate(), resultDomain.getCreateDate());
	}

	@Test
	public void testSave() throws Exception {

		// make data
		SecuredResourcesRoles securedResourcesRoles = new SecuredResourcesRoles();
		SecuredResourcesRolesId id = new SecuredResourcesRolesId();
		id.setResourceId("mtd_000006");
		id.setRoleId("ROLE_A");
		securedResourcesRoles.setId(id);
		securedResourcesRoles.setCreateDate("20090811");

		// save
		securedResourcesRolesService.save(securedResourcesRoles);

		// get
		SecuredResourcesRoles resultDomain = securedResourcesRolesService.get(id);

		// check
		checkResult(securedResourcesRoles, resultDomain);
	}

	@Test
	public void testAddSecuredResourcesRoles() throws Exception {

		// make data
		String[] resourceIds = new String[3];
		resourceIds[0] = "mtd_000006";
		resourceIds[1] = "web-000003";
		resourceIds[2] = "web-000010";

		String roleId = "ROLE_B";

		// save
		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, roleId);

		// check
		SecuredResourcesRolesId id1 = new SecuredResourcesRolesId();
		id1.setResourceId("mtd_000006");
		id1.setRoleId("ROLE_B");
		SecuredResourcesRoles domain1 = securedResourcesRolesService.get(id1);

		assertEquals(domain1.getId().getResourceId(), "mtd_000006");
		assertEquals(domain1.getId().getRoleId(), "ROLE_B");

		SecuredResourcesRolesId id2 = new SecuredResourcesRolesId();
		id2.setResourceId("web-000003");
		id2.setRoleId("ROLE_B");
		SecuredResourcesRoles domain2 = securedResourcesRolesService.get(id2);

		assertEquals(domain2.getId().getResourceId(), "web-000003");
		assertEquals(domain2.getId().getRoleId(), "ROLE_B");

		SecuredResourcesRolesId id3 = new SecuredResourcesRolesId();
		id3.setResourceId("web-000010");
		id3.setRoleId("ROLE_B");
		SecuredResourcesRoles domain3 = securedResourcesRolesService.get(id3);

		assertEquals(domain3.getId().getResourceId(), "web-000010");
		assertEquals(domain3.getId().getRoleId(), "ROLE_B");
	}

	@Test
	public void testDeleteSecuredResourceRoles() throws Exception {

		// make data
		String[] resourceIds = new String[3];
		resourceIds[0] = "mtd_000006";
		resourceIds[1] = "web-000003";
		resourceIds[2] = "web-000010";

		String roleId = "ROLE_B";

		// save
		securedResourcesRolesService.addSecuredResourcesRoles(resourceIds, roleId);

		// delete
		securedResourcesRolesService.deleteSecuredResourceRoles(resourceIds, roleId);

		// check
		SecuredResourcesRolesId id1 = new SecuredResourcesRolesId();
		id1.setResourceId("mtd_000006");
		id1.setRoleId("ROLE_B");
		try {
			securedResourcesRolesService.get(id1);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.SecuredResourcesRoles' object with id 'SecuredResourcesRolesId [resourceId='"
							+ id1.getResourceId() + "', roleId='" + id1.getRoleId() + "']' not found", e.getMessage());
		}

		SecuredResourcesRolesId id2 = new SecuredResourcesRolesId();
		id2.setResourceId("web-000003");
		id2.setRoleId("ROLE_B");
		try {
			securedResourcesRolesService.get(id2);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.SecuredResourcesRoles' object with id 'SecuredResourcesRolesId [resourceId='"
							+ id2.getResourceId() + "', roleId='" + id2.getRoleId() + "']' not found", e.getMessage());
		}
		SecuredResourcesRolesId id3 = new SecuredResourcesRolesId();
		id3.setResourceId("web-000010");
		id3.setRoleId("ROLE_B");
		try {
			securedResourcesRolesService.get(id3);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.SecuredResourcesRoles' object with id 'SecuredResourcesRolesId [resourceId='"
							+ id3.getResourceId() + "', roleId='" + id3.getRoleId() + "']' not found", e.getMessage());
		}
	}

}
