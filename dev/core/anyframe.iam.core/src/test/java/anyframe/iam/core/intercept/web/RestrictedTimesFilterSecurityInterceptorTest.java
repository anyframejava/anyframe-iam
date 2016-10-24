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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.DaoAuthenticationProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.common.util.DateUtil;

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
public class RestrictedTimesFilterSecurityInterceptorTest {

	@Resource(name = "restrictedTimesFilterSecurityInterceptor")
	RestrictedTimesFilterSecurityInterceptor restrictedTimesFilterSecurityInterceptor;

	@Resource(name = "restrictedTimesObjectDefinitionSource")
	ReloadableRestrictedTimesFilterInvocationDefinitionSource restrictedTimesObjectDefinitionSource;

	@Resource
	private DaoAuthenticationProvider provider;

	@Resource(name = "dataSource")
	private DataSource dataSource;

	public void setAuthenticatedUser() {
		setAuthenticatedUser("test", "test123");
	}

	public void setAuthenticatedUser(String id, String password) {

		SecurityContextHolder.setContext(new SecurityContextImpl());
		Authentication authentication = new UsernamePasswordAuthenticationToken(id, password);
		SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
	}

	public int changeTestData(String sql) {
		return new JdbcTemplate(dataSource).update(sql);
	}

	@Test
	public void testRestrictedTimesFilterSecurityInterceptor() throws Exception {

		// RetrictedTimes Roles/Resources 에 대해 DB 기반으로 등록한 초기 데이터 참고

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			assertTrue(true);
		}
		catch (Exception e) {
			fail("ROLE_ADMIN 은 장애/실행시간 제한 데이터로 설정하지 않았음.");
		}

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			fail("ROLE_USER 는 장애/실행시간 제한 데이터로 설정하였음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

		// ROLE_RESTRICTED
		setAuthenticatedUser("buyer", "buyer123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			fail("ROLE_RESTRICTED 는 장애/실행시간 제한 데이터로 설정하였음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

	}

	@Test
	public void testRestrictedTimesResourceData() throws Exception {

		// RetrictedTimes Roles/Resources 에 대해 DB 기반으로 등록한 초기 데이터 참고
		// - web-000002(\A/.*\.do.*\Z) 에 대해 제한 resource 로 등록되었음. - exclusion
		// Role - ROLE_ADMIN, ROLE_A

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test.do", "POST"));
			fail("resource 에 대해 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

		// ROLE_RESTRICTED
		setAuthenticatedUser("buyer", "buyer123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test.do", "POST"));
			fail("resource 에 대해 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test.do", "POST"));
			assertTrue(true);
		}
		catch (Exception e) {
			fail("ROLE_ADMIN 에 대해서는 해당 resource 에 예외 접근(exclusion) 설정했음.");
		}

		// ROLE_A
		setAuthenticatedUser("taeyoung.kim", "taeyoung0");
		// 현재 시각
		Date currentTime = DateUtil.string2Date(DateUtil.getCurrentTime("HHmmss"), "HHmmss");
		// time-00002 에 대한 시각 일과 종료, 일과 시작
		Date startTime = DateUtil.string2Date("180000", "HHmmss");
		Date endTime = DateUtil.string2Date("075959", "HHmmss");

		if ((DateUtil.greaterThan(currentTime, startTime) && DateUtil.greaterThan(currentTime, endTime))
				|| (DateUtil.greaterThan(startTime, currentTime) && DateUtil.greaterThan(endTime, currentTime))) {
			try {
				restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test.do", "POST"));
				fail("time-00002 에 대해서 ROLE_USER, ROLE_A 를 제한하였음.");
			}
			catch (Exception e) {
				assertTrue(e instanceof AccessDeniedException);
				// time-00002 에 대해서 ROLE_USER, ROLE_A 를 제한하였음.
				assertEquals(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0], RestrictedResourceHolder
						.getPresentResource());
			}
		}
		else { // time-00002 에 걸리지 않는 경우 --> time-00003(현재시각 포함) - web-000003
			// (*.do) 의 daily filtered resource 체크에서 ROLE_ADMIN 만 예외
			// 허용되있음.
			try {
				restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test.do", "POST"));
				fail("time-00003 에 대해서 ROLE_ADMIN 만 예외 허용하였음.");
			}
			catch (Exception e) {
				assertTrue(e instanceof AccessDeniedException);
				assertEquals(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[3], RestrictedResourceHolder
						.getPresentResource());
			}
		}

	}

	@Test
	public void testRestrictedTimesResourceDailyFilteredData() throws Exception {

		// restricted time role 맵핑 삭제 후 접근 가능 테스트
		changeTestData("delete from restricted_times_roles");
		restrictedTimesObjectDefinitionSource.reloadRestrictedTimes();

		// RetrictedTimes Roles/Resources 에 대해 DB 기반으로 등록한 초기 데이터 참고
		// - web-000003(\A/.*\Z) 에 대해 제한 resource 로 등록되었음. - exclusion Role -
		// ROLE_ADMIN, ROLE_A

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test", "GET"));
			fail("resource 에 대해 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test", "POST"));
			assertTrue(true);
		}
		catch (Exception e) {
			fail("ROLE_ADMIN 에 대해서는 해당 resource 에 예외 접근(exclusion) 설정했음.");
		}

	}

	@Test
	public void testReloadRestrictedTimesRoleData() throws Exception {

		// RetrictedTimes Roles/Resources 에 대해 DB 기반으로 등록한 초기 데이터 참고

		// restricted time role 맵핑 삭제 후 접근 가능 테스트
		changeTestData("delete from restricted_times_roles");
		restrictedTimesObjectDefinitionSource.reloadRestrictedTimes();

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			assertTrue(true);
		}
		catch (Exception e) {
			fail("장애/실행시간 제한 데이터를 삭제했음.");
		}

		// ROLE_RESTRICTED
		setAuthenticatedUser("buyer", "buyer123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			assertTrue(true);
		}
		catch (Exception e) {
			fail("장애/실행시간 제한 데이터를 삭제했음.");
		}

		// restricted time role 에 ROLE_RESTRICTED 추가 후 접근 가능 테스트
		changeTestData("insert into restricted_times_roles(time_id, role_id) values ('time-00001', 'ROLE_RESTRICTED')");
		restrictedTimesObjectDefinitionSource.reloadRestrictedTimes();

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			assertTrue(true);
		}
		catch (Exception e) {
			fail("ROLE_RESTRICTED 보다 상위 권한임.");
		}

		// ROLE_RESTRICTED
		setAuthenticatedUser("buyer", "buyer123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			fail("ROLE_RESTRICTED 에 대해 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

	}

	@Test
	public void testRestrictedTimesRoleDailyFilteredData() throws Exception {

		// daily filtered 데이터(time-00003 은 항상 오늘 데이터로 추가했음) 임시 추가 - ROLE_ADMIN
		// 까지 제한
		changeTestData("insert into restricted_times_roles(time_id, role_id) values ('time-00003', 'ROLE_ADMIN');");
		restrictedTimesObjectDefinitionSource.reloadRestrictedTimes();

		// ROLE_USER
		setAuthenticatedUser("bbnydory", "bbnydory0");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation());
			fail("현재날짜의 daily filtered Role 에 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

		// ROLE_ADMIN
		setAuthenticatedUser("test", "test123");
		try {
			restrictedTimesFilterSecurityInterceptor.invoke(createFilterInvocation("/test", "POST"));
			fail("현재날짜의 daily filtered Role 에 장애/실행시간 제한 데이터로 추가했음.");
		}
		catch (Exception e) {
			assertTrue(e instanceof AccessDeniedException);
		}

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
