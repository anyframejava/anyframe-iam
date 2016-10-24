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

package anyframe.iam.admin.roles.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import anyframe.common.exception.BaseException;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.authorities.service.AuthoritiesService;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.Roles;
import anyframe.iam.admin.domain.RolesHierarchy;
import anyframe.iam.admin.domain.RolesHierarchyId;
import anyframe.iam.admin.roles.service.RolesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class RolesServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("rolesService")
	RolesService rolesService;

	@Autowired
	@Qualifier("authoritiesService")
	AuthoritiesService authoritiesService;

	@Autowired
	@Qualifier("idGenerationServiceRole")
	IIdGenerationService idGenerationServiceRole;

	// make domain
	public Roles makeDomain() throws Exception {

		Roles domain = new Roles();
		domain.setRoleName("ROLE_TEST");

		RolesHierarchy rolesHierarchy = new RolesHierarchy();

		String parentRole = idGenerationServiceRole.getNextStringId();

		RolesHierarchyId rolesHierarchyId = new RolesHierarchyId();
		rolesHierarchyId.setChildRole("ROLE-00002");
		rolesHierarchyId.setParentRole(parentRole);

		rolesHierarchy.setId(rolesHierarchyId);

		Set<RolesHierarchy> childRole = new HashSet<RolesHierarchy>();

		childRole.add(rolesHierarchy);

		domain.setRolesHierarchiesForChildRole(childRole);

		domain.setRoleId(parentRole);
		domain.setDescription(parentRole + " DESC");

		return domain;
	}

	@Test
	public void testFindRole() throws Exception {

		// make data
		String[] userIds = new String[1];
		userIds[0] = "bearbin.song";
		String roleId = "ROLE_USER";

		// save
		authoritiesService.addUser(userIds, roleId);

		// find Role
		List<Roles> resultList = rolesService.findRoles(userIds[0]);

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals("ROLE_USER", resultList.get(0).getRoleId());
	}

	@Test
	public void testGetRoleTree() throws Exception {

		// make domain
		Roles domain = makeDomain();

		// save
		Roles savedRoles = rolesService.save(domain);

		// get role tree
		List<IamTree> resultList = rolesService.getRoleTree("ROLE-00002");

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals("ROLE-00003", resultList.get(0).getId());
	}

	@Test
	public void testUpdate() throws Exception {

		// make domain
		Roles domain = makeDomain();

		// save
		Roles savedDomain = rolesService.save(domain);

		// update
		Roles updatedDomain = rolesService.get(savedDomain.getRoleId());
		updatedDomain.setRoleName("TEST_ROLE");

		rolesService.update(updatedDomain);

		// check
		Roles resultDomain = rolesService.get(savedDomain.getRoleId());
		assertNotNull(resultDomain);
		assertEquals(updatedDomain.getRoleId(), resultDomain.getRoleId());
		assertEquals("TEST_ROLE", resultDomain.getRoleName());
	}

	@Test
	public void testGetRootNodeOfRoles() throws Exception {
		List<IamTree> rootNode = rolesService.getRootNodeOfRoles();
		assertNotNull(rootNode);
		assertEquals("ROLE_ADMIN", rootNode.get(0).getId());
	}

	@Test
	public void testGetList() throws Exception {
		List<Roles> resultList = rolesService.getList();
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);

		assertEquals("IS_AUTHENTICATED_FULLY", resultList.get(0).getRoleId());
	}

	@Test
	public void testRemove() throws Exception {

		// make domain
		Roles domain = makeDomain();

		// save
		Roles savedDomain = rolesService.save(domain);

		// remove
		rolesService.remove(savedDomain.getRoleId());

		// check
		try {
			Roles resultDomain = rolesService.get(savedDomain.getRoleId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.Roles' object with id '" + savedDomain.getRoleId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testRemoveRoles() throws Exception {

		Roles domain1 = new Roles();
		domain1.setRoleName("ROLE_TEST");

		RolesHierarchy rolesHierarchy1 = new RolesHierarchy();

		String parentRole1 = idGenerationServiceRole.getNextStringId();

		RolesHierarchyId rolesHierarchyId1 = new RolesHierarchyId();
		rolesHierarchyId1.setChildRole("ROLE-00002");
		rolesHierarchyId1.setParentRole(parentRole1);

		rolesHierarchy1.setId(rolesHierarchyId1);

		Set<RolesHierarchy> childRole1 = new HashSet<RolesHierarchy>();

		childRole1.add(rolesHierarchy1);

		domain1.setRolesHierarchiesForChildRole(childRole1);

		domain1.setRoleId(parentRole1);
		domain1.setDescription(parentRole1 + " DESC");

		// save
		Roles savedDomain1 = rolesService.save(domain1);

		// remove
		rolesService.remove("ROLE-00002");

		// check
		try {
			Roles resultDomain1 = rolesService.get(savedDomain1.getRoleId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.Roles' object with id '" + savedDomain1.getRoleId()
							+ "' not found", e.getMessage());
		}
	}
}
