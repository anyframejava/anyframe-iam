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
package anyframe.iam.core.acl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.Permission;

import anyframe.iam.core.acl.ExtBasePermission;
import anyframe.iam.core.acl.IViewResourceAccessService;
import anyframe.iam.core.acl.ViewResourceHelper;
import anyframe.iam.core.securedobject.ISecuredObjectService;

public class ViewResourceAccessServiceImpl implements IViewResourceAccessService {

	private ISecuredObjectService securedObjectService;

	private Permission[] registeredPermissions;

	private List registeredPermissionList = null;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	public ISecuredObjectService getSecuredObjectService() {
		return securedObjectService;
	}

	public void setRegisteredPermissions(Permission[] registeredPermissions) {
		this.registeredPermissions = registeredPermissions;
	}

	public Permission[] getRegisteredPermissions() {
		return registeredPermissions;
	}

	// requiredPermissionList 는 List<Integer> 여야함. <- 허용권한의 mask 값에 대한 Integer
	// List
	public boolean isGranted(String viewResourceId, List requiredPermissionList) throws Exception {

		Map paramMap = ViewResourceHelper.makeLoginUserMap();
		if (paramMap == null) {
			return false;
		}
		paramMap.put("viewResourceId", viewResourceId);

		List resultList = securedObjectService.getViewResourceMapping(paramMap);

		for (int i = 0; i < resultList.size(); i++) {
			Map tempMap = (Map) resultList.get(i);
			Object tempMask = tempMap.get("mask");
			int mask;
			if (tempMask instanceof BigDecimal) {
				mask = ((BigDecimal) tempMask).intValue();
			}
			else {
				mask = ((Integer) tempMask).intValue();
			}

			boolean result = false;

			// 해당 viewResource 에 대해 특정 user 를 맵핑해 놓은 경우 user 에 대한 permission
			// mapping 만 고려토록 함. 마찬가지로 group 에 대해 맵핑해 놓은 경우 그 다음으로 고려함.
			if ("USER".equals(tempMap.get("ref_type"))) {
				result = ViewResourceHelper.isGranted(mask, requiredPermissionList);
				return result ? true : false;
			}
			else if ("GROUP".equals(tempMap.get("ref_type"))) {
				result = ViewResourceHelper.isGranted(mask, requiredPermissionList);
				return result ? true : false;
			}
			else {
				result = ViewResourceHelper.isGranted(mask, requiredPermissionList);
				if (result) {
					return true;
				}
			}
		}

		return false;
	}

	public List getRegisteredPermissionList() throws Exception {

		if (registeredPermissionList != null) {
			return registeredPermissionList;
		}

		registeredPermissionList = new ArrayList();
		for (int i = 0; i < registeredPermissions.length; i++) {
			Map permissionMap = new HashMap();

			Integer mask = new Integer(registeredPermissions[i].getMask());
			String[] permissionNameAndCode = (String[]) ExtBasePermission.getRegisteredPermissionNames().get(mask);
			// permission 명
			permissionMap.put("permissionName", permissionNameAndCode[0]);
			// code
			permissionMap.put("permissionCode", permissionNameAndCode[1]);
			// mask
			permissionMap.put("permissionMask", mask);
			// pattern
			permissionMap.put("permissionPattern", registeredPermissions[i].getPattern());
			// bit 위치 - 0 부터 시작
			int order = (int) (Math.log(mask.doubleValue()) / Math.log(2.0));
			permissionMap.put("permissionBitOrder", new Integer(order));

			registeredPermissionList.add(permissionMap);

		}

		return registeredPermissionList;
	}
}
