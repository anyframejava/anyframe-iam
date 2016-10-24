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
package anyframe.iam.core.userdetails.jdbc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * 
 * @author Byunghun Woo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/context-common.xml",
		"classpath*:META-INF/spring/context-security-default-map.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ExtJdbcUserDetailsManagerDefaultTest {

	@Resource(name = "jdbcUserService")
	ExtJdbcUserDetailsManager extJdbcUserDetailsManager;

	@SuppressWarnings("unchecked")
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

		assertTrue(extUser2.getCustomUser() instanceof Map);

		Map customUser2 = (Map) extUser2.getCustomUser();

		assertEquals("buyer", customUser2.get("USER_ID"));
		assertEquals("Lee, Man-hong", customUser2.get("USER_NAME"));
		assertEquals("buyer123", customUser2.get("PASSWORD"));

	}

}
