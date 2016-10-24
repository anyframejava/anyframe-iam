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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.Permission;
import org.springframework.security.acls.domain.BasePermission;

/**
 * Spring Security 의 BasePermission 을 확장하고 있으며 기본 권한 외 많이 쓰이는 유형의 추가 권한의 정의를
 * 포함하고 있다. 또한 registeredPermissionNames 라는 Map 에 권한 mask (Integer) 에 대한 퍼미션
 * 명/코드에 대한 맵핑을 등록해두어 쉽게 퍼미션 정보를 참조 가능토록 하였다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ExtBasePermission extends BasePermission {

	// 기본 권한 - BasePermission 참고
	// READ('R'), WRITE('W'), CREATE('C'), DELETE('D'), ADMINISTRATION('A')

	// 추가 권한
	public static final Permission LIST = new ExtBasePermission(1 << 5, 'L'); // 32

	public static final Permission PRINT = new ExtBasePermission(1 << 6, 'P'); // 64

	public static final Permission REPORT = new ExtBasePermission(1 << 7, 'r'); // 128

	public static final Permission POPUP = new ExtBasePermission(1 << 8, 'p'); // 256

	public static final Permission DOWNLOAD = new ExtBasePermission(1 << 9, 'd'); // 512

	public static final Permission UPLOAD = new ExtBasePermission(1 << 10, 'u'); // 1024

	public static final Permission HELP = new ExtBasePermission(1 << 11, 'h'); // 2048

	// 기타 예약 권한
	public static final Permission FNC0 = new ExtBasePermission(1 << 12, '0');

	public static final Permission FNC1 = new ExtBasePermission(1 << 13, '1');

	public static final Permission FNC2 = new ExtBasePermission(1 << 14, '2');

	public static final Permission FNC3 = new ExtBasePermission(1 << 15, '3');

	public static final Permission FNC4 = new ExtBasePermission(1 << 16, '4');

	public static final Permission FNC5 = new ExtBasePermission(1 << 17, '5');

	public static final Permission FNC6 = new ExtBasePermission(1 << 18, '6');

	public static final Permission FNC7 = new ExtBasePermission(1 << 19, '7');

	public static final Permission FNC8 = new ExtBasePermission(1 << 20, '8');

	public static final Permission FNC9 = new ExtBasePermission(1 << 21, '9');

	private static final Map registeredPermissionNames = new HashMap();

	static {

		registerPermissionsFor(ExtBasePermission.class);

		registeredPermissionNames.put(new Integer(1 << 0), new String[] { "READ", "R" });
		registeredPermissionNames.put(new Integer(1 << 1), new String[] { "WRITE", "W" });
		registeredPermissionNames.put(new Integer(1 << 2), new String[] { "CREATE", "C" });
		registeredPermissionNames.put(new Integer(1 << 3), new String[] { "DELETE", "D" });
		registeredPermissionNames.put(new Integer(1 << 4), new String[] { "ADMINISTRATION", "A" });
		registeredPermissionNames.put(new Integer(1 << 5), new String[] { "LIST", "L" });
		registeredPermissionNames.put(new Integer(1 << 6), new String[] { "PRINT", "P" });
		registeredPermissionNames.put(new Integer(1 << 7), new String[] { "REPORT", "r" });
		registeredPermissionNames.put(new Integer(1 << 8), new String[] { "POPUP", "p" });
		registeredPermissionNames.put(new Integer(1 << 9), new String[] { "DOWNLOAD", "d" });
		registeredPermissionNames.put(new Integer(1 << 10), new String[] { "UPLOAD", "u" });
		registeredPermissionNames.put(new Integer(1 << 11), new String[] { "HELP", "h" });
		registeredPermissionNames.put(new Integer(1 << 12), new String[] { "FNC0", "0" });
		registeredPermissionNames.put(new Integer(1 << 13), new String[] { "FNC1", "1" });
		registeredPermissionNames.put(new Integer(1 << 14), new String[] { "FNC2", "2" });
		registeredPermissionNames.put(new Integer(1 << 15), new String[] { "FNC3", "3" });
		registeredPermissionNames.put(new Integer(1 << 16), new String[] { "FNC4", "4" });
		registeredPermissionNames.put(new Integer(1 << 17), new String[] { "FNC5", "5" });
		registeredPermissionNames.put(new Integer(1 << 18), new String[] { "FNC6", "6" });
		registeredPermissionNames.put(new Integer(1 << 19), new String[] { "FNC7", "7" });
		registeredPermissionNames.put(new Integer(1 << 20), new String[] { "FNC8", "8" });
		registeredPermissionNames.put(new Integer(1 << 21), new String[] { "FNC9", "9" });
	}

	/**
	 * 
	 */
	protected ExtBasePermission(int mask, char code) {
		super(mask, code);
	}

	/**
	 * mask (Integer) 에 대한 퍼미션 명/코드에 대한 맵핑정보를 제공하는 registeredPermissionNames 를
	 * 돌려준다.
	 * 
	 * @return Map
	 */
	public static Map getRegisteredPermissionNames() {
		return Collections.unmodifiableMap(registeredPermissionNames);
	}

	/**
	 * permission 에 따른 pattern 에서 코드가 존재하는 각 퍼미션 명을 구해와 list 형태로 돌려준다.
	 * 
	 * @param permission 입력 객체
	 * 
	 * @return List permissionName 의 list
	 */
	public static List getPermissionNamesList(Permission permission) {
		if (permission == null) {
			return null;
		}

		List permissionNames = new ArrayList();

		String pattern = permission.getPattern();

		int i = 1;
		int length = pattern.length();
		while (length >= i) {
			if (Permission.RESERVED_OFF != pattern.charAt(length - i)) {
				permissionNames.add(((String[]) registeredPermissionNames.get(new Integer(
						new Double(Math.pow(2, i - 1)).intValue())))[0]);
			}
			i++;
		}

		return permissionNames;
	}

	/**
	 * permission 에 따른 pattern 에서 코드가 존재하는 각 퍼미션 명을 구해와 String 배열 형태로 돌려준다.
	 * 
	 * @param permission 입력 객체(Permission)
	 * @return permissionName 의 배열
	 */
	public static String[] getPermissionNamesArray(Permission permission) {
		List permissionNames = getPermissionNamesList(permission);
		return (String[]) permissionNames.toArray(new String[0]);
	}

	/**
	 * permission 에 따른 pattern 에서 코드가 존재하는 각 퍼미션 명을 구해와 ,로 구분된 String 형태로 돌려준다.
	 * 
	 * @param permission 입력 객체(Permission)
	 * @return ,(콤마) 로 구분된 permissionNames
	 */
	public static String getPermissionNames(Permission permission) {
		return getPermissionNames(permission, ",");
	}

	/**
	 * permission 에 따른 pattern 에서 코드가 존재하는 각 퍼미션 명을 구해와 separator 로 구분된 String
	 * 형태로 돌려준다.
	 * 
	 * @param permission 입력 객체(Permission)
	 * @param separator 구분자(ex. ",")
	 * @return
	 */
	public static String getPermissionNames(Permission permission, String separator) {
		List permissionNames = getPermissionNamesList(permission);

		if (permissionNames == null || permissionNames.size() < 1) {
			return "";
		}
		else if (permissionNames.size() == 1) {
			return (String) permissionNames.get(0);
		}

		StringBuffer permissionNamesStr = new StringBuffer();
		for (int i = 0; i < permissionNames.size(); i++) {
			if (i != 0) {
				permissionNamesStr.append(separator);
			}
			permissionNamesStr.append(permissionNames.get(i));
		}

		return permissionNamesStr.toString();
	}

	/**
	 * ,로 구분된 주어진 permission 명들로 부터 해당 mask 값을 얻는다.
	 * 
	 * @param permissionNames - , 로 구분된 permission 명 ex.)"READ,WRITE"
	 * @return 해당 permission name 들의 합에 대한 mask 값(int)
	 */
	public static int getPermissionMask(String permissionNames) {
		return getPermissionMask(permissionNames, ",");
	}

	/**
	 * 
	 * @param permissionNames
	 * @param separator
	 * @return
	 */
	/**
	 * separator 로 구분된 주어진 permission 명들로 부터 해당 mask 값을 얻는다.
	 * 
	 * @param permissionNames , 로 구분된 permission 명 ex.)"READ,WRITE"
	 * @param separator 구분자 ex.) "," split 의 인자로 사용되므로 정규식 예약어 사용에 유의할 것
	 * @return 해당 permission name 들의 합에 대한 mask 값(int)
	 */
	public static int getPermissionMask(String permissionNames, String separator) {
		return getPermissionMask(permissionNames.replaceAll("\\s+", "").split(separator));
	}

	/**
	 * 주어진 permission 명들의 배열로 부터 해당 mask 값을 얻는다.
	 * 
	 * @param permissionNames permission 명의 배열 ex.) new String[]{"READ",
	 * "WRITE"}
	 * @return 해당 permission name 들의 합에 대한 mask 값(int)
	 */
	public static int getPermissionMask(String[] permissionNames) {
		int mask = 0;
		for (int i = 0; i < permissionNames.length; i++) {
			mask += buildFromName(permissionNames[i]).getMask();
		}
		return mask;
	}

}
