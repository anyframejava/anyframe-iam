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
package org.springframework.security.vote;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.hierarchicalroles.RoleHierarchy;
import org.springframework.security.userdetails.hierarchicalroles.UserDetailsWrapper;

import anyframe.iam.core.intercept.web.FilterInvocationWrapper;

/**
 * Spring Security 의 RoleVoter 를 extends 하고 있으며 restricted times 관련 권한 처리를 위한
 * Voter 이다. roleHierarchy 를 설정한 경우 RoleHierarchyVoter 와 마찬가지로 설정된 제한 Role 의 하위
 * Role 을 찾아 함께 고려하고 있다. Restricted Role 체크인 경우 RoleHierarchy 를 고려한 Denied 위주 접근
 * 권한 제어로 처리되고 Restricted Resource 체크인 경우 RoleVoter 의 기본 ACCESS_GRANTED 접근 권한
 * 제어로 처리됨에 유의한다.
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
	 * rolesHierarchy reload 시에 이전 cache 데이터를 잘 지워줄것 - Voter 는 일반적으로 inner bean
	 * 으로 등록되므로 해당 accessDecisionManager 를 얻어 getDecisionVoters() 로 현재 Voter 를
	 * 찾아야 할 것임
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
	 * restricted times 제한에 대한 접근 권한 판단을 담당한다. Restricted Role
	 * (FilterInvocationWrapper 로 가공하였음) 과 Restricted Resource 에 대한 처리가 함께 존재한다.
	 * (하나의 Filter 에 대한 accessDecisionManager 로 등록하나 서로 다른 voter 로직을 사용하기 위함)
	 * 
	 * @see anyframe.iam.core.intercept.web.RestrictedTimesFilterSecurityInterceptor
	 * @see anyframe.iam.core.intercept.web.ReloadableRestrictedTimesFilterInvocationDefinitionSource
	 * 
	 * <br/>
	 */
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {

		// Restricted Role - RoleHierarchy 를 고려한 Denied 위주 접근 권한 제어
		if (object instanceof FilterInvocationWrapper) {

			int result = ACCESS_ABSTAIN;

			if (roleHierarchy != null) {

				// 하위 hierarchy Role 을 포함하는 caching 데이터에 이전에 처리하여 넣어놓은 것이 있다면
				// 활용함
				ConfigAttributeDefinition hierarchyAddedCad = (ConfigAttributeDefinition) hierarchyAddedCadCache
						.get(config.toString());

				if (hierarchyAddedCad == null) {

					// 보호권한으로 설정한 하위 권한도 보호권한으로 등록
					Object[] configAttributes = (Object[]) config.getConfigAttributes().toArray();

					// RoleHierarchy 는 GrantedAuthority 형태로만 처리 가능 - 임시로
					// ConfigAttributeDefinition --> GrantedAuthority[] 변경
					GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[configAttributes.length];
					for (int i = 0; i < configAttributes.length; i++) {
						grantedAuthorities[i] = new GrantedAuthorityImpl(((ConfigAttribute) configAttributes[i])
								.getAttribute());
					}
					// RoleHierarchy 를 사용하여 getReachableGrantedAuthorities 얻어옴
					GrantedAuthority[] reachableGrantedAuthorities = roleHierarchy
							.getReachableGrantedAuthorities(grantedAuthorities);

					// 다시 GrantedAuthority[] 를 ConfigAttributeDefinition 으로 변경
					String[] reachableAuthorities = new String[reachableGrantedAuthorities.length];
					for (int i = 0; i < reachableGrantedAuthorities.length; i++) {
						reachableAuthorities[i] = reachableGrantedAuthorities[i].getAuthority();
					}
					hierarchyAddedCad = new ConfigAttributeDefinition(reachableAuthorities);

					hierarchyAddedCadCache.put(config.toString(), hierarchyAddedCad);
				}

				config = hierarchyAddedCad;
			}

			// 기본권한(상속권한 제외) 만 체크
			GrantedAuthority[] authorities = extractAuthorities(authentication);

			boolean isGranted = false;

			for (int i = 0; i < authorities.length; i++) {
				Iterator iter = config.getConfigAttributes().iterator();
				boolean isDenied = false;
				while (iter.hasNext()) {
					ConfigAttribute attribute = (ConfigAttribute) iter.next();
					if (this.supports(attribute)) {
						if (attribute.getAttribute().equals(authorities[i].getAuthority())) {
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
			return super.vote(authentication, object, config);
		}
	}

	/**
	 * UserDetailsWrapper 로 처리되는 경우 하위권한은 제외한 해당 사용자에 기본으로 맵핑된 권한만을 되돌려준다. <br/>
	 * 
	 */
	GrantedAuthority[] extractAuthorities(Authentication authentication) {
		if (authentication.getPrincipal() instanceof UserDetailsWrapper) {
			return ((User) ((UserDetailsWrapper) authentication.getPrincipal()).getUnwrappedUserDetails())
					.getAuthorities();
		}
		else {
			return super.extractAuthorities(authentication);
		}
	}

}
