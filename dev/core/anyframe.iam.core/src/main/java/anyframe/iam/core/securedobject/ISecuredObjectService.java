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
package anyframe.iam.core.securedobject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.ConfigAttributeDefinition;

import anyframe.common.exception.BaseException;

/**
 * Spring Security 의 초기 데이터로 참조되는 DB 기반의 보호된 자원(secured object resource) 데이터들에
 * 대한 조회 기능을 제공한다.
 * 
 * @author Byunghun Woo
 */
public interface ISecuredObjectService {
	Log LOGGER = LogFactory.getLog(ISecuredObjectService.class);

	/**
	 * Url 보호 자원에 대한 권한 맵핑 정보를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public LinkedHashMap getRolesAndUrl() throws BaseException;

	/**
	 * Method 보호 자원에 대한 권한 맵핑 정보를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public LinkedHashMap getRolesAndMethod() throws BaseException;

	/**
	 * Pointcut 보호 자원에 대한 권한 맵핑 정보를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public LinkedHashMap getRolesAndPointcut() throws BaseException;

	/**
	 * 매치되는 url 을 DB 로 부터 직접 찾는다. 현재 Oracle 10g 이상에 specific 한 쿼리로 기본 설정되어 있다.
	 * 
	 * @see anyframe.iam.core.intercept.LookupAttributesMethodReplacer#reimplement(Object,
	 * java.lang.reflect.Method, Object[])
	 * @param url
	 * @return
	 * @throws BaseException
	 */
	public ConfigAttributeDefinition getMatchedRequestMapping(String url) throws BaseException;

	/**
	 * Role 의 Hierarchy 정보를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public String getHierarchicalRoles() throws BaseException;

	/**
	 * restricted times-roles (시간에 따른 Role 제한) 맵핑 정보 를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List getRestrictedTimesRoles() throws BaseException;

	/**
	 * restricted times-resources (시간에 따른 Resource(Url) 제한) 맵핑 정보를 얻는다.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List getRestrictedTimesResources() throws BaseException;

	/**
	 * 사용자가 view resource 로 등록한 자원에 대한 접근 권한(permissions) 리스트를 얻는다.
	 * 
	 * @param paramMap - viewResourceId 와 로그인한 사용자 정보(userId, groupId,
	 * authorities) 를 담고 있는 검색조건 Map
	 * @return 해당 검색조건에 따른 view Resource 접근 권한 리스트
	 * @throws BaseException
	 */
	public List getViewResourceMapping(Map paramMap) throws BaseException;

}
