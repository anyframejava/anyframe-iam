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
package org.springframework.security.access.vote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.anyframe.iam.core.intercept.web.FilterInvocationWrapper;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.UserDetailsWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;

/**
 * This class extends RoleVotes of Spring Security for dealing authority of restricted times.
 * If RoleHierarchy is set, as RoleHierarchyVoter this class also find a Role that is a low rank than 
 * set restricted Role. In case of restricted Role checking, this class check for Denied in RoleHierarchy first.
 * In case of restricted Resource checking, this class check for ACCESS_GRANTED first.
 * 
 * @author Byunghun Woo
 * 
 */
public class AnyframeRoleHierarchyRestrictedVoter extends RoleVoter {

	private Map hierarchyAddedCadCache = new HashMap();

	public Map getHierarchyAddedCadCache() {
		return hierarchyAddedCadCache;
	}

	public void setHierarchyAddedCadCache(Map hierarchyAddedCadCache) {
		this.hierarchyAddedCadCache = hierarchyAddedCadCache;
	}

	/**
	 * when rolesHiearchy reloading, previous cache data should be deleted 
	 * - Voter is usually registered inner bean, so get accessDecisionManager first and then
	 * find Voter by using getDecisionVoters()
	 */
	public void clearHierarchyAddedCadCache() {
		hierarchyAddedCadCache.clear();
	}

	private RoleHierarchy roleHierarchy = null;

	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	public RoleHierarchy getRoleHierarchy() {
		return roleHierarchy;
	}

	/**
	 * This method judges access permission about restricted times.
	 * There exist both management of Restricted Role(wrapped to FilterInvocationWrapper) 
	 * and the one of Restricted Resource.
	 * 
	 * @see anyframe.iam.core.intercept.web.RestrictedTimesFilterSecurityInterceptor
	 * @see anyframe.iam.core.intercept.web.ReloadableRestrictedTimesFilterInvocationSecurityMetadataSource
	 * 
	 * <br/>
	 */
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

		// Restricted Role - RoleHierarchy 를 고려한 Denied 위주 접근 권한 제어
		if (object instanceof FilterInvocationWrapper) {

			int result = ACCESS_ABSTAIN;

			if (roleHierarchy != null) {

				// 하위 hierarchy Role 을 포함하는 caching 데이터에 이전에 처리하여 넣어놓은 것이 있다면
				// 활용함
				List<ConfigAttribute> hierarchyAddedCad = (List<ConfigAttribute>) hierarchyAddedCadCache
						.get(attributes.toString());

				if (hierarchyAddedCad == null) {
					hierarchyAddedCad =  new ArrayList<ConfigAttribute>();

					// 보호권한으로 설정한 하위 권한도 보호권한으로 등록
					Object[] configAttributes = (Object[]) attributes.toArray();

					// RoleHierarchy 는 GrantedAuthority 형태로만 처리 가능 - 임시로
					// ConfigAttribute --> GrantedAuthority[] 변경
					Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(configAttributes.length);
					for (int i = 0; i < configAttributes.length; i++) {
						grantedAuthorities.add(new GrantedAuthorityImpl(((ConfigAttribute) configAttributes[i])
								.getAttribute()));
					}
					// RoleHierarchy 를 사용하여 getReachableGrantedAuthorities 얻어옴
					Collection<GrantedAuthority> reachableGrantedAuthorities = roleHierarchy
							.getReachableGrantedAuthorities(grantedAuthorities);

					// 다시 GrantedAuthority[] 를 ConfigAttribute 으로 변경
					Iterator reachableGrantedAuthoritiesIterator = reachableGrantedAuthorities.iterator();
					while(reachableGrantedAuthoritiesIterator.hasNext()) {
						GrantedAuthority grantedAuthority = (GrantedAuthority)reachableGrantedAuthoritiesIterator.next();
						hierarchyAddedCad.add(new SecurityConfig(grantedAuthority.getAuthority()));
					}

					hierarchyAddedCadCache.put(attributes.toString(), hierarchyAddedCad);
				}

				attributes = hierarchyAddedCad;
			}

			// 기본권한(상속권한 제외) 만 체크
			Collection<GrantedAuthority> authorities = extractAuthorities(authentication);
			Iterator authIter = authorities.iterator();
			boolean isGranted = false;

			while (authIter.hasNext()) {
				GrantedAuthority grantedAuthority = (GrantedAuthority)authIter.next();
				boolean isDenied = false;
				Iterator attrIter = attributes.iterator();
				while (attrIter.hasNext()) {
					ConfigAttribute attribute = (ConfigAttribute) attrIter.next();
					if (this.supports(attribute)) {
						if (attribute.getAttribute().equals(grantedAuthority.getAuthority())) {
							// restricted Role 로 정의된 것을 기본 권한으로 가지고 있는 경우는
							// Denied 임.
							isDenied = true;
							break;
						}
					}
					else {
						return ACCESS_DENIED;
					}
				}
				// 기본 권한 중 하나라도 denied 로 설정되지 않은 것이 있다면 Granted 로 처리
				if (!isDenied) {
					isGranted = true;
				}
			}

			return isGranted ? ACCESS_GRANTED : ACCESS_DENIED;

		}
		else { // Restricted Resource - RoleVoter 의 기본 ACCESS_GRANTED 접근 권한 제어
			return super.vote(authentication, object, attributes);
		}
	}

	/**
	 * In case of treating UserDetailsWrapper, this method return mapped authority to 
	 * user except the lower rank authority
	 */
	Collection<GrantedAuthority> extractAuthorities(Authentication authentication) {
		if (authentication.getPrincipal() instanceof UserDetailsWrapper) {
			return ((User) ((UserDetailsWrapper) authentication.getPrincipal()).getUnwrappedUserDetails())
					.getAuthorities();
		}
		else {
			return super.extractAuthorities(authentication);
		}
	}

}
