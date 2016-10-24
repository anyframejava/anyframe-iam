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
package anyframe.iam.core.taglibs;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockPageContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
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
		"classpath*:META-INF/spring/context-security-view-resource.xml" })
public class ViewResourceTagTest {

	private final TestViewResourceTag viewResourceTag = new TestViewResourceTag();

	@Resource
	private DaoAuthenticationProvider provider;

	@Resource
	private ApplicationContext context;

	public void setAuthenticatedUser() {
		setAuthenticatedUser("test", "test123");
	}

	public void setAuthenticatedUser(String id, String password) {

		SecurityContextHolder.setContext(new SecurityContextImpl());
		Authentication authentication = new UsernamePasswordAuthenticationToken(id, password);
		SecurityContextHolder.getContext().setAuthentication(provider.authenticate(authentication));
	}

	@Test
	public void testViewResourceTag() throws JspException {
		setAuthenticatedUser();

		// 초기데이터 참고
		String viewResourceId = "updateCategory";
		String hasPermission = "1,3"; // {"READ", "READ,WRITE"}

		viewResourceTag.setViewResourceId(viewResourceId);
		viewResourceTag.setHasPermission(hasPermission);

		assertEquals(Tag.EVAL_BODY_INCLUDE, viewResourceTag.doStartTag());

		setAuthenticatedUser("taeyoung.kim", "taeyoung0");
		viewResourceTag.setViewResourceId(viewResourceId);
		viewResourceTag.setHasPermission(hasPermission);
		assertEquals(Tag.EVAL_BODY_INCLUDE, viewResourceTag.doStartTag());

		viewResourceTag.setViewResourceId(viewResourceId);
		viewResourceTag.setHasPermission("4");
		assertEquals(Tag.SKIP_BODY, viewResourceTag.doStartTag());

		PageContext pageContext = new MockPageContext();
		pageContext.setAttribute("requiredPermissions", hasPermission);
		viewResourceTag.setPageContext(pageContext);
		viewResourceTag.setViewResourceId(viewResourceId);
		viewResourceTag.setHasPermission("${requiredPermissions}");
		assertEquals(Tag.EVAL_BODY_INCLUDE, viewResourceTag.doStartTag());
	}

	private class TestViewResourceTag extends ViewResourceTag {

		@Override
		protected ApplicationContext getContext() {
			return context;
		}

	}

}
