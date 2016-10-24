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

/**
 * restricted times 관련 상수 및 현재 처리 RESTRICTED_RESOURCE_TYPE 을 설정하고 구하는 기능을 제공한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class RestrictedResourceHolder {

	/**
	 * RESTRICTED_TIMES_RESERVED_ROLE_NAME - ROLE_RESTRICTED_TIMES restricted
	 * times - resource 제한 시기본으로 존재하지 않는 해당 예약 Role 을 지정해버림 cf.) exclusion 으로
	 * 등록된 Role
	 */
	public static final String RESTRICTED_TIMES_RESERVED_ROLE_NAME = "ROLE_RESTRICTED_TIMES";

	/**
	 * RESTRICTED_MIN_DATE - 19700101
	 */
	public static final String RESTRICTED_MIN_DATE = "19700101";

	/**
	 * RESTRICTED_MAX_DATE - 99991231
	 */
	public static final String RESTRICTED_MAX_DATE = "99991231";

	public static final String[] RESTRICTED_RESOURCE_TYPE = { "alwaysTimeRoleCheck", "dailyFilteredTimeRoleCheck",
			"alwaysTimeResourceCheck", "dailyFilteredTimeResourceCheck" };

	/**
	 * 하나의 filter 처리 시 4가지 유형의 restricted times 체크를 시도하며 이때 ThreadLocal 에 현재 처리
	 * 순서를 지정함
	 */
	private static ThreadLocal resourceHolder = new ThreadLocal();

	/**
	 * 현재 처리 유형을 얻는다. null 인 경우는 alwaysTimeRoleCheck 를 돌려준다.
	 * 
	 * @return 현재 순번의 RESTRICTED_RESOURCE_TYPE
	 */
	public static String getPresentResource() {
		if (resourceHolder.get() == null) {
			resourceHolder.set(RESTRICTED_RESOURCE_TYPE[0]);
		}

		return (String) resourceHolder.get();
	}

	/**
	 * 현재 처리 유형을 설정한다.
	 * 
	 * @param resourceType - 처리 유형 -
	 * RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0]~[3]
	 */
	public static void setPresentResource(String resourceType) {
		resourceHolder.set(resourceType);
	}
}
