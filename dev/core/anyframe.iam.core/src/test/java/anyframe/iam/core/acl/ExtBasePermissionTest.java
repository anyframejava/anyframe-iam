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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.security.acls.domain.BasePermission;

/**
 * 
 * @author Byunghun Woo
 *
 */
public class ExtBasePermissionTest {

	@Test
	public void testExtBasePermission() throws Exception {

		assertEquals("READ", ExtBasePermission.getPermissionNames(BasePermission.READ));
		assertEquals("WRITE", ExtBasePermission.getPermissionNames(BasePermission.WRITE));
		assertEquals("CREATE", ExtBasePermission.getPermissionNames(BasePermission.CREATE));
		assertEquals("DELETE", ExtBasePermission.getPermissionNames(BasePermission.DELETE));
		assertEquals("ADMINISTRATION", ExtBasePermission.getPermissionNames(BasePermission.ADMINISTRATION));

		assertEquals("READ", ExtBasePermission.getPermissionNames(ExtBasePermission.READ));
		assertEquals("WRITE", ExtBasePermission.getPermissionNames(ExtBasePermission.WRITE));
		assertEquals("CREATE", ExtBasePermission.getPermissionNames(ExtBasePermission.CREATE));
		assertEquals("DELETE", ExtBasePermission.getPermissionNames(ExtBasePermission.DELETE));
		assertEquals("ADMINISTRATION", ExtBasePermission.getPermissionNames(ExtBasePermission.ADMINISTRATION));
		assertEquals("LIST", ExtBasePermission.getPermissionNames(ExtBasePermission.LIST));
		assertEquals("PRINT", ExtBasePermission.getPermissionNames(ExtBasePermission.PRINT));
		assertEquals("REPORT", ExtBasePermission.getPermissionNames(ExtBasePermission.REPORT));
		assertEquals("POPUP", ExtBasePermission.getPermissionNames(ExtBasePermission.POPUP));
		assertEquals("DOWNLOAD", ExtBasePermission.getPermissionNames(ExtBasePermission.DOWNLOAD));
		assertEquals("UPLOAD", ExtBasePermission.getPermissionNames(ExtBasePermission.UPLOAD));

		assertEquals("READ,WRITE", ExtBasePermission.getPermissionNames(ExtBasePermission.buildFromMask(3)));

		assertEquals("READ,WRITE,CREATE,DELETE,ADMINISTRATION", ExtBasePermission.getPermissionNames(ExtBasePermission
				.buildFromMask(31)));

		assertEquals(new String[] { "READ", "WRITE", "CREATE", "DELETE", "ADMINISTRATION" }, ExtBasePermission
				.getPermissionNamesArray(ExtBasePermission.buildFromMask(31)));

		assertEquals(new String[] { "LIST" }, ExtBasePermission.getPermissionNamesArray(ExtBasePermission
				.buildFromMask(32)));

		assertEquals("", ExtBasePermission.getPermissionNames(null));

		assertEquals(3, ExtBasePermission.getPermissionMask("READ,WRITE"));
		assertEquals(3, ExtBasePermission.getPermissionMask(new String[] { "READ", "WRITE" }));
		assertEquals(31, ExtBasePermission.getPermissionMask(" READ,WRITE, CREATE,	DELETE, ADMINISTRATION"));
		assertEquals(33, ExtBasePermission.getPermissionMask("READ,LIST"));

	}

}
