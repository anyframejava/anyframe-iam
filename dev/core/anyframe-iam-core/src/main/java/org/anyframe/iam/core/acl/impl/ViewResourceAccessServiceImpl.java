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
package org.anyframe.iam.core.acl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anyframe.iam.core.acl.ExtBasePermission;
import org.anyframe.iam.core.acl.IViewResourceAccessService;
import org.anyframe.iam.core.acl.ViewResourceHelper;
import org.anyframe.iam.core.securedobject.ISecuredObjectService;
import org.springframework.security.acls.model.Permission;

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
	//
	// view resource id 기반으로 동작하던 iam:access 태그를 view name 기반으로 수정
	// 그에 따른 쿼리문 수정
	// edited by youngmin.jo
	@SuppressWarnings("unchecked")
	public boolean isGranted(String viewName, List requiredPermissionList) throws Exception {

		Map paramMap = ViewResourceHelper.makeLoginUserMap();
		if (paramMap == null) {
			return false;
		}
		paramMap.put("viewName", viewName);

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
				if(result)
					return true;
			}
			else if ("GROUP".equals(tempMap.get("ref_type"))) {
				result = ViewResourceHelper.isGranted(mask, requiredPermissionList);
				if(result)
					return true;
			}
			else {
				result = ViewResourceHelper.isGranted(mask, requiredPermissionList);
				if (result) {
					return true;
				}
			}
		}
		
		// Permission이 없을 경우 상위의 view Resource를 검색,
		// 상위 view Resource에 대한 isGranted를 수행 함으로써
		// 상속 구조에서 권한 소유 여부를 체크한다.
		// edited by youngmin.jo
		
		// View Resource에 대한 상속 기능을 보류하기로 하여 아래의 내용을 주석 처리 하였음.
		// edited by youngmin.jo
//		String childView = securedObjectService.getViewHierarchy(viewResourceId);
//		if(!"".equals(childView))
//			return isGranted(childView, requiredPermissionList);

		return false;
	}

	@SuppressWarnings("unchecked")
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
