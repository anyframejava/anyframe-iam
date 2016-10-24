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

package anyframe.iam.admin.groups.service.impl;

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
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.groups.service.GroupsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class GroupsServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("groupsService")
	GroupsService groupsService;

	@Test
	public void testAddGroup() throws Exception {

		// make domain
		Groups domain = new Groups();
		domain.setGroupName("test");
		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		GroupsHierarchyId id = new GroupsHierarchyId();
		id.setParentGroup("GRP-0041");
		groupsHierarchy.setId(id);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		domain.setGroupsHierarchiesForChildGroup(childGroup);

		// save
		Groups savedGroups = groupsService.save(domain);

		// check
		Groups resultGroups = groupsService.get(savedGroups.getGroupId());
		assertEquals(savedGroups.getGroupId(), resultGroups.getGroupId());
	}

	@Test
	public void testUpdate() throws Exception {

		// make domain
		Groups domain = new Groups();
		domain.setGroupName("test");
		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		GroupsHierarchyId id = new GroupsHierarchyId();
		id.setParentGroup("GRP-0041");
		groupsHierarchy.setId(id);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		domain.setGroupsHierarchiesForChildGroup(childGroup);

		// save
		Groups savedGroups = groupsService.save(domain);

		// update
		domain.setGroupName("TEST");
		groupsService.update(domain);

		// check
		Groups resultGroups = groupsService.get(savedGroups.getGroupId());
		assertEquals("TEST", resultGroups.getGroupName());
	}

	@Test
	public void testGetGroupTree() throws Exception {

		// make domain
		Groups domain = new Groups();
		domain.setGroupName("test");
		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		GroupsHierarchyId id = new GroupsHierarchyId();
		id.setParentGroup("GRP-0041");
		groupsHierarchy.setId(id);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		domain.setGroupsHierarchiesForChildGroup(childGroup);

		// save
		Groups savedGroups = groupsService.save(domain);

		// get group tree
		List<IamTree> resultList = groupsService.getGroupTree("GRP-0041");

		// check
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals(3, resultList.size());
		assertEquals(savedGroups.getGroupId(), resultList.get(2).getId());
	}

	@Test
	public void testRemove() throws Exception {

		// make domain
		Groups domain1 = new Groups();
		domain1.setGroupName("test");
		GroupsHierarchy groupsHierarchy1 = new GroupsHierarchy();
		GroupsHierarchyId id1 = new GroupsHierarchyId();
		id1.setParentGroup("GRP-0041");
		groupsHierarchy1.setId(id1);

		Set<GroupsHierarchy> childGroup1 = new HashSet<GroupsHierarchy>();

		childGroup1.add(groupsHierarchy1);

		domain1.setGroupsHierarchiesForChildGroup(childGroup1);

		// save
		Groups savedGroups1 = groupsService.save(domain1);

		// remove
		groupsService.remove(savedGroups1.getGroupId());

		// check
		try {
			groupsService.get(savedGroups1.getGroupId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.Groups' object with id '" + savedGroups1.getGroupId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testRemoveGroups() throws Exception {

		// make domain
		Groups domain2 = new Groups();
		domain2.setGroupName("test2");
		GroupsHierarchy groupsHierarchy2 = new GroupsHierarchy();
		GroupsHierarchyId id2 = new GroupsHierarchyId();
		id2.setParentGroup("GRP-0041");
		groupsHierarchy2.setId(id2);

		Set<GroupsHierarchy> childGroup2 = new HashSet<GroupsHierarchy>();

		childGroup2.add(groupsHierarchy2);

		domain2.setGroupsHierarchiesForChildGroup(childGroup2);
		Groups savedGroups2 = groupsService.save(domain2);
		System.out.println(savedGroups2.getGroupId());

		Groups domain3 = new Groups();
		domain3.setGroupName("test3");
		GroupsHierarchy groupsHierarchy3 = new GroupsHierarchy();
		GroupsHierarchyId id3 = new GroupsHierarchyId();
		id3.setParentGroup(savedGroups2.getGroupId());
		groupsHierarchy3.setId(id3);

		Set<GroupsHierarchy> childGroup3 = new HashSet<GroupsHierarchy>();

		childGroup3.add(groupsHierarchy2);

		domain3.setGroupsHierarchiesForChildGroup(childGroup3);

		// save
		Groups savedGroups3 = groupsService.save(domain3);

		// remove
		groupsService.remove("GRP-0041");

		// check
		try {
			groupsService.get(savedGroups2.getGroupId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.Groups' object with id '" + savedGroups2.getGroupId()
							+ "' not found", e.getMessage());
		}

		try {
			groupsService.get(savedGroups3.getGroupId());
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.Groups' object with id '" + savedGroups3.getGroupId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testGetRootNodeOfGroups() throws Exception {
		List<IamTree> rootNode = groupsService.getRootNodeOfGroups();
		assertNotNull(rootNode);
		assertEquals("GRP-0001", rootNode.get(0).getId());
	}

	@Test
	public void testGetList() throws Exception {

		List<Groups> resultList = groupsService.getList();
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);

		assertEquals("GRP-0001", resultList.get(0).getGroupId());
	}
}
