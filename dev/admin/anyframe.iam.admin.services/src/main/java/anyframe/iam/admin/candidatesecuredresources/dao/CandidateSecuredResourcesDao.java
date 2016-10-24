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

package anyframe.iam.admin.candidatesecuredresources.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.CandidateSecuredResources;

/**
 * An interface that provides a data management interface to the CandidateSecuredResources table.
 * 
 * @author youngmin.jo
 */
public interface CandidateSecuredResourcesDao extends IamGenericDao<CandidateSecuredResources, String> {

	/**
	 * find list of package names that matches the given keyword in
	 * candidateSecuredResource table
	 * @param keyword search keyword
	 * @return list of package names
	 * @throws Exception fail to find list
	 */
	String getPackagesList(String keyword) throws Exception;
	
	String getPackagesList(String keyword, String systemName) throws Exception;

	/**
	 * find list of class names that matches the given keyword and package name
	 * in candidateSecuredResource table
	 * @param keyword search keyword
	 * @param packages package name
	 * @return list of class names
	 * @throws Exception fail to find list
	 */
	String getClassesList(String keyword, String packages) throws Exception;
	
	String getClassesList(String keyword, String packages, String systemName) throws Exception;

	/**
	 * find list of method names that matches the given keyword, the package
	 * name and the class name in candidateSecuredResource table
	 * @param keyword search keyword
	 * @param packages package name
	 * @param classes class name
	 * @return list of method names
	 * @throws Exception fail to find list
	 */
	String getMethodList(String keyword, String packages, String classes) throws Exception;
	
	String getMethodList(String keyword, String packages, String classes, String systemName) throws Exception;

	/**
	 * find list of URLs that matches the given keyword in
	 * candidateSecuredResources table
	 * @param keyword search keyword
	 * @return list of URLs
	 * @throws Exception fail to find list
	 */
	String getRequestMappingList(String keyword) throws Exception;
	
	String getRequestMappingList(String keyword, String systemName) throws Exception;

	/**
	 * find list of parameter names that matches given keyword and
	 * requestMapping in candidateSecuredResources table
	 * @param keyword search keyword
	 * @param requestMapping URL name
	 * @return list of parameter names
	 * @throws Exception fail to find list
	 */
	String getParameterList(String keyword, String requestMapping) throws Exception;
	
	String getParameterList(String keyword, String requestMapping, String systemName) throws Exception;

	/**
	 * find list of pointCut that matches the given keyword in
	 * candidateSecuredResources table
	 * @param keyword search keyword
	 * @return list of pointCut
	 * @throws Exception fail to find list
	 */
	String getPointCutList(String keyword) throws Exception;
	
	String getPointCutList(String keyword, String systemName) throws Exception;
	
	/**
	 * find bean id that contains "param" field at candidate_resource_type 
	 * @return String bean id
	 * @throws Exception fail to find bean id
	 */
	String findMethodParam() throws Exception;
	
	String findMethodParam(String systemName) throws Exception;
}
