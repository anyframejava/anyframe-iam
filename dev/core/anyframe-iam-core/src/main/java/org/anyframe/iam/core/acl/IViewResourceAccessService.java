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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class offers a method that check if currently log-in user has accessable permission
 * Class also offers a method that make list of permission information
 * 
 * @author Byunghun Woo
 * 
 */
public interface IViewResourceAccessService {

	Log LOGGER = LogFactory.getLog(IViewResourceAccessService.class);

	/**
	 * Check if currently log-in user(user > user group > given role) has accessable permission about given view resources
	 * 
	 * @param viewName
	 * @param requiredPermissionList
	 * 				List of bit mask(Integer)
	 * @return boolean
	 * 				true if granted
	 * @throws Exception
	 * 				fail to check
	 *
	 * view resource id 기반으로 동작하던 iam:access 태그를 view name 기반으로 수정
	 * 그에 따른 쿼리문 수정
	 * edited by youngmin.jo
	 */
	public boolean isGranted(String viewName, List requiredPermissionList) throws Exception;

	/**
	 * Offer list of permission information(permission name, code, mask, pattern, bit order)
	 * that registered in (used by) target application.
	 * To register permission, add information into list tag in registeredPermissions of viewResourceAccessService bean
	 * 
	 * <pre>
	 * &lt;b:bean id=&quot;viewResourceAccessService&quot;
	 * 		class=&quot;anyframe.iam.core.acl.impl.ViewResourceAccessServiceImpl&quot;&gt;
	 * 		&lt;b:property name=&quot;securedObjectService&quot; ref=&quot;securedObjectService&quot; /&gt;
	 * 		&lt;b:property name=&quot;registeredPermissions&quot;&gt;
	 * 			&lt;b:list&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.READ&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.WRITE&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.CREATE&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.DELETE&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.ADMINISTRATION&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.LIST&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.PRINT&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.REPORT&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.POPUP&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.DOWNLOAD&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.UPLOAD&quot; /&gt;
	 * 				&lt;b:ref local=&quot;anyframe.iam.core.acl.ExtBasePermission.HELP&quot; /&gt;
	 * 			&lt;/b:list&gt;
	 * 		&lt;/b:property&gt;
	 * 	 &lt;/b:bean&gt;
	 * </pre>
	 * 
	 * each permission should be registered as bean 
	 * by using org.springframework.beans.factory.config.FieldRetrievingFactoryBean  
	 * 
	 * <pre>
	 * &lt;b:bean id=&quot;anyframe.iam.core.acl.ExtBasePermission.READ&quot;
	 * 		class=&quot;org.springframework.beans.factory.config.FieldRetrievingFactoryBean&quot;&gt;
	 * 		&lt;b:property name=&quot;staticField&quot;
	 * 			value=&quot;anyframe.iam.core.acl.ExtBasePermission.READ&quot; /&gt;
	 * 	&lt;/b:bean&gt;
	 * 	&lt;b:bean id=&quot;anyframe.iam.core.acl.ExtBasePermission.WRITE&quot;
	 * 		class=&quot;org.springframework.beans.factory.config.FieldRetrievingFactoryBean&quot;&gt;
	 * 		&lt;b:property name=&quot;staticField&quot;
	 * 			value=&quot;anyframe.iam.core.acl.ExtBasePermission.WRITE&quot; /&gt;
	 * 	&lt;/b:bean&gt;
	 *  ...
	 * </pre>
	 * 
	 * @return List 
	 * 				list of Map that contains permission information  
	 * @throws Exception
	 * 				fail to make List
	 */
	public List getRegisteredPermissionList() throws Exception;

}
