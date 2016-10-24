package org.anyframe.iam.admin.groupshierarchy.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.anyframe.exception.BaseException;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;
import org.anyframe.iam.admin.groupshierarchy.service.GroupsHierarchyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class GroupsHierarchyServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	@Autowired
	@Qualifier("groupsHierarchyService")
	GroupsHierarchyService groupsHierarchyService;
	
	public GroupsHierarchy makeDomain(){
		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		GroupsHierarchyId groupsHierarchyId = new GroupsHierarchyId();
		
		groupsHierarchyId.setChildGroup("testGroup");
		groupsHierarchyId.setParentGroup("GRP-0016");
		
		groupsHierarchy.setId(groupsHierarchyId);
		return groupsHierarchy;
	}
	
	@Test
	public void testSave() throws Exception{
		GroupsHierarchy domain = makeDomain();
		
		GroupsHierarchy resultDomain = groupsHierarchyService.save(domain);
		
		assertNotNull(resultDomain);
		assertEquals(domain.getId().getChildGroup(), resultDomain.getId().getChildGroup());
		assertEquals(domain.getId().getParentGroup(), resultDomain.getId().getParentGroup());
	}
	
	@Test
	public void testRemoveAllGroupsHierarchy() throws Exception{
		groupsHierarchyService.removeAllGroupsHierarchy();
		
		GroupsHierarchyId id = new GroupsHierarchyId();
		
		id.setChildGroup("testGroup");
		id.setParentGroup("GRP-0016");
		
		try {
			groupsHierarchyService.get(id);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
		}
		
	}
}
