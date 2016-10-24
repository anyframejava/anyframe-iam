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
package anyframe.iam.core.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.vote.AbstractAccessDecisionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.iam.core.userdetails.jdbc.ExtJdbcUserDetailsManager;

/**
 * 
 * @author Byunghun Woo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-common.xml",
		"classpath*:META-INF/spring/context-security-view-resource.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ViewAccessServiceTest {

	@Resource(name = "jdbcUserService")
	ExtJdbcUserDetailsManager extJdbcUserDetailsManager;

	@Resource(name = "accessDecisionManager")
	AbstractAccessDecisionManager accessDecisionManager;

	@Resource(name = "viewResourceAccessService")
	IViewResourceAccessService viewResourceAccessService;

	@Resource
	private DaoAuthenticationProvider provider;

	public void setAuthenticatedUser() {
		setAuthenticatedUser("test", "test123");
	}

	public void setAuthenticatedUser(String id, String password) {

		SecurityContextHolder.setContext(new SecurityContextImpl());
		Authentication authentication = new UsernamePasswordAuthenticationToken(id, password);
		SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
	}

	@Test
	public void testIsGranted() throws Exception {
		setAuthenticatedUser();

		assertTrue(viewResourceAccessService.isGranted("updateCategory", Arrays.asList(new Integer[] { 3 })));

		setAuthenticatedUser("buyer", "buyer123");
		assertTrue(!viewResourceAccessService.isGranted("updateCategory", Arrays.asList(new Integer[] { 3 })));

		assertTrue(viewResourceAccessService.isGranted("addProduct", Arrays.asList(new Integer[] { 5 })));

	}

	@Test
	public void testGetregisteredPermissionList() throws Exception {
		setAuthenticatedUser();

		List registeredPermissionList = viewResourceAccessService.getRegisteredPermissionList();
		assertEquals(12, registeredPermissionList.size());

		List otherList = viewResourceAccessService.getRegisteredPermissionList();

		assertSame(registeredPermissionList, otherList);

	}
}