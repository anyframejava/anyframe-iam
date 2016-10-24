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
package org.anyframe.iam.core.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anyframe.iam.core.userdetails.ExtUser;
import org.springframework.security.access.hierarchicalroles.UserDetailsWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This class provide some function to treat view resource
 * 
 * @author Byunghun Woo
 */
public class ViewResourceHelper {

	/**
	 * Get userId, groupId, authorities(List of assigned role) from authentication object of log-in user
	 * and return this information as Map.
	 * in case of IAM ExtUser, keep a fact in mind that groupId should be set in userGroupPropertyName
	 * of jdbcUserService(ExtJdbcUserDetailsManager).
	 * Also usersByUsernameQuery query should contain selection of UserGroups 
	 * 
	 * @return Map
	 * 				Map object that contains information of log-in user
	 */
	public static Map makeLoginUserMap() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		Map paramMap = new HashMap();

		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			paramMap.put("userId", ((UserDetails) principal).getUsername());
		}
		else {
			paramMap.put("userId", principal.toString());
		}

		// groupId 설정 - ExtUser 인 경우
		if (principal instanceof UserDetailsWrapper) {
			Object unwrapped = ((UserDetailsWrapper) principal).getUnwrappedUserDetails();
			if (unwrapped instanceof ExtUser) {
				ExtUser extUser = (ExtUser) unwrapped;
				String groupId = extUser.getUserGroup();

				if (groupId != null)
					paramMap.put("groupId", groupId);
			}
		}
		else if (principal instanceof ExtUser) {
			String groupId = ((ExtUser) principal).getUserGroup();
			if (groupId != null)
				paramMap.put("groupId", groupId);
		}

		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		List authoritiesList = new ArrayList();
		authoritiesList.addAll(authorities);

		paramMap.put("authorities", authoritiesList);

		return paramMap;
	}

	/**
	 * Return true when given mask - foundMask is matched 
	 * with requiredPermissionList(List of bit mask(Integer) ) at least once.
	 * 
	 * @param foundMask 
	 * 				bit mask of input permission(int)
	 * @param requiredPermissionList 
	 * 				List of permission that want to be checked
	 * @return boolean
	 * 				true when given mask is matched with permission list at least once.
	 */
	public static boolean isGranted(int foundMask, List requiredPermissionList) {
		for (int i = 0; i < requiredPermissionList.size(); i++) {
			int presentMask = ((Integer) requiredPermissionList.get(i)).intValue();

			// 원본 mask 와 보수 mask 에 대한 bit & 연산의 결과가 모두 0 보다 크면 포함관계로 볼 수 있음.
			// ex.) p : f 비교 - 001&011 == 001, 010&101 == 000, 010&010 == 010
			// cf.) p 가 f 를 포함하는 경우
			// 011 & 001 == 001 이지만 present 의 보수 100 와 비교하면 000 이 됨
			if (foundMask == presentMask || ((foundMask & presentMask) > 0 && (foundMask & ~presentMask) > 0)) {
				return true;
			}

			// // String 변환 후 contains 비교 로직
			// // p : 1 -> "READ", f : 3 -> "READ,WRITE"
			// // --> "READ,WRITE".contains("READ") == true
			// String presentMaskPermissions = ExtBasePermission
			// .getPermissionNames(ExtBasePermission
			// .buildFromMask(presentMask));
			// String foundMaskPermissions = ExtBasePermission
			// .getPermissionNames(ExtBasePermission
			// .buildFromMask(foundMask));
			// if (foundMaskPermissions.contains(presentMaskPermissions)) {
			// return true;
			// }
		}

		return false;
	}

}
