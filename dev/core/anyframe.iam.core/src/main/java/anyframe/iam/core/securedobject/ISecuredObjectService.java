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
import org.springframework.security.access.ConfigAttribute;

import anyframe.common.exception.BaseException;

/**
 * This class offer some function to find data about secured object resources based on DB.
 * 
 * @author Byunghun Woo
 */
public interface ISecuredObjectService {
	Log LOGGER = LogFactory.getLog(ISecuredObjectService.class);

	/**
	 * Get mapping information of authority about secured resource with URL type.
	 * 
	 * @throws BaseException
	 *				fail to get data 
	 */
	public LinkedHashMap getRolesAndUrl() throws BaseException;

	/**
	 * Get mapping information of authority about secured resource with Method type.
	 * 
	 * @throws BaseException
	 *				fail to get data
	 */
	public LinkedHashMap getRolesAndMethod() throws BaseException;

	/**
	 * Get mapping information of authority about secured resource with PointCut type.
	 * 
	 * @throws BaseException
	 *				fail to get data
	 */
	public LinkedHashMap getRolesAndPointcut() throws BaseException;

	/**
	 * find URL data that matches the given url information.
	 * (Oracle 10g specific)
	 * 
	 * 
	 * @see anyframe.iam.core.intercept.LookupAttributesMethodReplacer#reimplement(Object,
	 * java.lang.reflect.Method, Object[])
	 * @param url
	 * @return
	 * @throws BaseException
	 */
	public List<ConfigAttribute> getMatchedRequestMapping(String url) throws BaseException;

	/**
	 * Get hierarchy information of Role
	 * 
	 * @return
	 * @throws BaseException
	 */
	public String getHierarchicalRoles() throws BaseException;

	/**
	 * get mapping information of restricted times-roles.
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List getRestrictedTimesRoles() throws BaseException;

	/**
	 * get mapping information of restricted times-resources
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List getRestrictedTimesResources() throws BaseException;

	/**
	 * get list of access permissions related with view resource that user register
	 * 
	 * @param paramMap
	 * 				Map object that contains viewResourceId and user information(userId, groupId,authorities)
	 * @return List
	 * 				List of access permissions that matches the given search conditions
	 * @throws BaseException
	 * 				fail to make list
	 */
	public List getViewResourceMapping(Map paramMap) throws BaseException;
	
	public String getViewHierarchy(String viewResourceId) throws BaseException;

}
