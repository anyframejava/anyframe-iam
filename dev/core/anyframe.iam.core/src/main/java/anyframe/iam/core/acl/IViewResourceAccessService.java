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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * view resource 에 대한 접근 권한 리스트에 대한 권한여부 판단 기능 및 어플리케이션에 등록된 permission 리스트 정보를
 * 제공한다.
 * 
 * @author Byunghun Woo
 * 
 */
public interface IViewResourceAccessService {

	Log LOGGER = LogFactory.getLog(IViewResourceAccessService.class);

	/**
	 * 인자로 주어진 viewResourceId 에 대하여 현재 로그인한 사용자(사용자 > 사용자 그룹 > 사용자에게 주어진 Role) 가
	 * requiredPermissionList 으로 제시한 접근 권한이 있는지 체크한다.
	 * 
	 * @param viewResourceId
	 * @param requiredPermissionList 비교대상 permission 의 Bit mask (Integer) List
	 * @return 허용여부
	 * @throws Exception
	 */
	public boolean isGranted(String viewResourceId, List requiredPermissionList) throws Exception;

	/**
	 * 해당 시스템에서 사용하는(등록되어 있는) 퍼미션 정보(퍼미션 명, 코드, mask, 패턴, bit order) 의 리스트를
	 * 제공한다. 등록하는 방법은viewResourceAccessService bean 의 registeredPermissions 에
	 * setter 방식의 list 태그를 사용한다.
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
	 * 각 permission 은
	 * org.springframework.beans.factory.config.FieldRetrievingFactoryBean 를
	 * 사용하여 bean 으로 등록되어 있어야 함.
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
	 * @return 퍼미션 정보 Map 의 list
	 * @throws Exception
	 */
	public List getRegisteredPermissionList() throws Exception;

}
