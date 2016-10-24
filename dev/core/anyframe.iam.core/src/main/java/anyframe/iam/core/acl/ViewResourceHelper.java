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
package anyframe.iam.core.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.hierarchicalroles.UserDetailsWrapper;

import anyframe.iam.core.userdetails.ExtUser;

/**
 * view resource 에 대한 처리를 위한 util 성 기능을 제공한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ViewResourceHelper {

	/**
	 * 로그인 한 사용자의 authentication 객체로 부터 userId, groupId, authorities(할당된 권한의
	 * List) 를 구하여 Map 형태로 돌려준다. groupId 의 경우 IAM 의 ExtUser 인 경우 jdbcUserService
	 * (ExtJdbcUserDetailsManager) 에 userGroupPropertyName 설정 및
	 * usersByUsernameQuery 쿼리에 사용자 그룹 조회를 포함하는 경우에 설정됨에 유의한다.
	 * 
	 * @return 로그인 사용자 정보 Map
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

		GrantedAuthority[] authorities = authentication.getAuthorities();
		List authoritiesList = new ArrayList();
		for (int i = 0; i < authorities.length; i++) {
			authoritiesList.add(authorities[i].getAuthority());
		}

		paramMap.put("authorities", authoritiesList);

		return paramMap;
	}

	/**
	 * requiredPermissionList (bit mask(Integer) 의 list) 에 대한 loop 를 돌며 하나라도
	 * foundMask 로 제공된 권한을 만족하면 true 를 리턴한다.
	 * 
	 * @param foundMask 입력 permission 에 대한 bit mask (int)
	 * @param requiredPermissionList 체크하고자 하는 접근 권한 리스트
	 * @return granted 여부
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
