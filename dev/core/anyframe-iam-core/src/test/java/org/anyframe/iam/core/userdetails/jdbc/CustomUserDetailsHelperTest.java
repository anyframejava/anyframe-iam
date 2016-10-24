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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Byunghun Woo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-common.xml",
		"classpath*:META-INF/spring/context-security-custom-vo.xml" })
public class CustomUserDetailsHelperTest {

	// @Resource(name = "jdbcUserService")
	// private ExtJdbcUserDetailsManager extJdbcUserDetailsManager;

	@Resource
	private DaoAuthenticationProvider provider;

	public void setAuthenticatedUser() {
		SecurityContextHolder.setContext(new SecurityContextImpl());

		Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test123");
		// DaoAuthenticationProvider provider = new
		// DaoAuthenticationProvider();
		// provider.setUserDetailsService(extJdbcUserDetailsManager);
		SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
	}

	@Test
	public void testGetAuthenticatedUser() {
		setAuthenticatedUser();

		ExtUser extUser = CustomUserDetailsHelper.getAuthenticatedUser();

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
	}

}
