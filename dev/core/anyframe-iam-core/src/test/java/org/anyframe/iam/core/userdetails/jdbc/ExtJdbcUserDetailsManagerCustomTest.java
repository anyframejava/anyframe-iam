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
package org.anyframe.iam.core.userdetails.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.anyframe.iam.core.userdetails.ExtUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Byunghun Woo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-common.xml",
		"classpath*:META-INF/spring/context-security-custom-vo.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ExtJdbcUserDetailsManagerCustomTest {

	@Resource(name = "jdbcUserService")
	ExtJdbcUserDetailsManager extJdbcUserDetailsManager;

	// SampleSecuritySechemaInitializer 를 통해 컨테이너 기동 시
	// DB 초기화 하였음.

	@Test
	public void testLoadUsersByUsername() throws Exception {

		String username = "test";
		String username2 = "buyer";

		// loadUserByUsername -> loadUsersByUsername (확장)

		// 'test' - ROLE_ADMIN
		UserDetails selectedUser = extJdbcUserDetailsManager.loadUserByUsername(username);

		ExtUser extUser = (ExtUser) selectedUser;
		
		Collection<GrantedAuthority> grantedAuthorities = extUser.getAuthorities();
		Iterator grantedAuthoritiesIterator = grantedAuthorities.iterator();
		String authority = (grantedAuthoritiesIterator.hasNext()) ? (String)((GrantedAuthority)grantedAuthoritiesIterator.next()).getAuthority():null;

		// check
		assertEquals("test", extUser.getUsername());
		assertEquals("test123", extUser.getPassword());
		assertTrue(grantedAuthorities instanceof Collection<?>);
		assertEquals("ROLE_ADMIN", authority);

		assertTrue(extUser.getCustomUser() instanceof CustomUser);

		CustomUser customUser = (CustomUser) extUser.getCustomUser();

		assertEquals("test", customUser.getUserId());
		assertEquals("Kim, Young-Su", customUser.getUserName());
		assertEquals("test123", customUser.getPassword());
		assertEquals(true, customUser.getEnabled());
		assertEquals("1234567890123", customUser.getSsn());
		assertEquals("Y", customUser.getSlYn().toString());
		assertEquals(29, customUser.getAge().intValue());
		assertEquals("2008-03-13", new SimpleDateFormat("yyyy-MM-dd").format(customUser.getRegDate()));

		// 'buyer' - ROLE_RESTRICTED
		UserDetails selectedUser2 = extJdbcUserDetailsManager.loadUserByUsername(username2);
		ExtUser extUser2 = (ExtUser) selectedUser2;

		Collection<GrantedAuthority> grantedAuthorities2 = extUser2.getAuthorities();
		Iterator grantedAuthoritiesIterator2 = grantedAuthorities2.iterator();
		String authority2 = (grantedAuthoritiesIterator2.hasNext()) ? (String)((GrantedAuthority)grantedAuthoritiesIterator2.next()).getAuthority():null;
		
		// check
		assertEquals("buyer", extUser2.getUsername());
		assertEquals("buyer123", extUser2.getPassword());
		assertTrue(grantedAuthorities2 instanceof Collection<?>);
		assertEquals("ROLE_RESTRICTED", authority2);

		assertTrue(extUser2.getCustomUser() instanceof CustomUser);

		CustomUser customUser2 = (CustomUser) extUser2.getCustomUser();

		assertEquals("buyer", customUser2.getUserId());
		assertEquals("Lee, Man-hong", customUser2.getUserName());
		assertEquals("buyer123", customUser2.getPassword());

	}

}
