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

package anyframe.iam.admin.assist.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.CandidateSecuredResources;

/**
 * An interface that provides a service to delete all candidate secured resources 
 * 
 * @author jongpil.park
 */
public interface AssistDao extends IamGenericDao<CandidateSecuredResources, String> {
	
//	/**
//	 * delete All candidate secured resources in DB.
//	 * @return the number of deleted resources 
//	 * @throws Exception fail to delete
//	 */
//	int removeAll() throws Exception;
	
	/**
	 * delete All candidate secured resources in DB that matches the given system Name field.
	 * @param systemName String system name
	 * @return the number of deleted resources
	 * @throws Exception fail to delete
	 */
	int removeAll(String systemName) throws Exception;
}
