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
/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package anyframe.iam.core.intercept.web;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.security.vote.AbstractAccessDecisionManager;
import org.springframework.security.vote.AnyframeRoleHierarchyRestrictedVoter;
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
		"classpath*:META-INF/spring/context-security-restricted-times.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class AnyframeRoleHierarchyRestrictedVoterTest {

	@Resource(name = "restrictedTimesAccessDecisionManager")
	AbstractAccessDecisionManager accessDecisionManager;

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
	public void testAnyframeRoleHierarchyRestrictedVoterFilterInvocation() throws Exception {

		// Restricted Resource - RoleVoter 의 기본 ACCESS_GRANTED 접근 권한 제어

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// exclusion role 을 등록하지 않은 경우 - deny
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_DENIED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocation("/test.do", "GET"), new ConfigAttributeDefinition(
								RestrictedResourceHolder.RESTRICTED_TIMES_RESERVED_ROLE_NAME)));

		// exclusion role 을 등록한 경우 - access
		String[] accessRole = { RestrictedResourceHolder.RESTRICTED_TIMES_RESERVED_ROLE_NAME, "ROLE_ADMIN" };

		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_GRANTED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocation("/test.do", "GET"), new ConfigAttributeDefinition(accessRole)));
	}

	@Test
	public void testAnyframeRoleHierarchyRestrictedVoterFilterInvocationWrapper() throws Exception {

		// Restricted Role - RoleHierarchy 를 고려한 Denied 위주 접근 권한 제어 테스트

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// ROLE_USER 가 restricted 인 경우 - access
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_GRANTED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition("ROLE_USER")));

		// ROLE_ADMIN 이 restricted 인 경우 - deny
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_DENIED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition("ROLE_ADMIN")));

		// ROLE_USER, ROLE_RESTRICTED 가 restricted 인 경우 (실제로는
		// AnyframeRoleHierarchyRestrictedVoter 에서 RoleHierarchy 를 따라 비교 대상
		// restricted Role 의 하위 권한까지 비교하기 때문에 ROLE_USER 만 설정하는 것과 같음.
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_GRANTED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition(new String[] { "ROLE_USER",
								"ROLE_RESTRICTED" })));

		// 아래는 결국 ROLE_ADMIN 까지 restricted 이므로 - deny
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_DENIED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition(new String[] { "ROLE_USER",
								"ROLE_ADMIN" })));

		// ROLE_A
		setAuthenticatedUser("taeyoung.kim", "taeyoung0");
		auth = SecurityContextHolder.getContext().getAuthentication();

		// ROLE_USER 가 restricted 인 경우 - ROLE_A 와는 hierarchy 관계가 없으므로 access
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_GRANTED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition("ROLE_USER")));

		// ROLE_ADMIN 가 restricted 인 경우 - ROLE_A 의 상위 Role 이므로 deny
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_DENIED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition("ROLE_ADMIN")));

		// ROLE_RESTRICTED 가 restricted 인 경우 - ROLE_A 가 상위 Role 이므로 access
		assertEquals(AnyframeRoleHierarchyRestrictedVoter.ACCESS_GRANTED,
				((AnyframeRoleHierarchyRestrictedVoter) accessDecisionManager.getDecisionVoters().get(0)).vote(auth,
						createFilterInvocationWrapper(), new ConfigAttributeDefinition("ROLE_RESTRICTED")));

	}

	public FilterInvocationWrapper createFilterInvocationWrapper() {
		return new FilterInvocationWrapper(createFilterInvocation());
	}

	// 참고 -
	// https://fisheye.springsource.org/browse/spring-security/tags/spring-security-2.0.5.RELEASE/core/src/test/java/org/springframework/security/intercept/web/DefaultFilterInvocationDefinitionSourceTests.java?r=HEAD
	private FilterInvocation createFilterInvocation(String path, String method) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI(null);
		request.setMethod(method);

		request.setServletPath(path);

		return new FilterInvocation(request, new MockHttpServletResponse(), new MockFilterChain());
	}

	private FilterInvocation createFilterInvocation() {
		return createFilterInvocation("", null);
	}
}
