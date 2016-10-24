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

package org.anyframe.iam.admin.assist.service.impl;

import java.util.List;
import java.util.Map;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.assist.dao.AssistDao;
import org.anyframe.iam.admin.assist.service.AssistService;
import org.anyframe.iam.admin.domain.CandidateSecuredResources;

public class AssistServiceImpl extends GenericServiceImpl<CandidateSecuredResources, String> implements AssistService {
	
	private AssistDao assistDao;

	public AssistServiceImpl(AssistDao assistDao) {
		super(assistDao);
		this.assistDao = assistDao;
	}

	public boolean save(List<Map<String, Object>> resourceMapList) throws Exception {
		
		CandidateSecuredResources candidateSecuredResources = null;
		
		if(resourceMapList.size() > 0){
			candidateSecuredResources = new CandidateSecuredResources();
			Map<String, Object> firstMap = resourceMapList.get(0);
			String systemName = (String)firstMap.get("systemname");

			int delRow = assistDao.removeAll(systemName);
			LOGGER.debug(delRow + " rows deleted");
			
			for(int i = 0 ; i < resourceMapList.size() ; i++) {
				candidateSecuredResources = new CandidateSecuredResources();
				Map<String, Object> resourceMap = resourceMapList.get(i);
				
				candidateSecuredResources.setCandidateResourceId(String.valueOf(i+1) + systemName);
				candidateSecuredResources.setBeanid((String)resourceMap.get("beanid"));
				candidateSecuredResources.setSystemName((String)resourceMap.get("systemname"));
				candidateSecuredResources.setPackages((String)resourceMap.get("packages"));
				candidateSecuredResources.setClasses((String)resourceMap.get("classes"));
				candidateSecuredResources.setMethod((String)resourceMap.get("method"));
				candidateSecuredResources.setParameter((String)resourceMap.get("parameter"));
				candidateSecuredResources.setRequestMapping((String)resourceMap.get("requestmapping"));
				candidateSecuredResources.setPointCut((String)resourceMap.get("pointcut"));
				candidateSecuredResources.setCandidateResourceType((String)resourceMap.get("candidate_resource_type"));
	
				assistDao.save(candidateSecuredResources);
			}
			return true;
		}
		
		return false;
	}
}
