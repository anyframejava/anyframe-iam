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
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.anyframe.iam.core.userdetails.ExtUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
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
		"classpath*:META-INF/spring/context-security-rolehierarchyvoter.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class RoleHierarchyVoterTest {

	@Resource(name = "jdbcUserService")
	ExtJdbcUserDetailsManager extJdbcUserDetailsManager;

	@Resource(name = "accessDecisionManager")
	AbstractAccessDecisionManager accessDecisionManager;

	@Resource
	private DaoAuthenticationProvider provider;

	public void setAuthenticatedUser() {
		SecurityContextHolder.setContext(new SecurityContextImpl());

		Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test123");
		SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAuthenticatedUser() throws Exception {
		setAuthenticatedUser();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		try {
			List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
			list.add(new SecurityConfig("ROLE_USER"));
			
			assertEquals(RoleHierarchyVoter.ACCESS_GRANTED, ((AccessDecisionVoter) accessDecisionManager
					.getDecisionVoters().get(0)).vote(auth, new Object(), list));
		}
		catch (Exception e) {
			fail("RoleHierarchyVoter test failed!");
		}

		ExtUser extUser = CustomUserDetailsHelper.getAuthenticatedUser();

		Collection<GrantedAuthority> grantedAuthorities = extUser.getAuthorities();
		Iterator grantedAuthoritiesIterator = grantedAuthorities.iterator();
		String authority = (grantedAuthoritiesIterator.hasNext()) ? (String)((GrantedAuthority)grantedAuthoritiesIterator.next()).getAuthority():null;

		// check
		assertEquals("test", extUser.getUsername());
		assertEquals("test123", extUser.getPassword());
		assertTrue(grantedAuthorities instanceof Collection<?>);
		assertEquals("ROLE_ADMIN", authority);

		// mapClass 를 작성/설정하지 않은 경우 default 는 Map (ListOrderedMap) 으로 custom 사용자
		// 정보 객체를 되돌려 줌
		assertTrue(extUser.getCustomUser() instanceof Map);

		Map customUser = (Map) extUser.getCustomUser();

		assertEquals("test", customUser.get("USER_ID"));
		assertEquals("Kim, Young-Su", customUser.get("USER_NAME"));
		assertEquals("test123", customUser.get("PASSWORD"));
		// assertEquals(true, customUser.get("ENABLED"));
		// DB 에 Integer 필드로 선언했음
		assertEquals(1, customUser.get("ENABLED"));
		assertEquals("1234567890123", customUser.get("SSN"));
		assertEquals("Y", customUser.get("SL_YN"));
		// DB 에 Numeric 필드로 선언했음
		assertEquals(new BigDecimal(29), (BigDecimal) customUser.get("AGE"));
		assertEquals("2008-03-13", new SimpleDateFormat("yyyy-MM-dd").format(customUser.get("REG_DATE")));

	}
}
