package org.anyframe.iam.admin.groupsusers.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.anyframe.exception.BaseException;
import org.anyframe.iam.admin.domain.GroupsUsersId;
import org.anyframe.iam.admin.groupsusers.service.GroupsUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class GroupsUsersServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("groupsUsersService")
	GroupsUsersService groupsUsersService;

	@Test
	public void testRemoveAllGroupsUsers() throws Exception {
		groupsUsersService.removeAllGroupsUsers();

		GroupsUsersId id = new GroupsUsersId();
		id.setUserId("jandeekum");
		id.setGroupId("GRP-0002");

		try {
			groupsUsersService.get(id);
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		} catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
		}
	}
}
