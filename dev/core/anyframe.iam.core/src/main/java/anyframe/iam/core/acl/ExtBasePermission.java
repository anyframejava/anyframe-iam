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
 * An object that extends BasePermission in Spring Security
 * and contains definition of additional permission.
 * To refer permission information easily, this class contains permission name and code of authority mask(Integer).
 * 
 * @author Byunghun Woo
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
	 * Return registerdPermissionNames that contains permission name and code of mask(Integer)  
	 * 
	 * @return Map
	 */
	public static Map getRegisteredPermissionNames() {
		return Collections.unmodifiableMap(registeredPermissionNames);
	}

	/**
	 * Get permission name that contains a code from pattern of permission
	 * and return list.
	 *  
	 * @param permission
	 * 				Permission object
	 * 
	 * @return List
	 * 				list of permissionName
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
	 * Get permission name that contains a code from pattern of permission
	 * and return array of String
	 * 
	 * @param permission
	 * 				Permission object
	 * @return String[]
	 * 				array of permissionName
	 */
	public static String[] getPermissionNamesArray(Permission permission) {
		List permissionNames = getPermissionNamesList(permission);
		return (String[]) permissionNames.toArray(new String[0]);
	}

	/**
	 * Get permission name that contains a code from pattern of permission
	 * and return String that split with comma
	 *  
	 * @param permission
	 * 				Permission class
	 * @return String
	 * 				permissionNames that split with comma
	 */
	public static String getPermissionNames(Permission permission) {
		return getPermissionNames(permission, ",");
	}

	/**
	 * Get permission name that contains a code from pattern of permission
	 * and return String that split with given separator
	 * 
	 * @param permission
	 * 				Permission class
	 * @param String
	 * 				separator
	 * @return String
	 * 				permissionNames that split with comma
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
	 * Get value of mask from permission name that split with comma
	 * 
	 * @param permissionNames
	 * 				permission names that split with comma.  ex) "READ,WRITE"
	 * @return value of mask(int)
	 * 				value of mask(int) that is sum of permission names
	 */
	public static int getPermissionMask(String permissionNames) {
		return getPermissionMask(permissionNames, ",");
	}

	/**
	 * Get value of mask from permission name that split with given separator
	 * 
	 * @param permissionNames
	 * 				permission names that split with given separator.  ex) "READ,WRITE,PRINT"
	 * @param String
	 * 				separator
	 * @return value of mask(int)
	 * 				value of mask(int) that is sum of permission names
	 */
	public static int getPermissionMask(String permissionNames, String separator) {
		return getPermissionMask(permissionNames.replaceAll("\\s+", "").split(separator));
	}

	/**
	 * Get value of mask from array of permission name
	 * 
	 * @param permissionNames
	 * 				String array of permission names
	 * @return value of mask(int)
	 * 				value of mask(int) that is sum of permission names
	 */
	public static int getPermissionMask(String[] permissionNames) {
		int mask = 0;
		for (int i = 0; i < permissionNames.length; i++) {
			mask += buildFromName(permissionNames[i]).getMask();
		}
		return mask;
	}

}
