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
package org.anyframe.iam.core.intercept.web;

/**
 * This class offers some variables about restricted times 
 * and function that get/set RESTRICTED_RESOURCE_TYPE
 *  
 * @author Byunghun Woo
 * 
 */
public class RestrictedResourceHolder {

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
	 * When one filter runs, check 4 types of restricted times 
	 * and specify current order processing in ThreadLocal
	 */
	private static ThreadLocal resourceHolder = new ThreadLocal();

	/**
	 * Get current type of processing.
	 * Return alwasTimeRoleCheck if null
	 * 
	 * @return RESTRICTED_RESOURCE_TYPE of current order processing
	 */
	public static String getPresentResource() {
		if (resourceHolder.get() == null) {
			resourceHolder.set(RESTRICTED_RESOURCE_TYPE[0]);
		}

		return (String) resourceHolder.get();
	}

	/**
	 * Setting current type of processing.
	 * 
	 * @param resourceType
	 * 				type of processing
	 * RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0]~[3]
	 */
	public static void setPresentResource(String resourceType) {
		resourceHolder.set(resourceType);
	}
}
