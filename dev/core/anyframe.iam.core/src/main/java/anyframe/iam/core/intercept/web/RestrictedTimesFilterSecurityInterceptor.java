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
package anyframe.iam.core.intercept.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;

/**
 * Spring Security 의 FilterSecurityInterceptor 를 extends 하고 있으며 restricted times
 * 에 대한 맵핑 정보를 담고 있는 ReloadableRestrictedTimesFilterInvocationDefinitionSource 및
 * AnyframeRoleHierarchyRestrictedVoter 와 쌍으로 설정되어 filter 를 통해
 * alwaysTimeRoleCheck, dailyFilteredTimeRoleCheck, alwaysTimeResourceCheck,
 * dailyFilteredTimeResourceCheck 를 순서대로 처리한다. Spring Security 의 Namespace 태그를
 * 사용하는 경우 FilterSecurityInterceptor 가 기본 포함되어 order 가 겹치는 문제가 있으므로
 * custom-filter 로 등록하되 position 에 유의한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class RestrictedTimesFilterSecurityInterceptor extends FilterSecurityInterceptor {
	protected static final Log logger = LogFactory.getLog(RestrictedTimesFilterSecurityInterceptor.class);

	/**
	 * FILTER_APPLIED -
	 * "__anyframe_iam_restrictedTimesFilterSecurityInterceptor_filterApplied"
	 */
	private static final String FILTER_APPLIED = "__anyframe_iam_restrictedTimesFilterSecurityInterceptor_filterApplied";

	/**
	 * 
	 */
	public int getOrder() {
		return super.getOrder() - 60;
	}

	/**
	 * restricted times 체크를 위한 filter 의 메인 메서드이다.
	 */
	public void invoke(FilterInvocation fi) throws IOException, ServletException {

		if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
				&& isObserveOncePerRequest()) {

			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}
		else {

			if (fi.getRequest() != null) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			// Role 체크와 Resource 체크를 구분하기 위해(Voter 의 ACCESS/DENIED 정책이 다름)
			// FilterInvocation 을 wrapping 함
			FilterInvocationWrapper fiWrapper = new FilterInvocationWrapper(fi);

			// alwaysTimeRoleCheck
			if (logger.isDebugEnabled())
				logger.debug("== alwaysTimeRoleCheck started ==");
			RestrictedResourceHolder.setPresentResource(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0]);
			super.beforeInvocation(fiWrapper);

			// dailyFilteredTimeRoleCheck
			if (logger.isDebugEnabled())
				logger.debug("== dailyFilteredTimeRoleCheck started ==");
			RestrictedResourceHolder.setPresentResource(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[1]);
			super.beforeInvocation(fiWrapper);

			// alwaysTimeResourceCheck
			if (logger.isDebugEnabled())
				logger.debug("== alwaysTimeResourceCheck started ==");
			RestrictedResourceHolder.setPresentResource(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[2]);
			super.beforeInvocation(fi);

			// dailyFilteredTimeResourceCheck
			if (logger.isDebugEnabled())
				logger.debug("== dailyFilteredTimeResourceCheck started ==");
			RestrictedResourceHolder.setPresentResource(RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[3]);
			super.beforeInvocation(fi);

			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

		}
	}
}
